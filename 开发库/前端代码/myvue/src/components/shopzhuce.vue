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
  <div class="addgood">
    <p>营业执照</p>
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
    <div slot="tip" class="el-upload__tip">只能上传1张jpg/png/jpeg图片，且不超过2MB</div>
  </el-upload>
  <p>----------------------------------</p>
  <p>身份证照片</p>
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
    <div slot="tip" class="el-upload__tip">只能上传1张jpg/png/jpeg图片，且不超过2MB</div>
  </el-upload>
  <p>----------------------------------</p>
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
      userword: '',
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
position: absolute;/*绝对定位*/
width: 300px;
height: 200px;
text-align: center;/*(让div中的内容居中)*/
left: 50%;
margin-left: -150px;
}
</style>
