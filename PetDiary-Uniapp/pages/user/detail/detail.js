Page({
  data: {
    userInfo: {},
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
  onLoad(options) {
    this.setData({
      userId: options.userId
    })
    this.getUserInfo()
    this.getPosts()
  },
  // 获取用户信息
  getUserInfo() {
    console.log('getUserInfo');
    wx.request({
      url: getApp().globalData.baseUrl+`/wx/home/showUserDetailInfo?userId=${this.data.userId}`,
      method:"GET",
      success:(res)=>{
        const {data} =res
        this.setData({
          userInfo:data.data
        })
        console.log(this.data.userInfo);
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
          url: getApp().globalData.baseUrl + `/wx/home/showContentInfo?operationType=${this.data.operationType}&pageNum=${this.data.pageNum}&openId=${this.data.userId}&contentType=${this.data.contentType}`,
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
    goUser(e) {
      wx.navigateTo({
        url: `/pages/user/detail/detail?userId=${e.currentTarget.dataset.id}`,
      })
    }
})