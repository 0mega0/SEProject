<template>
  <div >
    <div>
      <div style="text-align: center;"><el-image :src="logo" @click="main"></el-image></div>
      <el-row style="background-color: #e1e1e1;">
    	  <el-col span="12" style="text-align: left;">
    		  <span v-if="this.userid != ''" style="margin-left: 10px;">欢迎使用啊对对队平台购物，祝您购物愉快</span>
    		  <span v-if="this.userid == ''" style="margin-left: 10px;color: red;">请您先登陆，才能正常购物</span>
    	  </el-col>
    	  <el-col span="12" style="text-align: right;">
    		<el-link  style="font-size: 20px;margin-right: 10px;" type="primary" icon="el-icon-s-custom" v-if="this.userid === ''"  @click="denglu">登录</el-link>
    		<el-link  style="font-size: 20px;margin-right: 10px;" type="primary" v-if="this.userid != ''"  @click="user">{{this.userid}}</el-link>
    		<el-link  style="font-size: 20px;margin-right: 10px;" type="danger" icon="el-icon-shopping-cart-1" @click="car">购物车</el-link>
    		<el-link  style="font-size: 20px;margin-right: 10px;" type="warning" icon="el-icon-money" @click="chong">充值</el-link>
    		<el-link  style="font-size: 20px;margin-right: 10px;" icon="el-icon-back" @click="tui">退出</el-link>
    	  </el-col>
      </el-row>
    </div>
      <el-container style="height: 700px; border: 1px solid #eee">
        <el-header  style="background-color: #60a0ff;text-align: center;">
            <p6 style="font-size:30px;"><b>后台管理审核系统</b></p6>
        </el-header>
		<el-container>
			<el-aside width="200px">
				<el-menu
					default-active="1"
					class="el-menu-vertical-demo"
					style="height: 700px;"
					>
					<el-menu-item index="1">
						<span slot="title" @click="usermanage">用户注册审核</span>
					</el-menu-item>
					<el-menu-item index="2">
						<span slot="title" @click="shopmanage">商家注册审核</span>
					</el-menu-item>
					<el-menu-item index="3">
						<span slot="title" @click="goodmanage">上架商品审核</span>
					</el-menu-item>
					<el-menu-item index="4">
						<span slot="title" @click="shoplevel">商家等级管理</span>
					</el-menu-item>
				</el-menu>
			</el-aside>
			<el-container>
				<el-main style="height:0;flex-grow:1;">
					<el-table :data="tableData.slice((currentPage - 1) * pageSize, currentPage*pageSize)"
					@row-click="openDetails" border>
						<el-table-column prop="id" label="商家ID" width="80">
						</el-table-column>
						<el-table-column prop="goodid" label="商品ID" width="80">
						</el-table-column>
						<el-table-column prop="name" label="商品名称" width="80">
						</el-table-column>
						<el-table-column prop="price" label="商品原价" width="80">
						</el-table-column>
						<el-table-column prop="zhekou" label="商品折扣" width="80">
						</el-table-column>
						<el-table-column prop="intro" label="商品简介" width="120">
						</el-table-column>
						<el-table-column prop="newo" label="新旧程度" width="50">
						</el-table-column>
						<el-table-column prop="fenlei" label="商品类别" width="100" >
						</el-table-column>
						<el-table-column prop="size" label="商品尺寸" width="50" >
						</el-table-column>
						<el-table-column prop="yijia" label="是否议价" width="50" >
						</el-table-column>
						<el-table-column prop="amount" label="上架数量" width="80" >
						</el-table-column>
						<el-table-column prop="imgUrl" label="商品图片" width="300" >
							<template slot-scope="scope">
								<el-image style="width: 193px; height: 180px"
								v-for="item in scope.row.imgUrl"  :key="item"
								:src="item" ></el-image>
							</template>
						</el-table-column>
						<el-table-column  width="110" label="操作">
							<template slot-scope="scope">
								<el-button size="mini" type="primary" @click.native.prevent="deleteRow(scope.$index, tableData)">允许上架</el-button>
							</template>
						</el-table-column>
						<el-table-column align="right" >
							<template slot="header" slot-scope="scope">
								<el-row>
									<el-col span="18">
										<el-input width="120" v-model="search" v-on:input ="inputFunc" clearable="true" placeholder="输入关键字搜索"/>
									</el-col>
									<el-col span="4">
										<el-button type="primary" @click="clear">全部</el-button>
									</el-col>
								</el-row>
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
		</el-container>
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
	  logo: require('../assets/啊对对队.png'),
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
            //  this.tableData[k].imgUrl[j] = require('C:/Users/13049/Pictures/good/' + this.tableData[k].imgUrl[j])
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
