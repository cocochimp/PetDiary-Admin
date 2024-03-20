Page({
  data: {
    userInfo: {},
    userId: '',
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    this.getUserInfo()
  },
  // 获取用户信息
  getUserInfo() {
    console.log('getUserInfo');
    wx.request({
      url: getApp().globalData.baseUrl+`/wx/home/showUserDetailInfo?userId=omyzG6xGFX9OUPIcTHmOdQ8DVSFk`,
      method:"GET",
      success:(res)=>{
        const {data} =res
        this.setData({
          userInfo:data.data
        })
        console.log(this.data.userInfo);
      }
    })
  }
})