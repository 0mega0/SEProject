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
		<div class="login" >
			<i class="el-icon-money" style="color: gold;font-size: 150px;"></i>
			<p>余额 :{{m}}元</p>
			<el-row>
				<el-col :span="20">
					<el-input  clearable  v-model.trim="v" autocomplete="off" placeholder="请输入充值金额/¥"  style="width: 250px"></el-input>
				</el-col>
				<el-col :span="4">
					<el-button  class="c" type="primary" @click="chongzhi">充值</el-button>
				</el-col>
			</el-row>
		</div>
		<el-row>
		</el-row>
		<div style="width: 1150px;text-align: center;margin-left: auto;margin-right: auto;margin-top: 10px;">
			<el-row :gutter="20">
				<el-col :span="6">
					<el-button plain type="success" @click="chongzhi1" style="font-size: 50px;width: 250px;">50元</el-button>
				</el-col>
				<el-col :span="6">
					<el-button plain type="success" @click="chongzhi2" style="font-size: 50px;width: 250px;">100元</el-button>
				</el-col>
				<el-col :span="6">
					<el-button plain type="success" @click="chongzhi3" style="font-size: 50px;width: 250px;">500元</el-button>
				</el-col>
				<el-col :span="6">
					<el-button plain type="success" @click="chongzhi4" style="font-size: 50px;width: 250px;">1000元</el-button>
				</el-col>
			</el-row>
		</div>
		<div style="width: 1150px;text-align: center;margin-left: auto;margin-right: auto;margin-top: 50px;">
			<el-row :gutter="10">
				<el-col :span="6">
					<el-button plain type="success" @click="chongzhi5" style="font-size: 50px;width: 250px;">5000元</el-button>
				</el-col>
				<el-col :span="6">
					<el-button plain type="success" @click="chongzhi6" style="font-size: 50px;width: 250px;">10000元</el-button>
				</el-col>
				<el-col :span="6">
					<el-button plain type="success" @click="chongzhi7" style="font-size: 50px;width: 250px;">50000元</el-button>
				</el-col>
				<el-col :span="6">
					<el-button plain type="success" @click="chongzhi8" style="font-size: 50px;width: 250px;">100000元</el-button>
				</el-col>
			</el-row>
		</div>
  </div>
</template>

<script>

export default {
  data () {
    return {
      userid: '',
      userword: '',
      m: 0,
	  logo: require('../assets/啊对对队.png'),
      v: ''
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
    get () { // 登录按钮
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
      )
        .then(Response => {
          this.m = Response.data[0].money
          sessionStorage.setItem('money', this.m)
        })
    },
    chongzhi () {
      let formData = new FormData()
      formData.append('id', sessionStorage.getItem('userid'))
      formData.append('word', sessionStorage.getItem('userword'))
      formData.append('money', this.m)
      formData.append('v', this.v)
      let config = {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }
      this.$axios.post('http://localhost:8080/chongzhi', formData, config
      )
        .then(Response => {
          this.get()
        })
    },
	chongzhi1 () {
	  let formData = new FormData()
	  formData.append('id', sessionStorage.getItem('userid'))
	  formData.append('word', sessionStorage.getItem('userword'))
	  formData.append('money', this.m)
	  formData.append('v', 50)
	  let config = {
	    headers: {
	      'Content-Type': 'multipart/form-data'
	    }
	  }
	  this.$axios.post('http://localhost:8080/chongzhi', formData, config
	  )
	    .then(Response => {
	      this.get()
	    })
	},
	chongzhi2 () {
	  let formData = new FormData()
	  formData.append('id', sessionStorage.getItem('userid'))
	  formData.append('word', sessionStorage.getItem('userword'))
	  formData.append('money', this.m)
	  formData.append('v', 100)
	  let config = {
	    headers: {
	      'Content-Type': 'multipart/form-data'
	    }
	  }
	  this.$axios.post('http://localhost:8080/chongzhi', formData, config
	  )
	    .then(Response => {
	      this.get()
	    })
	},
	chongzhi3 () {
	  let formData = new FormData()
	  formData.append('id', sessionStorage.getItem('userid'))
	  formData.append('word', sessionStorage.getItem('userword'))
	  formData.append('money', this.m)
	  formData.append('v', 500)
	  let config = {
	    headers: {
	      'Content-Type': 'multipart/form-data'
	    }
	  }
	  this.$axios.post('http://localhost:8080/chongzhi', formData, config
	  )
	    .then(Response => {
	      this.get()
	    })
	},
	chongzhi4 () {
	  let formData = new FormData()
	  formData.append('id', sessionStorage.getItem('userid'))
	  formData.append('word', sessionStorage.getItem('userword'))
	  formData.append('money', this.m)
	  formData.append('v', 1000)
	  let config = {
	    headers: {
	      'Content-Type': 'multipart/form-data'
	    }
	  }
	  this.$axios.post('http://localhost:8080/chongzhi', formData, config
	  )
	    .then(Response => {
	      this.get()
	    })
	},
	chongzhi5 () {
	  let formData = new FormData()
	  formData.append('id', sessionStorage.getItem('userid'))
	  formData.append('word', sessionStorage.getItem('userword'))
	  formData.append('money', this.m)
	  formData.append('v', 5000)
	  let config = {
	    headers: {
	      'Content-Type': 'multipart/form-data'
	    }
	  }
	  this.$axios.post('http://localhost:8080/chongzhi', formData, config
	  )
	    .then(Response => {
	      this.get()
	    })
	},
	chongzhi6 () {
	  let formData = new FormData()
	  formData.append('id', sessionStorage.getItem('userid'))
	  formData.append('word', sessionStorage.getItem('userword'))
	  formData.append('money', this.m)
	  formData.append('v', 10000)
	  let config = {
	    headers: {
	      'Content-Type': 'multipart/form-data'
	    }
	  }
	  this.$axios.post('http://localhost:8080/chongzhi', formData, config
	  )
	    .then(Response => {
	      this.get()
	    })
	},
	chongzhi7 () {
	  let formData = new FormData()
	  formData.append('id', sessionStorage.getItem('userid'))
	  formData.append('word', sessionStorage.getItem('userword'))
	  formData.append('money', this.m)
	  formData.append('v', 50000)
	  let config = {
	    headers: {
	      'Content-Type': 'multipart/form-data'
	    }
	  }
	  this.$axios.post('http://localhost:8080/chongzhi', formData, config
	  )
	    .then(Response => {
	      this.get()
	    })
	},
	chongzhi8 () {
	  let formData = new FormData()
	  formData.append('id', sessionStorage.getItem('userid'))
	  formData.append('word', sessionStorage.getItem('userword'))
	  formData.append('money', this.m)
	  formData.append('v', 100000)
	  let config = {
	    headers: {
	      'Content-Type': 'multipart/form-data'
	    }
	  }
	  this.$axios.post('http://localhost:8080/chongzhi', formData, config
	  )
	    .then(Response => {
	      this.get()
	    })
	}
  }
}
</script>
<style type="text/css">
.login{
	width: 300px;
	height: 200px;
	top: 50%;
	left: 50%;
	margin-top: 100px;
	text-align: center;
	margin-left: auto;
	margin-right: auto;
}
.c {
 margin-left: 10px;
}
</style>
