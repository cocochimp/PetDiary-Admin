<template>
  <i-frame :src="url" />
</template>

<script>
import  iFrame from "@/components/iFrame/index"
export default {
  name: "Sentinel",
  components: { iFrame },
  data() {
    return {
      // url : '/sentinel' + "/#/dashboard/home"
      url:'http://localhost:8718'
    }
  }
}
</script>

<style scoped>

</style>
