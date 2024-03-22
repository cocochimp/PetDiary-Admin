// pages/home/Detail/Detail.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    operationType: 1,
    skipId: 0,
    detailList: {
    
    },
    // 底部弹出层控件
    position: '',
    basicVisible: false,
    animation: true,
    scrollVisible: false,
    closeVisible: false,
    comments: []
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    // var coverPath = this.data.detailList.coverPath.split(',')
    // var videoPath = this.data.detailList.videoPath.split(',')
    // var comments = this.data.detailList.userComment
    // console.log(11);
    // this.setData({
    //   'detailList.coverPath': coverPath,
    //   'detailList.videoPath': videoPath,
    //   comments:comments
    // }) 
    // console.log(this.data.comments);
    console.log(11);
    wx.request({
      url: getApp().globalData.baseUrl + `/wx/home/showContentInfoById?contentId=${options.contentId}`,
      method: "GET",
      success: (res) => {
        console.log(res);
        const {
          data
        } = res
        console.log(data.rows);
        data.rows.forEach(item=>{
          if(item.coverPath) {
            item.coverPath=item.coverPath.split(',')
          }
        })
        this.setData({
          detailList: data.rows[this.data.skipId]
        })
        console.log(this.data.detailList);
      }
    })
  },

  handlePopupClose() {
    this.setData({
      basicVisible: false,
      scrollVisible: false,
      closeVisible: false,
    });
  },
  handleShowBasic(e) {
    const {
      position
    } = e.target.dataset;
    this.setData({
      position,
      basicVisible: true,
    });
  },
  handleShowScroll() {
    this.setData({
      scrollVisible: true
    });
  },
  handleChangeAnimation(checked) {
    this.setData({
      animation: checked.detail
    });
  },
  handleShowClose() {
    this.setData({
      closeVisible: true
    });
  },
  // 跳转至用户主页
  goUser(e) {
    wx.navigateTo({
      url: `/pages/user/detail/detail?userId=${e.currentTarget.dataset.id}`,
    })
  }
})