<template>
  <i-frame :src="url" />
</template>

<script>
import  iFrame from "@/components/iFrame/index"
export default {
  name: "Swagger",
  components: { iFrame },
  data() {
    return {
      url : "http://localhost:8080/doc.html"
    }
  }
}
</script>

<style scoped>

</style>
