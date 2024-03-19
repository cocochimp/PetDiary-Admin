Component({
  /* 组件的属性列表 */
  properties: {
    isActive: Boolean
  },
  /* 组件的初始数据 */
  data: {
    active: 0,
    refreshState: false,
    navBarList: [{
        title: "推荐",
        value: 1
      },
      {
        title: "最新",
        value: 3
      },
      {
        title: "图文",
        value: 6
      },
      {
        title: "视频",
        value: 7
      },
      {
        title: "养猫",
        value: 8
      },
      {
        title: "养狗",
        value: 9
      },
    ],
    postsList: [],
    operationType: 1, //控制哪个类型数据获取
    skipId: '', //
    loadState: 'finish',
    pageNum: 1,
    noMore: false,



  },
  attached: function () {
    this.getPosts();
  },

  /**
   * 组件的方法列表
   */
  methods: {
    // 切换标签
    switchTab(e) {
      const index = e.currentTarget.dataset.index
      const value = e.currentTarget.dataset.value
      console.log("index", index)
      console.log("active", this.data.active)
      this.setData({
        operationType: value,
        postsList: [],
        pageNum: 1,
        noMore:false
      })
      this.getPosts()
      if (index != this.data.active) {
        console.log("center change tab")
        this.setData({
          refreshState: true,
          active: index
        })
      }
    },
    // 获取数据
    getPosts() {
      return new Promise((resolve, reject) => {
        this.setData({
          loadState: 'loading'
        })
        wx.request({
          url: getApp().globalData.baseUrl + `/wx/home/showContentInfo?operationType=${this.data.operationType}&pageNum=${this.data.pageNum}`,
          method: "GET",
          success: (res) => {
            const {
              data
            } = res
          
            if (!data.rows[0]) {
              this.setData({
                noMore: true,
                loadState: 'finish'
              })
              resolve();
            } else {
              console.log('data.rows', data.rows);
              if (data.rows.coverPath) {
                data.rows.forEach(item => {
                  item.coverPath = item.coverPath.split(',')[0];
                });
              }
              
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
    }
  }
})