<template>
  <div >
    <div>
      <el-button  type="primary" style="margin-left: 100px" @click="main">首页</el-button>
      <el-button  v-if="this.userid === ''" type="primary" style="margin-left: 950px" @click="denglu">登录</el-button>
      <el-button  v-if="this.userid != ''" type="primary" style="margin-left: 950px" @click="user">{{this.userid}}</el-button>
      <el-button  type="primary"  @click="car">购物车</el-button>
      <el-button  type="primary"  @click="chong">充值</el-button>
      <el-button  type="primary"  @click="tui">退出</el-button>
    </div>
      <el-container style="height: 700px; border: 1px solid #eee">
        <el-header  style="background-color: rgb(238, 241, 246)">
            <p6 style="margin-left: 25px;height: 100px;font-size:22px;">后台管理审核系统</p6>
            <el-button  type="primary" style="margin-left: 600px" @click="usermanage">用户注册审核</el-button>
            <el-button  type="primary" @click="shopmanage" >商家注册审核</el-button>
            <el-button  type="primary" @click="goodmanage" >上架商品审核</el-button>
            <el-button  type="primary" @click="shoplevel" >商家等级管理</el-button>
        </el-header>
        <el-main style="height:0;flex-grow:1;">
            <el-col :span="3">
            <el-input style="margin-left: 910px" width="120" v-model="search" v-on:input ="inputFunc" clearable="true" placeholder="输入关键字搜索"/>
            </el-col>
            <el-button style="margin-left: 960px" type="primary" @click="clear">全部</el-button>
            <el-table :data="tableData.slice((currentPage - 1) * pageSize, currentPage*pageSize)"
            @row-click="openDetails" border>
                <el-table-column prop="id" label="商家ID" width="80">
                </el-table-column>
                <el-table-column prop="goodid" label="商品ID" width="100">
                </el-table-column>
                <el-table-column prop="name" label="商品名称" width="50">
                </el-table-column>
                <el-table-column prop="price" label="商品原价" width="60">
                </el-table-column>
                <el-table-column prop="zhekou" label="商品折扣" width="50">
                </el-table-column>
                <el-table-column prop="intro" label="商品简介" width="120">
                </el-table-column>
                <el-table-column prop="newo" label="新旧程度" width="50">
                </el-table-column>
                <el-table-column prop="fenlei" label="商品类别" width="50" >
                </el-table-column>
                <el-table-column prop="size" label="商品尺寸" width="100" >
                </el-table-column>
                <el-table-column prop="yijia" label="是否议价" width="50" >
                </el-table-column>
                <el-table-column prop="amount" label="上架数量" width="50" >
                </el-table-column>
                <el-table-column prop="imgUrl" label="商品图片" width="600" >
                    <template slot-scope="scope">
                        <el-image style="width: 193px; height: 180px"
                        v-for="item in scope.row.imgUrl"  :key="item"
                        :src="item" ></el-image>
                    </template>
                </el-table-column>
                <el-table-column  width="100" label="操作">
                    <template slot-scope="scope">
                        <el-button size="mini" type="primary" @click.native.prevent="deleteRow(scope.$index, tableData)">允许上架</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-main>
        <el-footer>
            <el-pagination
                @size-change="sizechange"
                @current-change="currentchange"
                style="margin-left: 1010px"
                background
                :current-page="currentPage"
                :page-size="pageSize"
                layout="prev, pager, next"
                :total="length">
            </el-pagination>
        </el-footer>
    </el-container>
    <el-dialog
      title="提示"
      :visible.sync="centerDialogVisible"
      width="30%"
      center>
      <span>您将同意此商品上架，确定吗？</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="quxiao">取 消</el-button>
        <el-button type="primary" @click="sure">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
export default {
  data () {
    return {
      userid: '',
      userword: '',
      centerDialogVisible: false,
      tableData: [],
      search: '',
      currentPage: 1,
      pageSize: 8,
      length: 0,
      index: '',
      rows: '',
      column: '',
      table: [],
      str: []
    }
  },
  mounted () {
    this.getData()
  },
  methods: {
    car () {
      if (sessionStorage.getItem('userid') === '') {
        this.$router.push('/login')
      } else {
        this.$router.push('/car')
      }
    },
    main () {
      this.$router.push('/')
    },
    denglu () {
      this.$router.push('/login')
    },
    chong () {
      if (sessionStorage.getItem('userid') === '') {
        this.$router.push('/login')
      } else {
        this.$router.push('/chongzhi')
      }
    },
    user () {
      this.$router.push('/personalinfo')
    },
    tui () {
      sessionStorage.setItem('userid', '')
      sessionStorage.setItem('userword', '')
      this.$router.push('/')
    },
    shoplevel () {
      this.$router.push('/shoplevel')
    },
    goodmanage () {
      this.$router.push('/goodadmin')
    },
    usermanage () {
      this.$router.push('/admin')
    },
    shopmanage () {
      this.$router.push('/shopadmin')
    },
    inputFunc () { // 动态搜索
      this.currentPage = 1
      this.getList()
    },
    openDetails (row) { // 获取点击行的数据
      this.column = row
    },
    getData () { // 后台获取数据
      this.userid = sessionStorage.getItem('userid')
      this.userword = sessionStorage.getItem('userword')
      let formData = new FormData()
      formData.append('id', sessionStorage.getItem('userid'))
      formData.append('word', sessionStorage.getItem('userword'))
      let config = {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }
      this.$axios.post('http://localhost:8080/getgood', formData, config)
        .then(res => {
          for (var i = 0; i < res.data.length; i++) {
            this.str = res.data[i].imgUrl.split(',')
            this.str.pop()
            res.data[i].imgUrl = this.str
          }
          /* alert(res.data[0].imgUrl[0]) */
          this.tableData = res.data
          for (let k = 0; k < this.tableData.length; k++) {
            for (let j = 0; j < this.tableData[k].imgUrl.length; j++) {
              this.tableData[k].imgUrl[j] = require('C:/Users/13049/Pictures/good/' + this.tableData[k].imgUrl[j])
            }
          }
          this.length = this.tableData.length
          this.table = this.tableData
        })
    },
    getList () { // 根据id搜索结果集
      this.tableData = this.tableData.filter((item, index) =>
        item.id.includes(this.search)
      )
      this.length = this.tableData.length
    },
    clear () { // 搜索转全部
      this.search = ''
      this.currentPage = 1
      this.getData()
    },
    deleteRow (index, rows) {
      this.centerDialogVisible = true
      this.index = (this.currentPage - 1) * 4 + index
      this.rows = rows
    },
    sure () { // 确认允许上架，更改商品status
      this.centerDialogVisible = false
      this.rows.splice(this.index, 1)
      this.table = this.tableData
      let formData = new FormData()
      formData.append('id', sessionStorage.getItem('userid'))
      formData.append('word', sessionStorage.getItem('userword'))
      formData.append('goodid', this.column.goodid)
      let config = {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }
      this.$axios.post('http://localhost:8080/allowgood', formData, config)
        .then(res => {
          this.$notify({
            title: '成功',
            message: '操作成功',
            type: 'success'
          })
        })
    },
    quxiao () {
      this.centerDialogVisible = false
      this.$notify({
        title: '警告',
        message: '操作取消',
        type: 'warning'
      })
    },
    sizechange (val) {
      this.pageSize = val
    },
    currentchange (val) {
      this.currentPage = val
    }
  }
}
</script>
<style>
  .el-header {
    background-color: #B3C0D1;
    color: #333;
    line-height: 50px;
  }
</style>
