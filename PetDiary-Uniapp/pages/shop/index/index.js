// pages/shop/index/index.js

Page({
  /**
   * 页面的初始数据
   */
  data: {
    active: 0,
    activeId: 1,
    scrollPos: 0,
    navList: [],
    sort: [],
    OperateType:0,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getNavList()
    this.getSort()
  },


  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    if (typeof this.getTabBar === 'function' &&
      this.getTabBar()) {
      this.getTabBar().setData({
        active: 1
      })
    }
  },


  switchTab: function (e) {
    const index = e.currentTarget.dataset.index
    const id = e.currentTarget.dataset.id
    if (index != this.data.active) {
      this.setData({
        active: index,
        activeId: id
      })
    }
    this.getSort()
  },
  backTop() {
    this.setData({
      scrollPos: 0
    })
  },
  getNavList() {
    wx.request({
      url: getApp().globalData.baseUrl + '/wx/goods/showCategoryInfo',
      method: "GET",
      success: (res) => {
        const {
          data
        } = res
        this.setData({
          navList: data.rows
        })
      }
    })
  },
  getSort() {
    wx.request({
      url: getApp().globalData.baseUrl + `/wx/goods/showGoodsList?OperateType=${this.data.OperateType}&categoryId=${this.data.activeId}`,
      method: "GET",
      success: (res) => {
        const {
          data
        } = res
        this.setData({
          sort:data.rows
        })
        console.log(data);
      }
    })
  }
})