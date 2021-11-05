<template>
<div>
  <div>
      <el-button  type="primary" style="margin-left: 100px" @click="main">首页</el-button>
      <el-button  v-if="this.userid === ''" type="primary" style="margin-left: 950px" @click="denglu">登录</el-button>
      <el-button  v-if="this.userid != ''" type="primary" style="margin-left: 950px" @click="user">{{this.userid}}</el-button>
      <el-button  type="primary"  @click="car">购物车</el-button>
      <el-button  type="primary"  @click="chong">充值</el-button>
      <el-button  type="primary"  @click="tui">退出</el-button>
    </div>
  <div class="zhuce" >
    <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
       <el-form-item label="账号：" prop="id">
        <el-input clearable v-model.trim ="ruleForm.id" placeholder="请输入账户id"></el-input>
      </el-form-item>
      <el-form-item label="姓名：" prop="name">
        <el-input clearable v-model.trim ="ruleForm.name" placeholder="请输入姓名"></el-input>
      </el-form-item>
      <el-form-item label="性别：" prop="sex">
        <el-input clearable v-model.trim="ruleForm.sex" placeholder="请输入性别，男或女"></el-input>
      </el-form-item>
      <el-form-item label="手机号：" prop="phone">
        <el-input clearable oninput="value=value.replace(/^\.+|[^\d.]/g,'')" v-model.trim="ruleForm.phone" placeholder="请输入11位数字"></el-input>
      </el-form-item>
      <el-form-item label="邮箱：" prop="email">
        <el-input clearable v-model.trim="ruleForm.email" placeholder="请输入邮箱"></el-input>
      </el-form-item>
      <el-form-item label="城市：" prop="city">
        <el-input clearable v-model.trim="ruleForm.city" placeholder="请输入城市"></el-input>
      </el-form-item>
      <el-form-item label="银行卡号：" prop="acount">
        <el-input clearable oninput="value=value.replace(/^\.+|[^\d.]/g,'')" v-model.trim="ruleForm.acount" placeholder="请输入16位数字"></el-input>
      </el-form-item>
      <el-form-item label="密码：" prop="pass" >
        <el-input clearable type="password" v-model.trim="ruleForm.pass" autocomplete="off" placeholder="请输入密码"></el-input>
      </el-form-item>
      <el-form-item label="确认密码：" prop="checkPass">
        <el-input clearable type="password" v-model.trim="ruleForm.checkPass" autocomplete="off" placeholder="请再次输入密码"></el-input>
      </el-form-item>
      <el-form-item  label="验证码：" prop="verifycode">
      <el-input v-model="ruleForm.verifycode" placeholder="请输入验证码" class="identifyinput"></el-input>
      <el-col :span="8">
        <div class="code" @click="refreshCode">
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
position: absolute;/*绝对定位*/
width: 300px;
height: 150px;
text-align: center;/*(让div中的内容居中)*/
top: 50%;
left: 50%;
margin-top: -300px;
margin-left: -150px;
}
</style>
