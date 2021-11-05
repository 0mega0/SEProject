<template>
  <div >
    <router-view></router-view>
  </div>
</template>
<script>
export default {
  created () {
    sessionStorage.setItem('userid', '')
    sessionStorage.setItem('userword', '')
  }
}
</script>
