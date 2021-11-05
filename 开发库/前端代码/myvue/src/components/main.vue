<template>
	<div id="box" >
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
		<div>
			<div style="text-align: center;margin: 20px;">
				<el-input style="width: 470px;" v-model="search" v-on:input ="inputFunc"
				clearable="true" placeholder="输入关键字搜索"/>
				<p></p>
				<el-button type="primary"  @click="sheng">价格升序</el-button>
				<el-button type="primary"  @click="jiang">价格降序</el-button>
				<el-button type="primary"  @click="zong">综合排序</el-button>
			</div>
			<div class="maingood">
				<ul v-for="item in table" style="margin:10px;" :key="item">
					<li>
						<el-carousel height="250px" width="200px" :autoplay="false">
							<el-carousel-item v-for="i in item.imgUrl" :key="i">
								<el-image style="width: 200px; height: 250px" :src="i" ></el-image>
							</el-carousel-item>
						</el-carousel>
					</li>
					<div class="t">
						<li>
							<p style="color:red; font-size:25px">¥{{item.price}}</p>
						</li>
						<li>
							<p><el-link @click="info(item)":underline="false" style="font-size: 20px;">{{item.intro}}</el-link></p>
						</li>
						<li>
							<el-link @click="shop(item)">{{item.id}}的店铺</el-link>
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
      tableData: [],
      table: [
		  
	  ],
      str: [],
      search: '',
	  logo: require('../assets/啊对对队.png')
    }
  },
  mounted () {
    this.getData()
  },
  methods: {
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
    car () {
      if (sessionStorage.getItem('userid') === '') {
        this.$router.push('/login')
      } else {
        this.$router.push('/car')
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
    shop (item) {
      sessionStorage.setItem('shopid', item.id)
      this.$router.push('/usershop')
    },
    info (item) {
      sessionStorage.setItem('shopid', item.id)
      sessionStorage.setItem('goodid', item.goodid)
      this.$router.push('/goodinfo')
    },
    inputFunc () { // 动态搜索
      this.getList()
    },
    getList () { // 根据id搜索结果集
      this.table = this.tableData.filter((item, index) =>
        item.intro.includes(this.search)
      )
    },
    clear () { // 搜索转全部
      this.search = ''
      this.getList()
    },
    sheng () {
      this.table.sort((a, b) => {
        return a.price - b.price
      })
    },
    jiang () {
      function sortData (a, b) {
        return b.price - a.price
      }
      this.table.sort(sortData)
    },
    zong () {
      this.getData()
    },
    getData () { // 后台获取数据
      this.userid = sessionStorage.getItem('userid')
      this.userword = sessionStorage.getItem('userword')
      let formData = new FormData()
      let config = {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }
      this.$axios.post('http://localhost:8080/getAllgood', formData, config)
        .then(res => {
          // alert(res.data[0].imgUrl)
          for (var i = 0; i < res.data.length; i++) {
            this.str = res.data[i].imgUrl.split(',')
            this.str.pop()
            res.data[i].imgUrl = this.str
          }
          // alert(this.str.length)
          this.table = res.data
          for (let k = 0; k < this.table.length; k++) {
            for (let j = 0; j < this.table[k].imgUrl.length; j++) {
              //this.table[k].imgUrl[j] = require('C:/Users/13049/Pictures/good/' + this.table[k].imgUrl[j])
            }
          }
          this.tableData = this.table
        })
    }
  }
}
</script>
<style>
.maingood{
	background-color: #BCE4FF;
	width: 1450px;
	margin-left: auto;
	margin-right: auto;
}
.t {
  background-color: rgb(255, 255, 255);
  text-align: center;
}
#box ul{
  float: left;
  width: 200px;
  height: 400px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
  text-align: center;
  padding:0;
}
#box li{
  list-style: none;
  padding:auto;
}
.el-carousel__item:nth-child(2n) {
  background-color: #cdeafd;
}
.el-carousel__item:nth-child(2n+1) {
  background-color: #0055ff;
}
</style>
