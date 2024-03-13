// pages/user/index/index.js
//获取应用实例
const app = getApp()

Page({
  /**
   * 页面的初始数据
   */
  data: {
    hasUserInfo: false,
    // userInfo: app.globalData.userInfo
    userInfo: {}
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    console.log("1",app.globalData.userInfo);
    if (app.globalData.userInfo) {
      this.setData({
        userInfo: app.globalData.userInfo
        // hasUserInfo: true
      })
    }
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    console.log("2",app.globalData.userInfo);
    if (app.globalData.userInfo) {
      this.setData({
        userInfo: app.globalData.userInfo
      })
    }
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    console.log("4",app.globalData.userInfo);
    if (typeof this.getTabBar === 'function' &&
      this.getTabBar()) {
      this.getTabBar().setData({ active: 4 })
    };
    if (app.globalData.userInfo) {
      this.setData({
        userInfo: app.globalData.userInfo
      })
    }
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
  //第一个组件
  tozhuanpan: function(param){
    wx.navigateTo({       
      url: '/pages/index/zhuanpan/zhuanpan',
      })
  },
  gotoCart: function (params) {   
    wx.navigateTo({      
        url: '/pages/user/cart/cart',    //要跳转到的页面路径
    })  
 },
 onGetUserInfo(e) {
  console.log('onGetUserInfo');
  const that = this;
  // const userInfo = e.detail.userInfo
  const userInfo = getApp().globalData.userInfo
  if (userInfo) {
    // 1. 小程序通过wx.login()获取code
    wx.login({
      success: function(login_res) {
        console.log('code:',login_res.code);
        //获取用户信息
        wx.getUserInfo({
          success: function(info_res) {
            // 2. 小程序通过wx.request()发送code到开发者服务器
            wx.request({
              url: getApp().globalData.baseUrl + '/wx/user/login',
              method: 'POST',
              header: {
                'content-type': 'application/x-www-form-urlencoded'
              },
              data: {
                code: login_res.code, //临时登录凭证
                rawData: info_res.rawData, //用户非敏感信息
                signature: info_res.signature, //签名
                // encrypteData: info_res.encryptedData, //用户敏感信息
                // iv: info_res.iv //解密算法的向量
              },
              success: function(res) {
                if (res.data.status == 200) {
                  // 7.小程序存储skey（自定义登录状态）到本地
                  var userJson = JSON.parse(res.data.data);
                  var user = {
                    openId: userJson.openid,
                    avatarUrl: userJson.avatar,
                    gender: userJson.gender==null?2:userJson.gender,
                    nickName: userJson.nickname,
                    signature: userJson.brief,
                    sessionKey: userJson.sessionKey,
                    createTime: userJson.createTime,
                    updateTime: userJson.updateTime
                  };
                  wx.setStorageSync('userInfo', user);
                  that.setData({
                    hasUserInfo: true,
                    userInfo: user
                  })
                  // wx.setStorageSync('skey', user.skey);
                  // 当获取到用户信息后，将用户信息存储在App实例的globalData中
                  getApp().globalData.userInfo = user;
                  console.log('globalData',getApp().globalData.userInfo)
                } else{
                  console.log('服务器异常');
                }
              },
              fail: function(error) {
                //调用服务端登录接口失败
                console.log(error);
              }
            })
          }
        })
      }
    })
  }
},
gotoPage: function (params) {   
  wx.navigateTo({      
      url: '/pages/user/login/login',    //要跳转到的页面路径
  })  
}
})