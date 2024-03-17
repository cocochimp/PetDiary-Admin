// custom-tab-bar/index.js
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
    active: 0,
    maskState: false,
    list: [{
      path: "/pages/home/index/index",
      icon: "home",
      selectedIcon: "home-fill",
      text: "主页",
    }, {
      path: "/pages/shop/index/index",
      icon: "shopping-cart",
      selectedIcon: "shopping-cart-fill",
      text: "购物车",
    }, {
      icon: "plus",
      isPublishBtn: true
    }, {
      path: "/pages/social/index/index",
      icon: "pet",
      selectedIcon: "pet-fill",
      text: "社区",

    }, {
      path: "/pages/user/index/index",
      icon: "user",
      selectedIcon: "user-fill",
      text: "我的",

    }]
  },

  /**
   * 组件的方法列表
   */
  methods: {
    switchTab(e) {
      const index = e.currentTarget.dataset.index
      const el = this.data.list[index]
      const url = el.path
      console.log("进入了switchTab", index);
      console.log("el", el)
      console.log("url", url)

      if (el.isPublishBtn) {
        this.setData({
          maskState: true
        })
        this.setData({
          maskAnt: "show"
        })
      } else {
        wx.switchTab({
          url
        })
      }
    },
    goToPublish() {
      wx.navigateTo({
        url: "/pages/home/publish/publish"
      })
    },
    goToVideoPublish() {
      wx.navigateTo({
        url: "/pages/home/publishVideo/publishVideo"
      })
    },
    closeMask() {
      console.log("进入了closeMask");
      this.setData({
        maskAnt: ""
      })
      setTimeout(() => {
        this.setData({
          maskState: false
        })
      }, 200)
    }
  }
})