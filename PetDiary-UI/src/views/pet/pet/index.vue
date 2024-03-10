<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="宠物姓名" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入宠物姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="宠物类型" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择宠物类型" clearable>
          <el-option
            v-for="dict in dict.type.sys_pet_category"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="祖籍" prop="nation">
        <el-input
          v-model="queryParams.nation"
          placeholder="请输入祖籍"
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
          v-hasPermi="['pet:pet:add']"
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
          v-hasPermi="['pet:pet:edit']"
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
          v-hasPermi="['pet:pet:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['pet:pet:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="petList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="宠物id" align="center" prop="petId" />
      <el-table-column label="宠物姓名" align="center" prop="name" />
      <el-table-column label="宠物类型" align="center" prop="type">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_pet_category" :value="scope.row.type"/>
        </template>
      </el-table-column>
      <el-table-column label="封面图片url" align="center" prop="img" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.img" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="祖籍" align="center" prop="nation" />
      <el-table-column label="性格" align="center" prop="character" />
      <el-table-column label="寿命" align="center" prop="life" />
      <el-table-column label="价钱" align="center" prop="price" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['pet:pet:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['pet:pet:remove']"
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

    <!-- 添加或修改宠物类别对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="宠物姓名" prop="name">
          <el-input v-model="form.name" placeholder="请输入宠物姓名" />
        </el-form-item>
        <el-form-item label="宠物类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择宠物类型">
            <el-option
              v-for="dict in dict.type.sys_pet_category"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="英文名" prop="engName">
          <el-input v-model="form.engName" placeholder="请输入英文名" />
        </el-form-item>
        <el-form-item label="封面图片url" prop="img">
          <image-upload v-model="form.img"/>
        </el-form-item>
        <el-form-item label="祖籍" prop="nation">
          <el-input v-model="form.nation" placeholder="请输入祖籍" />
        </el-form-item>
        <el-form-item label="性格" prop="character">
          <el-input v-model="form.character" placeholder="请输入性格" />
        </el-form-item>
        <el-form-item label="易患病" prop="easyOfDisease">
          <el-input v-model="form.easyOfDisease" placeholder="请输入易患病" />
        </el-form-item>
        <el-form-item label="寿命" prop="life">
          <el-input v-model="form.life" placeholder="请输入寿命" />
        </el-form-item>
        <el-form-item label="价钱" prop="price">
          <el-input v-model="form.price" placeholder="请输入价钱" />
        </el-form-item>
        <el-form-item label="简介" prop="des">
          <el-input v-model="form.des" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="体态特征" prop="feature">
          <el-input v-model="form.feature" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="性格特点" prop="characterFeature">
          <el-input v-model="form.characterFeature" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="照顾知识" prop="careKnowledge">
          <el-input v-model="form.careKnowledge" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="喂养要点" prop="feedPoints">
          <el-input v-model="form.feedPoints" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listPet, getPet, delPet, addPet, updatePet } from "@/api/pet/pet";

export default {
  name: "Pet",
  dicts: ['sys_pet_category'],
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
      // 宠物类别表格数据
      petList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        type: null,
        nation: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          { required: true, message: "宠物姓名不能为空", trigger: "blur" }
        ],
        type: [
          { required: true, message: "宠物类型不能为空", trigger: "change" }
        ],
        img: [
          { required: true, message: "封面图片url不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询宠物类别列表 */
    getList() {
      this.loading = true;
      listPet(this.queryParams).then(response => {
        this.petList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        petId: null,
        name: null,
        type: null,
        engName: null,
        img: null,
        nation: null,
        character: null,
        easyOfDisease: null,
        life: null,
        price: null,
        des: null,
        feature: null,
        characterFeature: null,
        careKnowledge: null,
        feedPoints: null
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
      this.ids = selection.map(item => item.petId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加宠物类别";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const petId = row.petId || this.ids
      getPet(petId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改宠物类别";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.petId != null) {
            updatePet(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPet(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const petIds = row.petId || this.ids;
      this.$modal.confirm('是否确认删除宠物类别编号为"' + petIds + '"的数据项？').then(function() {
        return delPet(petIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('pet/pet/export', {
        ...this.queryParams
      }, `pet_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
