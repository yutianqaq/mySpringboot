<template>
  <div>
    <div style="padding: 10px 0">
      <el-input style="width: 300px" placeholder="请输入姓名" suffix-icon="el-icon-search" v-model="username"
                width="80"></el-input>
      <el-input style="width: 300px" placeholder="请输入地址" suffix-icon="el-icon-position"
                v-model="address"></el-input>
      <el-input style="width: 300px" placeholder="请输入邮箱" suffix-icon="el-icon-message"
                v-model="email"></el-input>
      <el-button class="ml-5" type="primary" @click="load()">搜索</el-button>
      <el-button type="warning" @click="reset()">重置</el-button>
    </div>
    <div>
      <el-dialog title="用户信息新增" :visible.sync="dialogFormVisibleAdd">
        <el-form :model="form">
          <el-form-item label="用户名" :label-width="formLabelWidth">
            <el-input v-model="form.username" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="昵称" :label-width="formLabelWidth">
            <el-input v-model="form.nickname" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="手机号" :label-width="formLabelWidth">
            <el-input v-model="form.phone" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="邮箱" :label-width="formLabelWidth">
            <el-input v-model="form.email" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="地址" :label-width="formLabelWidth">
            <el-input v-model="form.address" autocomplete="off"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisibleAdd = false">取 消</el-button>
          <el-button type="primary" @click="modifyFormInfo()">确 定</el-button>
        </div>
      </el-dialog>
      <el-button type="primary" @click="userAdd()">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
      <el-button type="danger" @click="delBatch">批量删除 <i class="el-icon-remove-outline"></i></el-button>
      <el-upload action="http://localhost:9900/user/import"
                 :show-file-list="false" accept="xlsx"
                 :on-success="handleImportSuccess"
                 :on-error="handleImportError"
                 style="display: inline-block;margin-right: 5px">
        <el-button type="primary">导入 <i class="el-icon-bottom"></i></el-button>
      </el-upload>
      <el-button type="primary" @click="exp">导出 <i class="el-icon-top"></i></el-button>
    </div>
    <el-dialog title="用户信息编辑" :visible.sync="dialogFormVisible">
      <el-form :model="form">
        <el-form-item label="用户名" :label-width="formLabelWidth">
          <el-input v-model="form.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="昵称" :label-width="formLabelWidth">
          <el-input v-model="form.nickname" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="手机号" :label-width="formLabelWidth">
          <el-input v-model="form.phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" :label-width="formLabelWidth">
          <el-input v-model="form.email" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="地址" :label-width="formLabelWidth">
          <el-input v-model="form.address" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="modifyFormInfo()">确 定</el-button>
      </div>
    </el-dialog>
    <el-table :data="tableData" border stripe header-cell-class-name="headerBg" ref="multipleTable"
              @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="id" label="ID" width="120"></el-table-column>
      <el-table-column prop="username" label="用户名" width="120"></el-table-column>
      <el-table-column prop="nickname" label="昵称" width="120"></el-table-column>
      <el-table-column prop="address" label="地址" width="120"></el-table-column>
      <el-table-column prop="email" label="邮箱" width="120"></el-table-column>
      <el-table-column prop="phone" label="电话"></el-table-column>
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <el-button type="success" @click="handleEdit(scope.row)">编辑<i class="el-icon-edit"></i></el-button>
          <el-button type="danger" @click="handleDelete(scope.row)">删除<i class="el-icon-remove-outline"></i>
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <div style="padding: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[5, 10, 15, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>
  </div>
</template>

<script>
export default {
  name: "UserView",
  data() {
    return {
      tableData: [],
      id: null,
      total: 0,
      pageNum: 1,
      pageSize: 5,
      username: "",
      address: "",
      email: "",
      collapseBtnClass: 'el-icon-s-fold',
      isCollapsed: false,
      sideWidth: 200,
      logoTextShow: true,
      headerBg: 'headerBg',
      dialogTableVisible: false,
      dialogFormVisible: false,
      dialogTableVisibleAdd: false,
      dialogFormVisibleAdd: false,
      form: {
        id: '',
        username: '',
        nickname: '',
        phone: '',
        email: '',
        address: '',
      },
      multipleSelection: [],
      formLabelWidth: '120px'
    }
  },
  created() {
    // 请求分页数据
    this.load()
  },
  methods: {
    load: function () {
      this.request.get("/user/page", {
        params: {
          pageSize: this.pageSize,
          pageNum: this.pageNum,
          username: this.username,
          address: this.address,
          email: this.email
        }
      }).then(res => {
        console.log(res)
        this.tableData = res.data.records
        console.log(this.tableData)
        this.total = res.data.total
      })
    },
    reset() {
      this.username = ''
      this.address = ''
      this.email = ''
    },
    deleteDataById(id) {
      this.request.delete("/user/" + id).then(res => {
      })
    },
    modifyFormInfo () {
      this.request.post("/user", {
        "id": this.form.id,
        "username": this.form.username,
        "nickname": this.form.nickname,
        "phone": this.form.phone,
        "email": this.form.email,
        "address": this.form.address,
      }).then(res => {
        console.log(res)
        this.dialogFormVisible = false
        this.dialogFormVisibleAdd = false
        this.load()
      })
    },
    userAdd: function () {
      this.form = {}
      this.dialogFormVisibleAdd = true
    },
    //点击函数exp, 导出表格
    exp(){
      window.open("http://localhost:9900/user/export")
    },
    toggleSelection(rows) {
      if (rows) {
        rows.forEach(row => {
          this.$refs.multipleTable.toggleRowSelection(row);
        });
      } else {
        this.$refs.multipleTable.clearSelection();
      }
    },
    handleSelectionChange(val) {
      // console.log(val);
      this.multipleSelection = val;
    },
    //导入,钩子函数进行页面的提示跟成功后页面的刷新
    handleImportSuccess(){
      this.$message.success("导入成功")
      this.load();
    },
    handleImportError() {
      this.$message.error("导入失败，请检查格式")
      this.load();
    },
    delBatch() {
      // 转换
      let ids = this.multipleSelection.map(v => v.id)
      this.$confirm('此操作将永久删除该用户信息, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$message({
          type: 'success',
          message: '删除成功!',
        });
        this.request.post("/user/del/batch", ids).then(res => {
        })
        this.load()
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });

    },
    handleDelete: function (row) {
      this.$confirm('此操作将永久删除该用户信息, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$message({
          type: 'success',
          message: '删除成功!',
        });
        this.deleteDataById(row.id)
        this.load()
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    handleEdit: function (row) {
      this.dialogFormVisible = true
      this.form = row
      console.log(this.form)
    },
    handleSizeChange: function (pageSize) {
      // console.log(pageSize)
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange: function (pageNum) {
      // console.log(pageNum)
      this.pageNum = pageNum
      this.load()
    }
  }
}
</script>

<style scoped>

</style>