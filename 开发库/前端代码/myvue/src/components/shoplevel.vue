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
      					default-active="4"
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
      				<el-main>
      					<el-table :data="tableData.slice((currentPage - 1) * pageSize, currentPage*pageSize)"
      					@row-click="openDetails" border>
      						<el-table-column prop="id" label="商家账号" width="80">
      						</el-table-column>
      						<el-table-column prop="word" label="密码" width="80">
      						</el-table-column>
      						<el-table-column prop="name" label="姓名" width="80">
      						</el-table-column>
      						<el-table-column prop="sex" label="性别" width="60">
      						</el-table-column>
      						<el-table-column prop="phone" label="手机号" width="130">
      						</el-table-column>
      						<el-table-column prop="email" label="邮箱" width="150">
      						</el-table-column>
      						<el-table-column prop="city" label="城市" width="76">
      						</el-table-column>
      						<el-table-column prop="account" label="银行账户" width="180" >
      						</el-table-column>
      						<el-table-column prop="licence" label="营业执照" width="190" >
      						    <template slot-scope="scope">
      						        <el-image style="width: 200px; height: 200px" :src="scope.row.licence" lazy></el-image>
      						    </template>
      						</el-table-column>
      						<el-table-column prop="card" label="身份证照片" width="190" >
      						    <template slot-scope="scope">
      						        <el-image style="width: 200px; height: 200px" :src="scope.row.card" lazy></el-image>
      						    </template>
      						</el-table-column>
      						<el-table-column  prop="level" width="170" label="商家等级">
      						    <template slot-scope="scope">
      						        <el-input-number size="mini" v-model="scope.row.level" @change="(currentValue, oldValue) => handleChange(currentValue, oldValue, scope.$index)"
      						    :min="1"  :max="5" label="描述文字"></el-input-number>
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
  </div>
</template>
<script>
export default {
  data () {
    return {
		userid: '',
		userword: '',
		tableData: [],
		search: '',
		currentPage: 1,
		pageSize: 8,
		length: 0,
		index: '',
		rows: '',
		column: '',
		table: [],
		logo: require('../assets/啊对对队.png')
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
    handleChange (currentValue, oldValue, index) {
      let formData = new FormData()
      formData.append('id', sessionStorage.getItem('userid'))
      formData.append('word', sessionStorage.getItem('userword'))
      formData.append('uid', this.tableData[(this.currentPage - 1) * this.pageSize + index].id)
      formData.append('level', this.tableData[(this.currentPage - 1) * this.pageSize + index].level)
      let config = {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }
      this.$axios.post('http://localhost:8080/changelevel', formData, config)
        .then(res => {
        })
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
      this.$axios.post('http://localhost:8080/getshoplevel', formData, config)
        .then(res => {
          this.tableData = res.data
          for (var i = 0; i < res.data.length; i++) {
            //this.tableData[i].licence = require('C:/Users/13049/Pictures/shop/' + this.tableData[i].licence)
            //this.tableData[i].card = require('C:/Users/13049/Pictures/shop/' + this.tableData[i].card)
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
