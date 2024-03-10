<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="标题" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入标题"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="内容发布人ID" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入内容发布人ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="类别ID" prop="categoryId">
        <el-input
          v-model="queryParams.categoryId"
          placeholder="请输入类别ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="直播状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择直播状态" clearable>
          <el-option
            v-for="dict in dict.type.sys_live_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="创建时间">
        <el-date-picker
          v-model="daterangeCreateTime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['live:live:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['live:live:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['live:live:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['live:live:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="liveList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="直播ID" align="center" prop="liveId" />
      <el-table-column label="标题" align="center" prop="title" />
      <el-table-column label="内容描述" align="center" prop="description" />
      <el-table-column label="直播封面地址" align="center" prop="coverPath" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.coverPath" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="内容发布人ID" align="center" prop="userId" />
      <el-table-column label="类别ID" align="center" prop="categoryId" />
      <el-table-column label="直播状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_live_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleBan(scope.row)"
            v-hasPermi="['live:live:edit']"
          >封禁/解禁</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['live:live:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改直播对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入标题" />
        </el-form-item>
        <el-form-item label="内容描述" prop="description">
          <el-input v-model="form.description" placeholder="请输入内容描述" />
        </el-form-item>
        <el-form-item label="直播封面地址" prop="coverPath">
          <image-upload v-model="form.coverPath"/>
        </el-form-item>
        <el-form-item label="内容发布人ID" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入内容发布人ID" />
        </el-form-item>
        <el-form-item label="类别ID" prop="categoryId">
          <el-input v-model="form.categoryId" placeholder="请输入类别ID" />
        </el-form-item>
        <el-form-item label="直播状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in dict.type.sys_live_status"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="封禁内容" prop="banInfo">
          <el-input v-model="form.banInfo" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 添加或修改直播对话框 -->
    <el-dialog :title="title" :visible.sync="ban" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="banRules" label-width="80px">
        <el-form-item label="直播状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in dict.type.sys_live_status"
              :key="dict.value"
              :label="dict.value"
              v-if="dict.value === '0' || dict.value === '1'"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="封禁内容" prop="banInfo">
          <el-input v-model="form.banInfo" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="banForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listLive, getLive, delLive, addLive, updateLive, banLive} from "@/api/live/live";

export default {
  name: "Live",
  dicts: ['sys_live_status'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 直播表格数据
      liveList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 拉黑封禁弹出层
      ban: false,
      // 封禁内容时间范围
      daterangeCreateTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        title: null,
        userId: null,
        categoryId: null,
        status: null,
        createTime: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        title: [
          { required: true, message: "标题不能为空", trigger: "blur" }
        ],
        coverPath: [
          { required: true, message: "直播封面地址不能为空", trigger: "blur" }
        ],
        userId: [
          { required: true, message: "内容发布人ID不能为空", trigger: "blur" }
        ],
        status: [
          { required: true, message: "直播状态不能为空", trigger: "change" }
        ],
      },
      // 表单校验
      banRules: {
        banInfo: [
          { required: true, message: "封禁内容不能为空", trigger: "blur" }
        ],
        status: [
          { required: true, message: "直播状态不能为空", trigger: "change" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询直播列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeCreateTime && '' != this.daterangeCreateTime) {
        this.queryParams.params["beginCreateTime"] = this.daterangeCreateTime[0];
        this.queryParams.params["endCreateTime"] = this.daterangeCreateTime[1];
      }
      listLive(this.queryParams).then(response => {
        this.liveList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.ban = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        liveId: null,
        title: null,
        description: null,
        coverPath: null,
        userId: null,
        categoryId: null,
        status: null,
        banInfo: null,
        createTime: null,
        updateTime: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.daterangeCreateTime = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.liveId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加直播";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const liveId = row.liveId || this.ids
      getLive(liveId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改直播";
      });
    },
    /** 拉黑按钮操作 */
    handleBan(row) {
      this.reset();
      const liveId = row.liveId || this.ids
      getLive(liveId).then(response => {
        this.form = response.data;
        this.ban = true;
        this.title = "封禁直播";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.liveId != null) {
            updateLive(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addLive(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 封禁按钮 */
    banForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          banLive(this.form).then(response => {
            this.$modal.msgSuccess("封禁成功");
            this.ban = false;
            this.getList();
          });
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const liveIds = row.liveId || this.ids;
      this.$modal.confirm('是否确认删除直播编号为"' + liveIds + '"的数据项？').then(function() {
        return delLive(liveIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('live/live/export', {
        ...this.queryParams
      }, `live_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
