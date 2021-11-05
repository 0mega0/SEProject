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
  <div class="userinfo">
    <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" >
       <el-form-item label="账号：" prop="id">
        <el-input  :disabled="true" clearable v-model ="ruleForm.id" ></el-input>
      </el-form-item>
      <el-form-item label="姓名：" prop="name">
        <el-input clearable v-model.trim ="ruleForm.name" placeholder="请输入姓名"></el-input>
      </el-form-item>
      <el-form-item label="性别：" prop="sex">
        <el-input :disabled="true" clearable v-model.trim="ruleForm.sex" ></el-input>
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
      <el-form-item label="银行卡号：" prop="account">
        <el-input clearable oninput="value=value.replace(/^\.+|[^\d.]/g,'')" v-model.trim="ruleForm.account" placeholder="请输入16位数字"></el-input>
      </el-form-item>
      <el-form-item prop="status">
        <el-button type="primary" @click="submitForm('ruleForm')">保存</el-button>
        <el-button v-if="this.ruleForm.status === '3'" v-model.trim="ruleForm.status" type="primary" @click="shopmanage">店铺管理</el-button>
        <el-button v-if="this.ruleForm.status !== '3'" v-model.trim="ruleForm.status" type="primary" @click="shopregister">商家注册</el-button>
      </el-form-item>
    </el-form>
  </div>
</div>
</template>
<script>
export default {
  data () {
    var check = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('该项不能为空'))
      } else {
        callback()
      }
    }
    return {
      userid: '',
      userword: '',
      ruleForm: {
        status: '',
        email: '',
        sex: '',
        phone: '',
        name: '',
        account: '',
        city: '',
        id: ''
      },
      rules: {
        name: [
          { validator: check, trigger: 'blur' }
        ],
        phone: [
          { validator: check, trigger: 'blur' },
          { min: 11, max: 11, message: '长度为11 个数字', trigger: 'blur' }
        ],
        account: [
          { validator: check, trigger: 'blur' },
          { min: 16, max: 16, message: '长度为16位数字', trigger: 'blur' }
        ]
      }
    }
  },
  mounted () {
    this.getInfo()
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
    getInfo () {
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
      ).then(Response => {
        if (Response.data.length === 1) {
          this.ruleForm.id = Response.data[0].id
          this.ruleForm.name = Response.data[0].name
          this.ruleForm.sex = Response.data[0].sex
          this.ruleForm.phone = Response.data[0].phone
          this.ruleForm.email = Response.data[0].email
          this.ruleForm.city = Response.data[0].city
          this.ruleForm.account = Response.data[0].account
          this.ruleForm.status = Response.data[0].status
        } else {
          this.$router.push('/login')
        }
      })
    },
    shopmanage () {
      this.$router.push('/shopmanage')
    },
    shopregister () {
      this.$router.push('/shopzhuce')
    },
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let formData = new FormData()
          formData.append('name', this.ruleForm.name)
          formData.append('phone', this.ruleForm.phone)
          formData.append('email', this.ruleForm.email)
          formData.append('city', this.ruleForm.city)
          formData.append('account', this.ruleForm.account)
          formData.append('id', sessionStorage.getItem('userid'))
          formData.append('word', sessionStorage.getItem('userword'))
          let config = {
            headers: {
              'Content-Type': 'multipart/form-data'
            }
          }
          this.$axios.post('http://localhost:8080/updateInfo', formData, config)
            .then(res => {
              alert('保存成功!')
            })
        }
      })
    }
  }
}
</script>
<style type="text/css">
.userinfo{
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
