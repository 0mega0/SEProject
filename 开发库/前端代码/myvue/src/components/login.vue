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
	<el-row>
		<el-col :span="12">
			<el-image style="width: 100%; height: 100%;margin-top: 100px;margin-left: 100px;" :src="back">
				
			</el-image>
		</el-col>
		<el-col :span="12">
			<div class="login" >
				<el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="auto" class="demo-ruleForm" >
				  <p4>校园二手交易平台登录系统</p4>
				  <el-form-item  label-width="300px" >
				  </el-form-item>
				  <el-form-item label="账号:" label-width="50px"  prop="pass">
					<el-input  clearable name="name" type="username" v-model.trim="ruleForm.pass" autocomplete="off" placeholder="请输入账号"  style="width: 250px"></el-input>
				  </el-form-item>
				  <el-form-item label="密码:" label-width="50px" prop="checkPass">
					<el-input clearable name="word" type="password" v-model.trim="ruleForm.checkPass" autocomplete="off" placeholder="请输入密码" style="width: 250px"></el-input>
				  </el-form-item>
				  <el-form-item label-width="95px">
					<el-button  type="warning" @click="login">登录</el-button>
					<el-button  @click="resetForm">注册</el-button>
				  </el-form-item>
				</el-form>
			</div>
		</el-col>
	</el-row>
  </div>
</template>

<script>

export default {
  data () {
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入账号'))
      }
    }
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'))
      }
    }
    return {
		logo: require('../assets/啊对对队.png'),
		back: require('../assets/购物.png'),
		ruleForm: {
		pass: '',
		checkPass: ''
		},
		userid: '',
		userword: '',
		rules: {
			pass: [
			  { validator: validatePass, trigger: 'blur' }
			],
			checkPass: [
			  { validator: validatePass2, trigger: 'blur' }
			]
		}
	}
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
      if (this.$g.userid === '') {
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
    login () { // 登录按钮
      let formData = new FormData()
      formData.append('id', this.ruleForm.pass)
      formData.append('word', this.ruleForm.checkPass)
      let config = {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }
      this.$axios.post('http://localhost:8080/login', formData, config
      )
        .then(Response => {
          this.get()
          if (Response.data === 'admin') {
            sessionStorage.setItem('userid', this.ruleForm.pass)
            sessionStorage.setItem('userword', this.ruleForm.checkPass)
            this.$router.push('/admin')
          } else if (Response.data === 'user') {
            sessionStorage.setItem('userid', this.ruleForm.pass)
            sessionStorage.setItem('userword', this.ruleForm.checkPass)
            this.$router.push('/')
          } else {
            alert(Response.data)
          }
        })
    },
    get () {
      let formData = new FormData()
      formData.append('id', this.ruleForm.pass)
      formData.append('word', this.ruleForm.checkPass)
      let config = {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }
      this.$axios.post('http://localhost:8080/login1', formData, config
      )
        .then(Response => {
          // alert(sessionStorage.getItem('money'))
          sessionStorage.setItem('money', Response.data[0].money)
        })
    },
    resetForm () { // 注册按钮
      this.$router.push('/zhuce')
    }
  }
}
</script>
<style type="text/css">
.login{
width: 300px;
height: 200px;
text-align: right;/*(让div中的内容居中)*/
margin-right: 300px;
margin-top: 150px;
float: right;
padding: 40px;
border-radius: 15px;
box-shadow: 0 15px 22px 10px rgba(0, 0, 0, 0.3);
background: rgba(255, 255, 255, .3);
}
.sss{
	width: 99.2vw;
	height: 88vh;
	background: url(../assets/zarda.jpg) no-repeat;
	background-size: 100% 100%;position: relative;
}
</style>
