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
    <div class="left">
    <p>商品图片</p>
    <el-upload
          :limit="3"
          accept="image/png,image/jpg,image/jpeg"
          action="#"
          :auto-upload="false"
          :on-change="fileChange"
          :on-remove="handleRemove"
          :file-list="fileList"
          list-type="picture">
    <el-button size="small" type="primary">点击上传</el-button>
    <div slot="tip" class="el-upload__tip">只能上传3张jpg/png/jpeg图片，且不超过2MB</div>
  </el-upload>
  <p>----------------------------------</p>
    </div>
    <div class="righht">
    <el-form ref="form" status-icon :rules="rules" :model="form" label-width="100px">
        <el-form-item label="商品名称:" prop="name">
            <el-input clearable
              v-model.trim="form.name" placeholder="请输入商品名称">
              </el-input>
        </el-form-item>
        <el-form-item  label="商品价格:" prop="price">
              <el-input
              clearable oninput="value=value.replace(/^\.+|[^\d.]/g,'')"
              v-model.trim="form.price" placeholder="请输入价格">
              <i slot="suffix" style="font-size:30px" >¥</i>
              </el-input>
        </el-form-item>
         <el-form-item label="商品折扣:" prop="zhekou">
            <el-input-number v-model="form.zhekou" @change="handleChange" :precision="1" :step="0.1"
            :min="1" :max="10" label="描述文字"></el-input-number>
        </el-form-item>
        <el-form-item label="商品简介:" prop="intro">
            <el-input clearable
              v-model.trim="form.intro" placeholder="请输入商品简介">
              </el-input>
        </el-form-item>
        <el-form-item label="新旧程度:" prop="new">
            <el-input-number v-model="form.new" @change="handleChange" :min="1" :max="10" label="描述文字"></el-input-number>
        </el-form-item>
        <el-form-item label="是否议价:" prop="yijia">
            <el-input clearable
              v-model.trim="form.yijia" placeholder="请输入是或否">
              </el-input>
        </el-form-item>
        <el-form-item label="商品分类:" prop="fenlei">
          <el-input
              clearable
              v-model.trim="form.fenlei" placeholder="请输入商品类别">
              </el-input>
        </el-form-item>
        <el-form-item label="商品尺寸:" prop="size">
            <el-input clearable
              v-model.trim="form.size" placeholder="请输入商品尺寸">
              </el-input>
        </el-form-item>
        <el-form-item label="上架数量:" prop="amount">
          <el-input-number v-model="form.amount" @change="handleChange" :min="1" :max="99999" label="描述文字"></el-input-number>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click.native="shangjia('form')">立即上架</el-button>
            <el-button @click="quxiao">取消</el-button>
        </el-form-item>
    </el-form>
    </div>
  </div>
   </div>
</template>
<script>
export default {
  data () {
    var check = (rule, value, callback) => {
      if (value === '是' || value === '否') {
        callback()
      } else {
        callback(new Error('此项只能为是或否'))
      }
    }
    var checkKong = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('该项不能为空'))
      } else {
        callback()
      }
    }
    return {
      fileList: [],
      userid: '',
      userword: '',
      form: {
        size: '',
        new: 10,
        name: '',
        zhekou: 10.0,
        fenlei: '',
        intro: '',
        price: '',
        amount: 1,
        yijia: ''
      },
      rules: {
        yijia: [
          { validator: check, trigger: 'blur' }
        ],
        name: [
          { validator: checkKong, trigger: 'blur' }
        ],
        price: [
          { validator: checkKong, trigger: 'blur' }
        ],
        intro: [
          { validator: checkKong, trigger: 'blur' }
        ],
        fenlei: [
          { validator: checkKong, trigger: 'blur' }
        ],
        size: [
          { validator: checkKong, trigger: 'blur' }
        ]
      }
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
    fileChange (file, fileList) {
      const isImage = file.raw.type === 'image/png' || file.raw.type === 'image/jpg' || file.raw.type === 'image/jpeg'
      const isLt5M = file.size < 1024 * 1024 * 1
      if (!isImage) {
        this.$message.error('上传只能是png,jpg,jpeg格式!')
        fileList.splice(-1, 1)
      }
      if (!isLt5M) {
        this.$message.error('上传图片大小不能超过 1MB!')
        fileList.splice(-1, 1)
      }
      this.fileList = fileList
      console.log('change')
      console.log(file)
      console.log(fileList)
    },
    handleChange (value) {
      console.log(value)
    },
    handleRemove (file, fileList) {
      this.fileList = fileList
      console.log(file, fileList)
    },
    shangjia (form) {
      this.$refs[form].validate((valid) => {
        if (valid) {
          let formData = new FormData()
          this.fileList.forEach(file => {
            formData.append('file', file.raw)
          })
          formData.append('id', sessionStorage.getItem('userid'))
          formData.append('word', sessionStorage.getItem('userword'))
          formData.append('price', this.form.price)
          formData.append('name', this.form.name)
          formData.append('zhekou', this.form.zhekou)
          formData.append('intro', this.form.intro)
          formData.append('newo', this.form.new)
          formData.append('fenlei', this.form.fenlei)
          formData.append('size', this.form.size)
          formData.append('yijia', this.form.yijia)
          formData.append('amount', this.form.amount)
          let config = {
            headers: {
              'Content-Type': 'multipart/form-data'
            }
          }
          this.$axios.post('http://localhost:8080/addgood', formData, config
          ).then(res => {
            if (res.data === 'fail') {
              this.$router.push('/')
            } else {
              alert(res.data)
            }
          })
        }
      })
    },
    quxiao () {
      this.$router.push('/shopmanage')
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
