Component({
  /* 组件的属性列表 */
  properties: {
    isActive: Boolean
  },
  /* 组件的初始数据 */
  data: {
    refreshState: false,
    postsList: [],
    operationType: 3, //控制哪个类型数据获取
    loadState: 'finish',
    pageNum: 1,
    noMore: false,
    hotList: [],
    radius: false,
  },
  attached: function () {
    console.log(1);
    this.getPosts();
    this.getHotList()
  },

  /**
   * 组件的方法列表
   */
  methods: {
    // 获取热榜数据
    getHotList() {
      wx.request({
        url: getApp().globalData.baseUrl + '/wx/home/showHotList',
        method: "GET",
        success: (res) => {
          const {
            data
          } = res
          console.log('hotlist', data.rows);
          this.setData({
            hotList: data.rows
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
        wx.request({
          url: getApp().globalData.baseUrl + `/wx/home/showContentInfo?operationType=2&pageNum=${this.data.pageNum}`,
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
  }
})