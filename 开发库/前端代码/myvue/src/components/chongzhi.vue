<template>
    <div>
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
  <div class="login" >
    <p>余额 :{{m}}元</p>
    <el-input  clearable  v-model.trim="v" autocomplete="off" placeholder="请输入充值金额/¥"  style="width: 250px"></el-input>
    <el-button  class="c" type="primary" @click="chongzhi">充值</el-button>
  </div>
  </div>
</template>

<script>

export default {
  data () {
    return {
      userid: '',
      userword: '',
      m: 0,
	  logo: require('../assets/啊对对队.png'),
      v: ''
    }
  },
  mounted () {
    this.get()
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
    get () { // 登录按钮
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
      this.$axios.post('http://localhost:8080/personal', formData, config
      )
        .then(Response => {
          this.m = Response.data[0].money
          sessionStorage.setItem('money', this.m)
        })
    },
    chongzhi () {
      let formData = new FormData()
      formData.append('id', sessionStorage.getItem('userid'))
      formData.append('word', sessionStorage.getItem('userword'))
      formData.append('money', this.m)
      formData.append('v', this.v)
      let config = {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }
      this.$axios.post('http://localhost:8080/chongzhi', formData, config
      )
        .then(Response => {
          this.get()
        })
    }
  }
}
</script>
<style type="text/css">
.login{
position: absolute;/*绝对定位*/
width: 300px;
height: 200px;
text-align: center;/*(让div中的内容居中)*/
top: 50%;
left: 50%;
margin-top: -200px;
margin-left: -150px;
}
.c {
    margin-top: 20px;
}
</style>
