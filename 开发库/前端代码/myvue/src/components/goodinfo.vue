<template>
    <div class="div">
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
		<el-row>
			<el-col class="block" :span="12">
			  <el-carousel  height="300px" style="margin-right: 20px;width:300px;float: right;" :autoplay="false">
				<el-carousel-item v-for="i in this.table[0].imgUrl" :key="i" >
				  <el-image style="width: 300px; height:300px"
							:src="i" ></el-image>
				</el-carousel-item>
			  </el-carousel>
			</el-col>
			<el-col class="info" :span="12">
				<p style="font-size:25px">商品名称:{{this.table[0].name}}</p>
				<p style="font-size:25px">{{this.table[0].intro}}</p>
				<p style="color:red; font-size:25px">原价:<span style="text-decoration: line-through">¥{{this.table[0].price}}</span>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 现价:¥{{this.table[0].price*this.table[0].zhekou/10}}</p>
				<el-row>
					<el-col :span="5">折扣：{{this.table[0].zhekou}}</el-col>
					<el-col :span="12">新旧程度：{{this.table[0].newo}}</el-col>
				</el-row>
				<el-row>
					<el-col :span="5">是否议价：{{this.table[0].yijia}}</el-col>
					<el-col :span="12">商品尺寸：{{this.table[0].size}}</el-col>
				</el-row>
				<p>销量：{{this.table[0].shouchu}}</p>
				<p>库存：{{this.table[0].amount}}</p>
				<el-input-number v-model="count" @change="handleChange" :min="1"  label="描述文字"></el-input-number>
				<el-button  style="margin-left: 20px; background-color:red" type="primary" @click="add">加入购物车</el-button>
			</el-col>
		</el-row>
		<el-divider>评论区</el-divider>
		<div class="aaa" id="box">
			<ul v-for="item in data" :key="item" style="box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1)">
				<li>
					<p>用户：{{item.id}}</p>
				</li>
				<li>
					<p>评论：{{item.text}}</p>
				</li>
				<li style="text-align: right;">
					<el-rate v-model="item.rate" show-text disabled style="margin-top:20px"> </el-rate>
				</li>
			</ul>
		</div>
    </div>
</template>
<script>
export default {
  data () {
    return {
		userid: '',
		userword: '',
		data: [
			{
				id:"aaaa",
				text:"noaoaod百度表白地位不代表带不带我把我的",
				rate:4,
			},
			{
				id:"aaaa",
				text:"noaoaod百度表白地位不代表带不带我把我的",
				rate:4,
			},
			{
				id:"aaaa",
				text:"noaoaod百度表白地位不代表带不带我把我的",
				rate:4,
			},
			{
				id:"aaaa",
				text:"noaoaod百度表白地位不代表带不带我把我的",
				rate:4,
			},
			{
				id:"aaaa",
				text:"noaoaod百度表白地位不代表带不带我把我的",
				rate:4,
			},
			{
				id:"aaaa",
				text:"noaoaod百度表白地位不代表带不带我把我的noaoaod百度表白地位不代表带不带我把我的noaoaod百度表白地位不代表带不带我把我的noaoaod百度表白地位不代表带不带我把我的noaoaod百度表白地位不代表带不带我把我的noaoaod百度表白地位不代表带不带我把我的noaoaod百度表白地位不代表带不带我把我的noaoaod百度表白地位不代表带不带我把我的noaoaod百度表白地位不代表带不带我把我的noaoaod百度表白地位不代表带不带我把我的noaoaod百度表白地位不代表带不带我把我的noaoaod百度表白地位不代表带不带我把我的noaoaod百度表白地位不代表带不带我把我的",
				rate:4,
			},
		],
		logo: require('../assets/啊对对队.png'),
		table: [
			{
				imgUrl:"aaaa",
				name:"asd",
				intro:"asdwda",
				price:1000,
				zhekou:5,
				newo:"旧",
				yijia:"是",
				size:"大",
				shouchu:100000,
				amount:100,
			},
		],
      str: [],
      count: 1,
      u: '',
      val: '',
      max: ''
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
    add () {
      if (sessionStorage.getItem('userid') === '' || sessionStorage.getItem('userid') === null) {
        this.$router.push('/login')
      } else {
        let formData = new FormData()
        formData.append('id', sessionStorage.getItem('userid'))
        formData.append('word', sessionStorage.getItem('userword'))
        formData.append('shopid', sessionStorage.getItem('shopid'))
        formData.append('goodid', sessionStorage.getItem('goodid'))
        formData.append('name', this.table[0].name)
        formData.append('price', this.table[0].price * this.table[0].zhekou / 10)
        formData.append('intro', this.table[0].intro)
        formData.append('newo', this.table[0].newo)
        formData.append('fenlei', this.table[0].fenlei)
        formData.append('size', this.table[0].size)
        formData.append('yijia', this.table[0].yijia)
        formData.append('count', this.count)
        formData.append('amount', this.table[0].amount)
        formData.append('imgUrl', this.u)
        let config = {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        }
        this.$axios.post('http://localhost:8080/addcar', formData, config)
          .then(res => {
            alert('添加成功！')
          })
      }
    },
    getData () {
      this.userid = sessionStorage.getItem('userid')
      this.userword = sessionStorage.getItem('userword')
      let formData = new FormData()
      formData.append('goodid', sessionStorage.getItem('goodid'))
      let config = {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }
      this.$axios.post('http://localhost:8080/goodinfo', formData, config)
        .then(res => {
          this.u = res.data[0].imgUrl
          for (var i = 0; i < res.data.length; i++) {
            this.str = res.data[i].imgUrl.split(',')
            this.str.pop()
            res.data[i].imgUrl = this.str
          }
          this.table = res.data
          for (let k = 0; k < this.table.length; k++) {
            for (let j = 0; j < this.table[k].imgUrl.length; j++) {
              //this.table[k].imgUrl[j] = require('C:/Users/13049/Pictures/good/' + this.table[k].imgUrl[j])
            }
          }
          this.max = this.table[0].amount
          this.getData1()
        })
    },
    getData1 () {
      let formData = new FormData()
      formData.append('goodid', sessionStorage.getItem('goodid'))
      let config = {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }
      this.$axios.post('http://localhost:8080/comment', formData, config)
        .then(res => {
          this.data = res.data
        })
    },
    handleChange (currentValue, oldValue) {
      this.$nextTick(() => {
        // alert(this.tableData[index].goodid)
        this.val = oldValue
        if (currentValue > this.table[0].amount) {
          this.count = this.val
          alert('数量超出范围~')
        }
      })
    }
  }
}
</script>
<style>

#box ul{
  padding:10px;
}
#box li{
  list-style: none;
  padding: auto;
  padding:0px;
}
.block {
  margin-top: 50px;
  height: 350px;
  text-align: right;
}
.info {
  margin-top: 30px;
  text-align: left;
}
  .el-header {
    background-color: #B3C0D1;
    color: #333;
    text-align: center;
    line-height: 60px;
  }
  .aaa{
	margin-left: auto;
	margin-right: auto;
	width: 750px;
  }
</style>
