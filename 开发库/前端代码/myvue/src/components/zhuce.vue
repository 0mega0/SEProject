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
  <div class="zhuce" >
	<p style="font-size: 35px;"><b>注册</b></p>
    <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="0px" class="demo-ruleForm">
       <el-form-item prop="id">
        <el-input clearable v-model.trim ="ruleForm.id" placeholder="请输入账户id">
			<template slot="prepend">账号</template>
		</el-input>
      </el-form-item>
      <el-form-item  prop="name">
        <el-input clearable v-model.trim ="ruleForm.name" placeholder="请输入姓名">
			<template slot="prepend">姓名</template>
		</el-input>
      </el-form-item>
      <el-form-item  prop="sex">
        <el-input clearable v-model.trim="ruleForm.sex" placeholder="请输入性别，男或女">
			<template slot="prepend">性别</template>
		</el-input>
      </el-form-item>
      <el-form-item prop="phone">
        <el-input clearable oninput="value=value.replace(/^\.+|[^\d.]/g,'')" v-model.trim="ruleForm.phone" placeholder="请输入11位数字">
			<template slot="prepend">手机号</template>
		</el-input>
      </el-form-item>
      <el-form-item prop="email">
        <el-input clearable v-model.trim="ruleForm.email" placeholder="请输入邮箱">
			<template slot="prepend">邮箱</template>
		</el-input>
      </el-form-item>
      <el-form-item prop="city">
        <el-input clearable v-model.trim="ruleForm.city" placeholder="请输入城市">
			<template slot="prepend">城市</template>
		</el-input>
      </el-form-item>
      <el-form-item  prop="acount">
        <el-input clearable oninput="value=value.replace(/^\.+|[^\d.]/g,'')" v-model.trim="ruleForm.acount" placeholder="请输入16位数字">
			<template slot="prepend">银行卡号</template>
		</el-input>
      </el-form-item>
      <el-form-item  prop="pass" >
        <el-input clearable type="password" v-model.trim="ruleForm.pass" autocomplete="off" placeholder="请输入密码">
			<template slot="prepend">密码</template>
		</el-input>
      </el-form-item>
      <el-form-item prop="checkPass">
        <el-input clearable type="password" v-model.trim="ruleForm.checkPass" autocomplete="off" placeholder="请再次输入密码">
			<template slot="prepend">确认密码</template>
		</el-input>
      </el-form-item>
      <el-form-item   prop="verifycode">
      <el-input v-model="ruleForm.verifycode" placeholder="请输入验证码" class="identifyinput">
		  <template slot="prepend">验证码</template>
	  </el-input>
      <el-col :span="8" style="margin-top: 20px;">
        <div class="code" style="margin-left: 50px;margin-bottom: -10px;" @click="refreshCode">
            <security :identifyCode="identifyCode"></security>
        </div>
      </el-col>
    </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
        <el-button @click="resetForm('ruleForm')">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</div>
</template>
<script>
import security from '../components/identify'
export default {
  data () {
    const validateVerifycode = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入验证码'))
      } else if (value !== this.identifyCode) {
        console.log('validateVerifycode:', value)
        callback(new Error('验证码不正确!'))
      } else {
        callback()
      }
    }
    var check = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('该项不能为空'))
      } else {
        callback()
      }
    }
    var checkSex = (rule, value, callback) => {
      if (value === '男' || value === '女') {
        callback()
      } else {
        callback(new Error('此项只能为男或女'))
      }
    }
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'))
      } else {
        if (this.ruleForm.checkPass !== '') {
          this.$refs.ruleForm.validateField('checkPass')
        }
        callback()
      }
    }
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.ruleForm.pass) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    return {
      userid: '',
      userword: '',
      code: '',
      // 随机抽取四位数
      identifyCodes: '1234567890abcdef',
      identifyCode: '',
	  logo: require('../assets/啊对对队.png'),
      ruleForm: {
        pass: '',
        checkPass: '',
        email: null,
        sex: null,
        phone: null,
        name: null,
        acount: null,
        city: null,
        id: '',
        verifycode: ''
      },
      rules: {
        pass: [
          { validator: validatePass, trigger: 'blur' }
        ],
        checkPass: [
          { validator: validatePass2, trigger: 'blur' }
        ],
        name: [
          { validator: check, trigger: 'blur' }
        ],
        city: [
          { validator: check, trigger: 'blur' }
        ],
        phone: [
          { validator: check, trigger: 'blur' },
          { min: 11, max: 11, message: '长度为11 个数字', trigger: 'blur' }
        ],
        sex: [
          { validator: checkSex, trigger: 'blur' }
        ],
        acount: [
          { validator: check, trigger: 'blur' },
          { min: 16, max: 16, message: '长度为16位数字', trigger: 'blur' }
        ],
        email: [
          { validator: check, trigger: 'blur' }
        ],
        id: [
          { validator: check, trigger: 'blur' }
        ],
        verifycode: [
          { validator: validateVerifycode, trigger: 'blur' }
        ]
      }
    }
  },
  components: {
    security
  },
  mounted () {
    this.identifyCode = ''
    this.makeCode(this.identifyCodes, 4)
  },
  methods: {
    car () {
      if (sessionStorage.getItem('userid') === '') {
        this.$router.push('/login')
      } else {
        this.$router.push('/car')
      }
    },
    // 验证码处理
    randomNum (min, max) {
      return Math.floor(Math.random() * (max - min) + min)
    },

    // 刷新验证码
    refreshCode () {
      this.identifyCode = ''
      this.makeCode(this.identifyCodes, 4)
    },
    makeCode (o, l) {
      for (let i = 0; i < l; i++) {
        this.identifyCode += this.identifyCodes[this.randomNum(0, this.identifyCodes.length)]
      }
      console.log(this.identifyCode)
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
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let formData = new FormData()
          formData.append('id', this.ruleForm.id)
          let config = {
            headers: {
              'Content-Type': 'multipart/form-data'
            }
          }
          this.$axios.post('http://localhost:8080/getZhuceUser', formData, config)
            .then(res => {
              if (res.data.length > 0) {
                alert('您已提交申请，请勿重复提交!')
              } else {
                let formData1 = new FormData()
                formData1.append('id', this.ruleForm.id)
                formData1.append('word', this.ruleForm.pass)
                formData1.append('name', this.ruleForm.name)
                formData1.append('sex', this.ruleForm.sex)
                formData1.append('phone', this.ruleForm.phone)
                formData1.append('email', this.ruleForm.email)
                formData1.append('city', this.ruleForm.city)
                formData1.append('account', this.ruleForm.acount)
                let config1 = {
                  headers: {
                    'Content-Type': 'multipart/form-data'
                  }
                }
                this.$axios.post('http://localhost:8080/insertZhuce', formData1, config1)
                  .then(res => {
                  })
                alert('提交成功，请等待管理员确认之后登录!')
              }
            })
        }
      })
    },
    resetForm (formName) {
      this.$refs[formName].resetFields()
    }
  }
}
</script>
<style type="text/css">
.zhuce{
width: 400px;
height: 820px;
text-align: center;
margin-left: auto;
margin-right: auto;
margin-top: 10px;
box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
}
.el-input {
    width: 300px;
  }
</style>
