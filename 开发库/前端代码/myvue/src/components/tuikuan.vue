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
            <p6 style="margin-left: 25px;height: 100px;font-size:22px;">店铺管理系统</p6>
            <el-button  type="primary" style="margin-left: 500px" @click="allgood">全部宝贝</el-button>
            <el-button  type="primary" @click="daifahuo" >待发货订单</el-button>
            <el-button  type="primary" @click="tuikuan" >退款订单</el-button>
            <el-button  type="primary" @click="yiwancheng" >已完成订单</el-button>
            <el-button  type="primary" @click="goodmanage" >已下架商品</el-button>
            <el-button  type="primary" @click="addgood">添加商品</el-button>
        </el-header>
        <el-main style="height:0;flex-grow:1;">
            <el-table :data="tableData.slice((currentPage - 1) * pageSize, currentPage*pageSize)"
            @row-click="openDetails" border>
                <el-table-column prop="id" label="用户ID" width="70">
                </el-table-column>
                <el-table-column prop="goodid" label="商品ID" width="90">
                </el-table-column>
                <el-table-column prop="name" label="商品名称" width="80">
                </el-table-column>
                <el-table-column prop="price" label="商品总价" width="70">
                </el-table-column>
                <el-table-column prop="intro" label="商品简介" width="140">
                </el-table-column>
                <el-table-column prop="newo" label="新旧程度/成" width="70">
                </el-table-column>
                <el-table-column prop="fenlei" label="商品类别" width="50" >
                </el-table-column>
                <el-table-column prop="size" label="商品尺寸" width="70" >
                </el-table-column>
                <el-table-column prop="yijia" label="是否议价" width="50" >
                </el-table-column>
                <el-table-column prop="count" label="商品数量" width="50" >
                </el-table-column>
                <el-table-column prop="amount" label="商品库存" width="50" >
                </el-table-column>
                <el-table-column prop="address" label="用户地址" width="90" >
                </el-table-column>
                <el-table-column prop="time" label="下单时间" width="90" >
                </el-table-column>
                <el-table-column prop="imgUrl" label="商品图片" width="305" >
                    <template slot-scope="scope">
                        <el-carousel  style="width: 300px; height:300px" >
                            <el-carousel-item v-for="i in scope.row.imgUrl" :key="i">
                            <el-image style="width: 300px; height:300px"
                                :src="i" ></el-image>
                            </el-carousel-item>
                        </el-carousel>
                    </template>
                </el-table-column>
                <el-table-column  width="180" label="操作">
                    <template slot-scope="scope">
                        <el-button size="mini" type="primary" @click.native.prevent="deleteRow(scope.$index, tableData)">退款</el-button>
                        <el-button size="mini" type="primary" @click.native.prevent="d(scope.$index, tableData)">拒绝退款</el-button>
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
      <span>是否同意用户退款？</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="quxiao">取 消</el-button>
        <el-button type="primary" @click="sure">确 定</el-button>
      </span>
    </el-dialog>
    <el-dialog
      title="提示"
      :visible.sync="centerDialogVisible1"
      width="30%"
      center>
      <span>是否拒绝用户退款?</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="quxiao">取 消</el-button>
        <el-button type="primary" @click="sure1">确 定</el-button>
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
      centerDialogVisible1: false,
      tableData: [],
      currentPage: 1,
      pageSize: 8,
      length: 0,
      index: '',
      rows: '',
      column: '',
      str: [],
      data: []
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
    allgood () {
      this.$router.push('/shopmanage')
    },
    daifahuo () {
      this.$router.push('/sendgood')
    },
    tuikuan () {
      this.$router.push('/tuikuan')
    },
    yiwancheng () {
      this.$router.push('/finished')
    },
    addgood () {
      this.$router.push('/addgood')
    },
    goodmanage () {
      this.$router.push('/xiajia')
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
      this.$axios.post('http://localhost:8080/tui', formData, config)
        .then(res => {
          for (var i = 0; i < res.data.length; i++) {
            this.str = res.data[i].imgUrl.split(',')
            this.str.pop()
            res.data[i].imgUrl = this.str
          }
          this.tableData = res.data
          for (let k = 0; k < this.tableData.length; k++) {
            for (let j = 0; j < this.tableData[k].imgUrl.length; j++) {
              this.tableData[k].imgUrl[j] = require('C:/Users/13049/Pictures/good/' + this.tableData[k].imgUrl[j])
            }
          }
          for (let f = 0; f < this.tableData.length; f++) {
            this.tableData[f].address = '手机号:' + this.tableData[f].phone + '\xa0\xa0\xa0\xa0\xa0\xa0姓名:' + this.tableData[f].uname +
            '\xa0\xa0\xa0\xa0\xa0\xa0地址:' + this.tableData[f].address
          }
          this.length = this.tableData.length
        })
    },
    deleteRow (index, rows) {
      this.centerDialogVisible = true
      this.index = (this.currentPage - 1) * 4 + index
      this.rows = rows
    },
    d (index, rows) {
      this.centerDialogVisible1 = true
      this.index = (this.currentPage - 1) * 4 + index
      this.rows = rows
    },
    sure1 () { // 拒绝退款
      this.centerDialogVisible1 = false
      let formData = new FormData()
      formData.append('id', this.column.id)
      formData.append('shopid', sessionStorage.getItem('userid'))
      formData.append('word', sessionStorage.getItem('userword'))
      formData.append('goodid', this.column.goodid)
      let config = {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }
      this.$axios.post('http://localhost:8080/jutui', formData, config)
        .then(res => {
          this.getData()
          this.$notify({
            title: '成功',
            message: '操作成功',
            type: 'success'
          })
        })
    },
    sure () { // 同意退款
      this.centerDialogVisible = false
      let formData = new FormData()
      formData.append('id', this.column.id)
      formData.append('shopid', sessionStorage.getItem('userid'))
      formData.append('word', sessionStorage.getItem('userword'))
      formData.append('goodid', this.column.goodid)
      formData.append('price', parseFloat(this.column.price * this.column.count).toFixed(2))
      formData.append('count', this.column.count)
      formData.append('amount', this.column.amount)
      let config = {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }
      this.$axios.post('http://localhost:8080/allowtui', formData, config)
        .then(res => {
          this.getData()
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
