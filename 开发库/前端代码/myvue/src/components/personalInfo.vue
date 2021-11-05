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
	<div class="info">
		<el-row>
			<el-col span="7" class="name">
				<div style="font-size: 75px; margin-top: 50px;">
					<i class="el-icon-user-solid"></i>
				</div>
				<div style="font-size: 25px;">
					<p><b>欢迎您！</b></p>
					<p>{{ruleForm.id}}</p>
				</div>
				<el-button v-if="this.ruleForm.status === '3'" type="primary" @click="shopmanage" round>店铺管理</el-button>
				<el-button v-if="this.ruleForm.status !== '3'" type="primary" @click="shopregister" round>商家注册</el-button>
			</el-col>
			<el-col span="17">
				<el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" style="margin-top: 50px;">
					<el-row>
						<el-col span="8">
							<el-form-item label="姓名：" prop="name">
								<el-input clearable v-model.trim ="ruleForm.name" placeholder="请输入姓名"></el-input>
							</el-form-item>
						</el-col>
						<el-col span="5">
							<el-form-item label="性别：" prop="sex">
								<el-input :disabled="true" clearable v-model.trim="ruleForm.sex" ></el-input>
							</el-form-item>
						</el-col>
						<el-col span="10">
							<el-form-item label="手机号：" prop="phone">
								<el-input clearable oninput="value=value.replace(/^\.+|[^\d.]/g,'')" v-model.trim="ruleForm.phone" placeholder="请输入11位数字"></el-input>
							</el-form-item>
						</el-col>
					</el-row>
					<el-row>
						<el-col span="12">
							<el-form-item label="邮箱：" prop="email">
								<el-input clearable v-model.trim="ruleForm.email" placeholder="请输入邮箱"></el-input>
							</el-form-item>
						</el-col>
						<el-col span="11">
							<el-form-item label="城市：" prop="city">
								<el-input clearable v-model.trim="ruleForm.city" placeholder="请输入城市"></el-input>
							</el-form-item>
						</el-col>
					</el-row>
					<el-row>
						<el-col span="14">
							<el-form-item label="银行卡号：" prop="account">
								<el-input clearable oninput="value=value.replace(/^\.+|[^\d.]/g,'')" v-model.trim="ruleForm.account" placeholder="请输入16位数字"></el-input>
							</el-form-item>
						</el-col>
						<el-col span="7" style="text-align: center;">
							<el-form-item prop="status">
								<el-button type="primary" @click="submitForm('ruleForm')">保存</el-button>
							</el-form-item>
						</el-col>
					</el-row>
				</el-form>
			</el-col>
		</el-row>
	</div>
	<div class="userinfo">
		
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
	  logo: require('../assets/啊对对队.png'),
      ruleForm: {
        status: '',
        email: '',
        sex: '',
        phone: '',
        name: '',
        account: '',
        city: '',
        id: 'aaa',
		
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
		width: 300px;
		height: 150px;
		text-align: center;/*(让div中的内容居中)*/
		top: 50%;
		left: 50%;
		
	}
	.info{
		margin-top: 30px;
		box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
		height: 350px;
		width: 1000px;
		margin-left: auto;
		margin-right: auto;
	}
	.name{
		height: 390px;
		box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
		margin-top: -20px;
		background-color: #3974a7;
		text-align: center;
		color: #ffffff;
	}
</style>
