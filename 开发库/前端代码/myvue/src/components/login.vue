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
        <el-button  type="primary" @click="login">登录</el-button>
        <el-button  @click="resetForm">注册</el-button>
      </el-form-item>
    </el-form>
  </div>
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
position: absolute;/*绝对定位*/
width: 300px;
height: 200px;
text-align: center;/*(让div中的内容居中)*/
top: 50%;
left: 50%;
margin-top: -200px;
margin-left: -150px;
}
</style>
