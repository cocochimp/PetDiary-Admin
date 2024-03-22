// pages/shop/components/cart/cart.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {

  },

  /**
   * 组件的初始数据
   */
  data: {
    carList: []
  },
  lifetimes: {
    attached() {
      this.getCarList()
    }
  },

  /**
   * 组件的方法列表
   */
  methods: {
    getCarList() {
      wx.request({
        url: getApp().globalData.baseUrl + '/wx/goods/showCarListByUserId?uId=omyzG6xGFX9OUPIcTHmOdQ8DVSFk',
        method: "GET",
        success: (res) => {
          const{data:{rows}} =res
          this.setData({
            carList:rows
          })
          console.log(this.data.carList);
        }
      })
    }
  }
})