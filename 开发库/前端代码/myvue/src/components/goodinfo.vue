<template>
    <div class="div">
      <div>
      <el-button  type="primary" style="margin-left: 100px" @click="main">首页</el-button>
      <el-button  v-if="this.userid === ''" type="primary" style="margin-left: 950px" @click="denglu">登录</el-button>
      <el-button  v-if="this.userid != ''" type="primary" style="margin-left: 950px" @click="user">{{this.userid}}</el-button>
      <el-button  type="primary"  @click="car">购物车</el-button>
      <el-button  type="primary"  @click="chong">充值</el-button>
      <el-button  type="primary"  @click="tui">退出</el-button>
    </div>
      <div>
        <div class="block">
          <el-carousel  style="width: 300px; height:800px">
            <el-carousel-item v-for="i in this.table[0].imgUrl" :key="i">
              <el-image style="width: 300px; height:300px"
                        :src="i" ></el-image>
            </el-carousel-item>
          </el-carousel>
        </div>
        <div class="info">
          <p style="font-size:25px">商品名称:{{this.table[0].name}}</p>
          <p style="font-size:25px">{{this.table[0].intro}}</p>
          <p style="color:red; font-size:25px">原价:¥{{this.table[0].price}}
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 现价:¥{{this.table[0].price*this.table[0].zhekou/10}}</p>
          <p >折扣：{{this.table[0].zhekou}}</p>
          <p>新旧程度：{{this.table[0].newo}}</p>
          <p>是否议价：{{this.table[0].yijia}}</p>
          <p>商品尺寸：{{this.table[0].size}}</p>
          <p>销量：{{this.table[0].shouchu}}</p>
          <p>库存：{{this.table[0].amount}}</p>
          <el-input-number v-model="count" @change="handleChange" :min="1"  label="描述文字"></el-input-number>
          <el-button  style="margin-left: 20px; background-color:red" type="primary" @click="add">加入购物车</el-button>
        </div>
      </div>
      <div class="aaa" id="box">
          <span>评论区：</span>
          <ul v-for="item in data" :key="item">
      <li>
          <p>用户：{{item.id}}</p>
      </li>
      <li>
           <p>评论：{{item.text}}</p>
      </li>
      <li>
          <el-rate v-model="item.rate" show-text disabled style="margin-top:20px"> </el-rate>
      </li>
    </ul>
    </div>
    </div>
</template>
<script>
export default {
  data () {
    return {
      userid: '',
      userword: '',
      data: [],
      table: [],
      str: [],
      count: 1,
      u: '',
      val: '',
      max: ''
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
    add () {
      if (sessionStorage.getItem('userid') === '' || sessionStorage.getItem('userid') === null) {
        this.$router.push('/login')
      } else {
        let formData = new FormData()
        formData.append('id', sessionStorage.getItem('userid'))
        formData.append('word', sessionStorage.getItem('userword'))
        formData.append('shopid', sessionStorage.getItem('shopid'))
        formData.append('goodid', sessionStorage.getItem('goodid'))
        formData.append('name', this.table[0].name)
        formData.append('price', this.table[0].price * this.table[0].zhekou / 10)
        formData.append('intro', this.table[0].intro)
        formData.append('newo', this.table[0].newo)
        formData.append('fenlei', this.table[0].fenlei)
        formData.append('size', this.table[0].size)
        formData.append('yijia', this.table[0].yijia)
        formData.append('count', this.count)
        formData.append('amount', this.table[0].amount)
        formData.append('imgUrl', this.u)
        let config = {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        }
        this.$axios.post('http://localhost:8080/addcar', formData, config)
          .then(res => {
            alert('添加成功！')
          })
      }
    },
    getData () {
      this.userid = sessionStorage.getItem('userid')
      this.userword = sessionStorage.getItem('userword')
      let formData = new FormData()
      formData.append('goodid', sessionStorage.getItem('goodid'))
      let config = {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }
      this.$axios.post('http://localhost:8080/goodinfo', formData, config)
        .then(res => {
          this.u = res.data[0].imgUrl
          for (var i = 0; i < res.data.length; i++) {
            this.str = res.data[i].imgUrl.split(',')
            this.str.pop()
            res.data[i].imgUrl = this.str
          }
          this.table = res.data
          for (let k = 0; k < this.table.length; k++) {
            for (let j = 0; j < this.table[k].imgUrl.length; j++) {
              this.table[k].imgUrl[j] = require('C:/Users/13049/Pictures/good/' + this.table[k].imgUrl[j])
            }
          }
          this.max = this.table[0].amount
          this.getData1()
        })
    },
    getData1 () {
      let formData = new FormData()
      formData.append('goodid', sessionStorage.getItem('goodid'))
      let config = {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }
      this.$axios.post('http://localhost:8080/comment', formData, config)
        .then(res => {
          this.data = res.data
        })
    },
    handleChange (currentValue, oldValue) {
      this.$nextTick(() => {
        // alert(this.tableData[index].goodid)
        this.val = oldValue
        if (currentValue > this.table[0].amount) {
          this.count = this.val
          alert('数量超出范围~')
        }
      })
    }
  }
}
</script>
<style>

#box ul{
  padding:10px;
}
#box li{
  list-style: none;
  padding: auto;
  padding:0px;
}
.block {
  margin-top: 50px;
  margin-left: 300px;
  float: left;
  width: 300px;
  height: 500px;
}
.info {
  margin-top: 30px;
  margin-left: 50px;
  float: left;
}
  .el-header {
    background-color: #B3C0D1;
    color: #333;
    text-align: center;
    line-height: 60px;
  }
  .aaa{
    margin-top:600px;

 position:absolute;

 left:45%
  }
</style>
