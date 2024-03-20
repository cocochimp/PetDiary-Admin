// pages/user/fanList/fanList.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    listType: 1,
    // userId:getApp().globalData.userInfo.openId
    userId: 'omyzG6wx9cqKd703dzebT7LouPVs',
    fanList:[],
    radius: false,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    this.setData({
      listType: options.type
    })
    this.getList()
  },
  getList() {
    wx.request({
      url: getApp().globalData.baseUrl + `/wx/home/showUserInfoByUserId?listType=${this.data.listType}&userId=${this.data.userId}`,
      method:"GET",
      success:(res) =>{
        const {data} =res
        this.setData({
          fanList:data.rows
        })
        console.log(this.data.fanList);

      }
    })
  }

})