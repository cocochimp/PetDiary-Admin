const app = getApp()

Page({
  /**
   * 页面的初始数据
   */
  data: {
    hasUserInfo: false,
    userInfo: {},
    userInfo1: {}, //粉丝数据
    userId: '',
    refreshState: false,
    postsList: [],
    operationType: 10, //控制哪个类型数据获取
    loadState: 'finish',
    pageNum: 1,
    noMore: false,
    items: [{
        title: '图片',
      },
      {
        title: '视频',
      }
    ],
    contentType: 0
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (app.globalData.userInfo.openId) {
      this.setData({
        userInfo: app.globalData.userInfo,
        hasUserInfo: true
      })
      this.getFanList()
      this.getPosts()
    }
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
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
    if (typeof this.getTabBar === 'function' &&
      this.getTabBar()) {
      this.getTabBar().setData({
        active: 4
      })
    };
    if (app.globalData.userInfo) {
      this.setData({
        userInfo: app.globalData.userInfo
      })
    }
  },

  //第一个组件
  tozhuanpan: function (param) {
    wx.navigateTo({
      url: '/pages/index/zhuanpan/zhuanpan',
    })
  },
  gotoCart: function (params) {
    wx.navigateTo({
      url: '/pages/user/cart/cart', //要跳转到的页面路径
    })
  },
  onGetUserInfo(e) {
    const that = this;
    console.log('onGetUserInfo');
    wx.showModal({
      title: '提示',
      content: '是否立即登录',
      success(res) {
        if (res.confirm) {
          console.log('用户点击确认')
          wx.login({
            success: function (login_res) {
              console.log('code:', login_res.code);
              //获取用户信息
              wx.getUserInfo({
                success: function (info_res) {
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
                    success: function (res) {
                      if (res.data.status == 200) {
                        // 7.小程序存储skey（自定义登录状态）到本地
                        var userJson = JSON.parse(res.data.data);
                        var user = {
                          openId: userJson.openid,
                          avatarUrl: userJson.avatar,
                          gender: userJson.gender == null ? 2 : userJson.gender,
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
                        that.getFanList()
                        // wx.setStorageSync('skey', user.skey);
                        // 当获取到用户信息后，将用户信息存储在App实例的globalData中
                        getApp().globalData.userInfo = user;
                        console.log('globalData', getApp().globalData.userInfo)
                      } else {
                        console.log('服务器异常');
                      }
                    },
                    fail: function (error) {
                      //调用服务端登录接口失败
                      console.log(error);
                    }
                  })
                }
              })
            }
          })
        } else if (res.cancel) {
          console.log('用户点击取消')
        }
      }
    })
  },
  gotoPage: function (params) {
    wx.navigateTo({
      url: '/pages/user/login/login', //要跳转到的页面路径
    })
  },
  // 获取用户粉丝信息
  getFanList() {
    wx.request({
      url: getApp().globalData.baseUrl + `/wx/home/showUserDetailInfo?userId=${getApp().globalData.userInfo.openId}`,
      method: "GET",
      success: (res) => {
        const {
          data
        } = res
        this.setData({
          userInfo1: data.data
        })
      }
    })
  },
  // 获取数据
  getPosts() {
    return new Promise((resolve, reject) => {
      this.setData({
        loadState: 'loading'
      })
      console.log(this.data.contentType);
      wx.request({
        url: getApp().globalData.baseUrl + `/wx/home/showContentInfo?operationType=${this.data.operationType}&pageNum=${this.data.pageNum}&openId=${getApp().globalData.userInfo.openId}&contentType=${this.data.contentType}`,
        method: "GET",
        success: (res) => {
          const {
            data
          } = res
          console.log('res', res);
          if (!data.rows[0]) {
            this.setData({
              noMore: true,
              loadState: 'finish'
            })
            resolve();
          } else {
            console.log('data.rows', data.rows);
            data.rows.forEach(item => {
              if (item.coverPath) {
                item.imgNum = item.coverPath.split(',').length
                item.coverPath = item.coverPath.split(',')[0];
              }

            });

            const originData = this.data.postsList
            this.setData({
              postsList: [...originData, ...data.rows]
            });
            console.log('postsList', this.data.postsList);
            resolve();
          }
        },
        fail: function (res) {
          console.log(res);
          reject(res);
        }
      });
    });
  },

  /* 刷新开始处理 */
  onRefresh() {
    console.log('开始刷新');
    this.setData({
      postsList: []
    })
    // 返回一个 Promise 对象
    this.getPosts().then(() => {
      setTimeout(() => {
        this.setData({
          refreshState: false
        });
        console.log('刷新完成');
      }, 1000);
    });
  },

  /* 下拉到底处理 */
  onLoadMore() {
    this.data.pageNum += 1
    this.getPosts()
  },

  goDetail(e) {

    wx.navigateTo({
      url: `/pages/home/Detail/Detail?contentId=${e.currentTarget.dataset.index}`,
    })
  },
  handleChange(current) {
    this.setData({
      contentType: current.detail,
      postsList: []
    })
    this.getPosts()
    console.log(this.data.contentType);
  },
  goAnimal(e) {
    console.log(e.currentTarget.dataset.id);
    wx.navigateTo({
      url: `/pages/social/animal/animal?id=${e.currentTarget.dataset.id}`,
    })
  },
  // goUser(e) {
  //   wx.navigateTo({
  //     url: `/pages/user/detail/detail?userId=${e.currentTarget.dataset.id}`,
  //   })
  // }

})