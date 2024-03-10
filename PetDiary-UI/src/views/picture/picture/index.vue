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
      <el-form-item label="内容描述" prop="description">
        <el-input
          v-model="queryParams.description"
          placeholder="请输入内容描述"
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
          v-hasPermi="['picture:picture:add']"
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
          v-hasPermi="['picture:picture:edit']"
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
          v-hasPermi="['picture:picture:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['picture:picture:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="pictureList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="内容ID" align="center" prop="contentId" />
      <el-table-column label="标题" align="center" prop="title" />
      <el-table-column label="内容描述" align="center" prop="description" />
      <el-table-column label="图片地址" align="center" prop="coverPath" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.coverPath" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="分类ID" align="center" prop="categoryId" />
      <el-table-column label="内容发布人ID" align="center" prop="userId" />
      <el-table-column label="内容状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_content_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleBan(scope.row)"
            v-hasPermi="['picture:picture:edit']"
          >封禁/恢复</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['picture:picture:remove']"
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

    <!-- 添加或修改图文管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入标题" />
        </el-form-item>
        <el-form-item label="内容描述" prop="description">
          <el-input v-model="form.description" placeholder="请输入内容描述" />
        </el-form-item>
        <el-form-item label="图片地址" prop="coverPath">
          <image-upload v-model="form.coverPath"/>
        </el-form-item>
        <el-form-item label="分类ID" prop="categoryId">
          <el-input v-model="form.categoryId" placeholder="请输入分类ID" />
        </el-form-item>
        <el-form-item label="内容发布人ID" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入内容发布人ID" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 添加或修改图文管理对话框 -->
    <el-dialog :title="title" :visible.sync="banOpen" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="banRules" label-width="80px">
        <el-form-item label="内容状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in dict.type.sys_content_status"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="封禁内容" prop="rejectInfo">
          <el-input v-model="form.rejectInfo" type="textarea" placeholder="请输入内容" />
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
import { listPicture, getPicture, delPicture, addPicture, updatePicture, banPicture} from "@/api/picture/picture";

export default {
  name: "Picture",
  dicts: ['sys_content_status'],
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
      // 图文管理表格数据
      pictureList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示封禁弹出层
      banOpen: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        title: null,
        description: null,
        userId: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        title: [
          { required: true, message: "标题不能为空", trigger: "blur" }
        ],
        coverPath: [
          { required: true, message: "图片地址不能为空", trigger: "blur" }
        ],
        userId: [
          { required: true, message: "内容发布人ID不能为空", trigger: "blur" }
        ],
        status: [
          { required: true, message: "内容状态不能为空", trigger: "change" }
        ],
      },
      // 表单校验
      banRules: {
        rejectInfo: [
          { required: true, message: "封禁内容不能为空", trigger: "blur" }
        ],
        status: [
          { required: true, message: "内容状态不能为空", trigger: "change" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询图文管理列表 */
    getList() {
      this.loading = true;
      listPicture(this.queryParams).then(response => {
        this.pictureList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.banOpen = true;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        contentId: null,
        title: null,
        description: null,
        contentType: null,
        coverPath: null,
        videoPath: null,
        categoryId: null,
        userId: null,
        status: null,
        rejectInfo: null,
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
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.contentId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加图文管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const contentId = row.contentId || this.ids
      getPicture(contentId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改图文管理";
      });
    },
    /** 封禁按钮操作 */
    handleBan(row) {
      this.reset();
      const contentId = row.contentId || this.ids
      getPicture(contentId).then(response => {
        this.form = response.data;
        this.banOpen = true;
        this.title = "封禁图文";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.contentId != null) {
            updatePicture(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPicture(this.form).then(response => {
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
          banPicture(this.form).then(response => {
            this.$modal.msgSuccess("封禁成功");
            this.banOpen = false;
            this.getList();
          });
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const contentIds = row.contentId || this.ids;
      this.$modal.confirm('是否确认删除图文管理编号为"' + contentIds + '"的数据项？').then(function() {
        return delPicture(contentIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('picture/picture/export', {
        ...this.queryParams
      }, `picture_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
