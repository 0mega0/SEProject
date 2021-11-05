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
  <div class="all">
    <div>
      <el-container>
        <el-header>{{this.shopid}}的店铺</el-header>
      </el-container>
    </div>
    <div class="main" id="box" >
    <ul v-for="item in table" :key="item">
      <div class="block">
      <li>
          <el-carousel height="230px" width="150px" >
            <el-carousel-item v-for="i in item.imgUrl" :key="i">
              <el-image style="width: 200px; height: 250px"
                        :src="i" ></el-image>
            </el-carousel-item>
          </el-carousel>
      </li>
      </div>
      <div class="t">
      <li>
        <p style="color:red; font-size:25px">¥{{item.price}}</p>
      </li>
      <li>
        <p @click="info(item)" style="text-decoration:underline">{{item.intro}}</p>
      </li>
      </div>
    </ul>
  </div>
  </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
      userid: '',
      userword: '',
      table: [],
      str: [],
      shopid: ''
    }
  },
  mounted () {
    this.getData()
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
    info (item) {
      sessionStorage.setItem('goodid', item.goodid)
      this.$router.push('/goodinfo')
    },
    getData () { // 后台获取数据
      this.userid = sessionStorage.getItem('userid')
      this.userword = sessionStorage.getItem('userword')
      this.shopid = sessionStorage.getItem('shopid')
      let formData = new FormData()
      formData.append('id', sessionStorage.getItem('shopid'))
      let config = {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }
      this.$axios.post('http://localhost:8080/shopgood', formData, config)
        .then(res => {
          for (var i = 0; i < res.data.length; i++) {
            this.str = res.data[i].imgUrl.split(',')
            this.str.pop()
            res.data[i].imgUrl = this.str
          }
          // alert(this.str.length)
          this.table = res.data
          for (let k = 0; k < this.table.length; k++) {
            for (let j = 0; j < this.table[k].imgUrl.length; j++) {
              this.table[k].imgUrl[j] = require('C:/Users/13049/Pictures/good/' + this.table[k].imgUrl[j])
            }
          }
          // alert(this.table[0].goodid)
        })
    }
  }
}
</script>
<style>
.all {
  background-color: rgb(244, 248, 250);
}
.t {
  background-color: rgb(236, 248, 255);
}
#box ul{
  float: left;
  width: 200px;
  height: 500px;
}
#box li{
  list-style: none;
  padding: auto;
}
.el-carousel__item:nth-child(2n) {
  background-color: #bce4ff;
}
.el-carousel__item:nth-child(2n+1) {
  background-color: #cdebff;
}
.el-header {
    background-color: #d7e9ff;
    color: rgb(9, 34, 49);
    text-align: center;
    line-height: 60px;
  }
</style>
