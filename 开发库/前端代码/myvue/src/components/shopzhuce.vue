<template>
<div>
  <div>
  	<div style="text-align: center;"><el-image :src="logo" @click="main"></el-image></div>
  	<el-row style="background-color: #e1e1e1;">
  		<el-col span="12" style="text-align: left;">
  			<span v-if="this.userid != ''" style="margin-left: 10px;">欢迎进入店铺管理系统</span>
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
	<div class="addgood">
		<el-row :gutter="10"  style="margin-bottom: 50px; margin-top: 40px;">
			<el-col :span="11" 
			style="box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);border: 2px dashed lightblue;margin-right: 40px; height: 300px;">
				<p style="font-size: 30px;">营业执照</p>
				<el-upload
					  :limit="1"
					  accept="image/png,image/jpg,image/jpeg"
					  action="#"
					  :auto-upload="false"
					  :on-change="fileChange"
					  :on-remove="handleRemove"
					  :file-list="shopimg"
					  list-type="picture">
					<el-button size="small" type="primary">点击上传</el-button>
					<div slot="tip" class="el-upload__tip" style="font-size: 15px;">只能上传1张jpg/png/jpeg图片，且不超过2MB</div>
				</el-upload>
			</el-col>
			<el-col :span="11" style="box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);border: 2px dashed lightblue;height: 300px;">
				<p style="font-size: 30px;">身份证照片</p>
				<el-upload
					  :limit="1"
					  accept="image/png,image/jpg,image/jpeg"
					  action="#"
					  :auto-upload="false"
					  :on-remove="handleRemove"
					  :file-list="identify"
					  :on-change="fileChange1"
					  list-type="picture">
					<el-button size="small" type="primary">点击上传</el-button>
					<div slot="tip" class="el-upload__tip" style="font-size: 15px;">只能上传1张jpg/png/jpeg图片，且不超过2MB</div>
				</el-upload>
			</el-col>
		</el-row>
		<el-button type="primary" @click="onSubmit">立即注册</el-button>
		<el-button @click="quxiao">取消</el-button>
	</div>
</div>
</template>
<script>
export default {
  data () {
    return {
      userid: '',
	  logo: require('../assets/啊对对队.png'),
      userword: '',
	  active: 0,
      file1: {
        file: ''
      },
      file2: {
        file: ''
      },
      shopimg: [],
      identify: []
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
	next() {
	        if (this.active++ > 2) this.active = 0;
	},
    get () {
      this.userid = sessionStorage.getItem('userid')
      this.userword = sessionStorage.getItem('userword')
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
    fileChange (file, shopimg) {
      const isImage = file.raw.type === 'image/png' || file.raw.type === 'image/jpg' || file.raw.type === 'image/jpeg'
      const isLt5M = file.size < 1024 * 1024 * 1
      if (!isImage) {
        this.$message.error('上传只能是png,jpg,jpeg格式!')
        shopimg.splice(-1, 1)
      }
      if (!isLt5M) {
        this.$message.error('上传图片大小不能超过 1MB!')
        shopimg.splice(-1, 1)
      }
      console.log('change')
      console.log(file)
      this.file1.file = file.raw
      console.log(this.file1.file)
      console.log(shopimg)
    },
    fileChange1 (file, identify) {
      const isImage = file.raw.type === 'image/png' || file.raw.type === 'image/jpg' || file.raw.type === 'image/jpeg'
      const isLt5M = file.size < 1024 * 1024 * 1
      if (!isImage) {
        this.$message.error('上传只能是png,jpg,jpeg格式!')
        identify.splice(-1, 1)
      }
      if (!isLt5M) {
        this.$message.error('上传图片大小不能超过 1MB!')
        identify.splice(-1, 1)
      }
      console.log('change')
      console.log(file)
      this.file2.file = file.raw
      console.log(this.file2.file)
      console.log(identify)
    },
    handleRemove (file, fileList) {
      console.log(file, fileList)
    },
    onSubmit () {
    /*  alert(this.file1) */
      let formData = new FormData()
      formData.append('licence', this.file1.file)
      formData.append('card', this.file2.file)
      formData.append('id', sessionStorage.getItem('userid'))
      formData.append('word', sessionStorage.getItem('userword'))
      let config = {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }
      this.$axios.post('http://localhost:8080/shopzhuce', formData, config
      ).then(res => {
        alert(res.data)
      })
    },
    quxiao () {
      this.$router.push('/personalinfo')
    }
  }
}
</script>
<style type="text/css">
.addgood{
width: 500px;
height: 200px;
text-align: center;/*(让div中的内容居中)*/
margin-left: auto;
margin-right: auto;
margin-top: 150px;
}
</style>
