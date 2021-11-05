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
	<el-container style="height: 700px; margin-top: 20px;">
        <el-aside width="150px"  style="background-color: rgb(96, 160, 255);text-align: center;">
        	<div style="font-size:30px; background-color: rgb(255, 255, 255);margin-top: 20px;" ><b>我的订单</b></div>
        	<el-menu
        		default-active="2"
        		background-color="#60a0ff"
        		active-text-color="#103cff"
        		>
        		<el-menu-item index="1">
        			<i class="el-icon-shopping-cart-1"></i>
        			<span slot="title"  @click="car">购物车</span>
        		</el-menu-item>
        		<el-menu-item index="2">
        			<i class="el-icon-wallet"></i>
        			<span slot="title" @click="shopmanage">已付款</span>
        		</el-menu-item>
        		<el-menu-item index="3">
        			<i class="el-icon-truck"></i>
        			<span slot="title" @click="goodmanage">待收货</span>
        		</el-menu-item>
        		<el-menu-item index="4">
        			<i class="el-icon-finished"></i>
        			<span slot="title" @click="shoplevel">已完成</span>
        		</el-menu-item>
        	</el-menu>
        </el-aside>
		<el-container>
			<el-main style="height:0px;margin-left: auto;margin-right: auto;">
				<el-table :data="tableData.slice((currentPage - 1) * pageSize, currentPage*pageSize)"
				@row-click="openDetails" border>
					<el-table-column prop="shopid" label="商家ID" width="80">
					</el-table-column>
					<el-table-column prop="goodid" label="商品ID" width="100">
					</el-table-column>
					<el-table-column prop="name" label="商品名称" width="80">
					</el-table-column>
					<el-table-column prop="price" label="商品价格" width="90">
					</el-table-column>
					<el-table-column prop="intro" label="商品简介" width="160">
					</el-table-column>
					<el-table-column prop="newo" label="新旧程度" width="80">
					</el-table-column>
					<el-table-column prop="fenlei" label="商品类别" width="80" >
					</el-table-column>
					<el-table-column prop="count" label="购买数量" width="80" >
					</el-table-column>
					<el-table-column prop="address" label="我的地址" width="150" >
					</el-table-column>
					<el-table-column prop="time" label="下单时间" width="140" >
					</el-table-column>
					<el-table-column prop="imgUrl" label="商品图片" width="320" >
						<template slot-scope="scope">
							<el-carousel  style="width: 300px; height:300px" >
								<el-carousel-item v-for="i in scope.row.imgUrl" :key="i">
									<el-image style="width: 300px; height:300px"
										:src="i" ></el-image>
								</el-carousel-item>
							</el-carousel>
						</template>
					</el-table-column>
					<el-table-column  width="90" label="操作">
						<template slot-scope="scope">
							<el-button size="mini" type="primary" @click.native.prevent="deleteRow(scope.$index, tableData)">评价</el-button>
						</template>
					</el-table-column>
				</el-table>
			</el-main>
			<el-footer>
				<el-pagination
					@size-change="sizechange"
					@current-change="currentchange"
					style="text-align: center;"
					background
					:current-page="currentPage"
					:page-size="pageSize"
					layout="prev, pager, next"
					:total="length">
				</el-pagination>
			</el-footer>
		</el-container>
    </el-container>
    <el-dialog
      title="评价"
      :visible.sync="centerDialogVisible"
      width="350px"
      center>
      <el-input placeholder="请填写评价内容" style="width:300px" type="textarea" :rows="2"
        v-model="t"></el-input>
        <el-rate v-model="v" show-text style="margin-top:10px"></el-rate>
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
	  logo: require('../assets/啊对对队.png'),
      tableData: [
				{
					shopid:"123123",
					goodid:"123123",
					name:"澳大阿萨大",
					price:"100",
					intro:"闹闹送i阿东",
					newo:"八成",
					fenlei:"玩具",
					count:100,
					address:"啊等我下次vv",
					time:"2021-11-05",
					imgUrl:"a"
				},
				{
					shopid:"123123",
					goodid:"123123",
					name:"澳大阿萨大",
					price:"100",
					intro:"闹闹送i阿东",
					newo:"八成",
					fenlei:"玩具",
					count:100,
					address:"啊等我下次vv",
					time:"2021-11-05",
					imgUrl:"aaaa"
				},
				{
					shopid:"123123",
					goodid:"123123",
					name:"澳大阿萨大",
					price:"100",
					intro:"闹闹送i阿东",
					newo:"八成",
					fenlei:"玩具",
					count:100,
					address:"啊等我下次vv",
					time:"2021-11-05",
					imgUrl:"aaaa"
				},
				{
					shopid:"123123",
					goodid:"123123",
					name:"澳大阿萨大",
					price:"100",
					intro:"闹闹送i阿东",
					newo:"八成",
					fenlei:"玩具",
					count:100,
					address:"啊等我下次vv",
					time:"2021-11-05",
					imgUrl:"aaaa"
				},
				{
					shopid:"123123",
					goodid:"123123",
					name:"澳大阿萨大",
					price:"100",
					intro:"闹闹送i阿东",
					newo:"八成",
					fenlei:"玩具",
					count:100,
					address:"啊等我下次vv",
					time:"2021-11-05",
					imgUrl:"aaaa"
				},
			],
      currentPage: 1,
      pageSize: 8,
      length: 0,
      index: '',
      rows: '',
      column: '',
      data: [],
      t: '',
      v: 0
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
      this.$router.push('/horder')
    },
    goodmanage () {
      this.$router.push('/shouhuo')
    },
    usermanage () {
      this.$router.push('/car')
    },
    shopmanage () {
      this.$router.push('/payed')
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
      this.$axios.post('http://localhost:8080/horder', formData, config)
        .then(res => {
          for (var i = 0; i < res.data.length; i++) {
            this.str = res.data[i].imgUrl.split(',')
            this.str.pop()
            res.data[i].imgUrl = this.str
          }
          this.tableData = res.data
          for (let k = 0; k < this.tableData.length; k++) {
            for (let j = 0; j < this.tableData[k].imgUrl.length; j++) {
              //this.tableData[k].imgUrl[j] = require('C:/Users/13049/Pictures/good/' + this.tableData[k].imgUrl[j])
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
    },
    sure () {
      this.centerDialogVisible = false
      let formData = new FormData()
      formData.append('id', sessionStorage.getItem('userid'))
      formData.append('word', sessionStorage.getItem('userword'))
      formData.append('goodid', this.column.goodid)
      formData.append('text', this.t)
      formData.append('rate', this.v)
      let config = {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }
      this.$axios.post('http://localhost:8080/addcomment', formData, config)
        .then(res => {
          this.v = 0
          this.t = ''
          alert(res.data)
        })
    },
    quxiao () {
      this.money = 0
      this.centerDialogVisible = false
      this.centerDialogVisible1 = false
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
