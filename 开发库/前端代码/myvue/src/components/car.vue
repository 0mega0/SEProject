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
				<el-table ref="multipleTable" @selection-change="handleSelectionChange"
				:data="tableData.slice((currentPage - 1) * pageSize, currentPage*pageSize)"
				@row-click="openDetails" border>
					<el-table-column type="selection" width="40">
					</el-table-column>
					<el-table-column prop="goodid" label="商品ID" width="0">
					</el-table-column>
					<el-table-column prop="name" label="商品名称" width="80">
					</el-table-column>
					<el-table-column prop="price" label="商品价格" width="90">
					</el-table-column>
					<el-table-column prop="intro" label="商品简介" width="150">
					</el-table-column>
					<el-table-column prop="newo" label="新旧程度/成" width="80">
					</el-table-column>
					<el-table-column prop="fenlei" label="商品类别" width="80" >
					</el-table-column>
					<el-table-column prop="size" label="商品尺寸" width="100" >
					</el-table-column>
					<el-table-column prop="yijia" label="是否议价" width="60" >
					</el-table-column>
					<el-table-column prop="count" label="购买数量" width="210" >
						<template slot-scope="scope">
						<el-input-number v-model="scope.row.count" @change="(currentValue, oldValue) => handleChange(currentValue, oldValue, scope.$index)"
						:min="1"  label="描述文字"></el-input-number>
						</template>
					</el-table-column>
					<el-table-column prop="amount" label="商品库存" width="80" >
					</el-table-column>
					<el-table-column prop="imgUrl" label="商品图片" width="310" >
						<template slot-scope="scope">
							<el-carousel  style="width: 300px; height:300px" >
								<el-carousel-item v-for="i in scope.row.imgUrl" :key="i">
								<el-image style="width: 300px; height:300px"
									:src="i" ></el-image>
								</el-carousel-item>
							</el-carousel>
						</template>
					</el-table-column>
					<el-table-column  width="80" label="操作">
						<template slot-scope="scope">
							<el-button size="mini" type="primary" @click.native.prevent="deleteRow(scope.$index, tableData)">删除</el-button>
						</template>
					</el-table-column>
				</el-table>
			</el-main>
			<el-footer>
				<div style="text-align: right;">
					<el-button
					type="primary" @click="pay" style="margin-right: 80px;">结算</el-button>
				</div>
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
      title="提示"
      :visible.sync="centerDialogVisible"
      width="30%"
      center>
      <span>您将删除此商品，确定吗？</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="quxiao">取 消</el-button>
        <el-button type="primary" @click="sure">确 定</el-button>
      </span>
    </el-dialog>
    <el-dialog
      title="我的地址"
      :visible.sync="centerDialogVisible1"
      width="350px"
      center>
      <el-form ref="form" :model="form" label-width="80px" size="mini">
        <el-form-item label="手机号:">
        <el-input v-model="form.phone" style="width: 160px"></el-input>
        </el-form-item>
         <el-form-item label="姓名:">
        <el-input v-model="form.uname" style="width: 160px"></el-input>
        </el-form-item>
         <el-form-item label="地址:">
        <el-input v-model="form.address" style="width: 160px"></el-input>
        </el-form-item>
        <el-form-item size="large">
        <el-button type="primary" @click="onSubmit">立即结算</el-button>
        <el-button @click="quxiao">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>
<script>
export default {
  data () {
    return {
      userid: '',
	  logo: require('../assets/啊对对队.png'),
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
      multipleSelection: [],
      data: [],
      val: '',
      number: '',
      form: {
        phone: '',
        uname: '',
        address: ''
      },
      money: 0
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
    handleChange (currentValue, oldValue, index) {
      // alert(this.tableData[index].goodid)
      let formData = new FormData()
      formData.append('goodid', this.tableData[index].goodid)
      formData.append('id', sessionStorage.getItem('userid'))
      formData.append('word', sessionStorage.getItem('userword'))
      formData.append('count', this.tableData[(this.currentPage - 1) * this.pageSize + index].count)
      let config = {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }
      this.$axios.post('http://localhost:8080/changecount', formData, config)
        .then(res => {
        })
    },
    pay () {
      if (this.multipleSelection.length === 0) {
        alert('请您选择商品!')
      } else {
        var c = 0
        for (var j = 0; j < this.multipleSelection.length; j++) {
          if (this.multipleSelection[j].count > this.multipleSelection[j].amount) {
            c++
          }
        }
        if (c > 0) {
          alert('购买数量超出范围~')
        } else {
          for (var i = 0; i < this.multipleSelection.length; i++) {
            this.money += this.multipleSelection[i].count * this.multipleSelection[i].price
          }
          this.data = this.multipleSelection
          alert('您将付款' + parseFloat(this.money).toFixed(2) + '元')
          this.centerDialogVisible1 = true
        }
      }
    },
    onSubmit () {
      if (this.money > sessionStorage.getItem('money')) {
        alert('金额不足，请充值后重新下单!')
      } else {
        var aaa = sessionStorage.getItem('money') - parseFloat(this.money).toFixed(2)
        sessionStorage.setItem('money', aaa)
        // alert(sessionStorage.getItem('money'))
        let formData = new FormData()
        formData.append('id', sessionStorage.getItem('userid'))
        formData.append('word', sessionStorage.getItem('userword'))
        for (var i = 0; i < this.data.length; i++) {
          formData.append('goodid', this.data[i].goodid)
        }
        formData.append('phone', this.form.phone)
        formData.append('uname', this.form.uname)
        formData.append('address', this.form.address)
        formData.append('money', parseFloat(this.money).toFixed(2))
        let config = {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        }
        this.$axios.post('http://localhost:8080/pay', formData, config)
          .then(res => {
            this.getData()
            this.centerDialogVisible1 = false
            alert('下单成功!')
          })
      }
    },
    toggleSelection (rows) {
      if (rows) {
        rows.forEach(row => {
          this.$refs.multipleTable.toggleRowSelection(row)
        })
      } else {
        this.$refs.multipleTable.clearSelection()
      }
    },
    handleSelectionChange (val) {
      this.multipleSelection = val
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
      // alert(sessionStorage.getItem('money'))
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
      this.$axios.post('http://localhost:8080/car', formData, config)
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
          this.length = this.tableData.length
        })
    },
    deleteRow (index, rows) {
      this.centerDialogVisible = true
      this.index = (this.currentPage - 1) * 4 + index
      this.rows = rows
    },
    sure () { // 将商品从购物车删除
      this.centerDialogVisible = false
      this.rows.splice(this.index, 1)
      let formData = new FormData()
      formData.append('id', sessionStorage.getItem('userid'))
      formData.append('word', sessionStorage.getItem('userword'))
      formData.append('goodid', this.column.goodid)
      let config = {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }
      this.$axios.post('http://localhost:8080/dgoodcar', formData, config)
        .then(res => {
          this.$notify({
            title: '成功',
            message: '操作成功',
            type: 'success'
          })
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
