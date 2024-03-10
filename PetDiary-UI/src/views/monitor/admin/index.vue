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
      url : "http://localhost:9100/login"
    }
  }
}
</script>

<style scoped>

</style>
