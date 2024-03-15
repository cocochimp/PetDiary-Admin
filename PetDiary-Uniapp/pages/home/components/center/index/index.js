// pages/social/components/center/index/index.js
var canLoading = true
var loadPages = 1
Component({
  /* 组件的属性列表 */
  properties: {
    isActive: Boolean
  },
  /* 组件的初始数据 */
  data: {
    active: 0,
    refreshState: false,
    loadState: "finish",
    navBarList: [{
        title: "推荐"
      },
      {
        title: "最新"
      },
      {
        title: "热榜"
      }
    ],
    videoId: null,
    contentArray: [],
    contentArrayLength: 0,
    res: [],
    list: [],
    goodList: [{
      id: '1',
      name: '#小猫',
      pic: '../../../../../images/testData/比熊.png',
      minPrice: '小了',
	  originalPrice: '111',
	  type:0,
    },
    {
      id: '2',
      name: '#小狗',
      pic: '../../../../../images/testData/比熊.png',
      minPrice: '小白',
	  originalPrice: '222',
	  type:1,
	  
    },
    {
      id: '3',
      name: '#小🐖',
      pic: '../../../../../images/testData/比熊.png',
      minPrice: '小明',
	  originalPrice: '333',
	  type:0,
    }]
    
  },
  attached: function () {
    console.log("进入:attached")
    this.showContentInfo();
    this.setData({
      refreshState: true,
    })
    setTimeout(() => {
      let data = this._getDemoData()
      this._loadList(data)
    }, 1000)
  },

  /**
   * 组件的方法列表
   */
  methods: {
    // 切换标签
    switchTab(e) {
      const index = e.currentTarget.dataset.index
      console.log("index", index)
      console.log("active", this.data.active)
      if (index != this.data.active) {
        console.log("center change tab")
        this.setData({
          refreshState: true,
          active: index
        })
      }
    },
    // 播放视频
    onVideoPlay(e) {
      const index = e.currentTarget.dataset.index
      if (index != this.data.videoId) {
        console.log("center change video play")
        this.setData({
          videoId: index
        })
      }
    },
    /* 刷新开始处理 */
    onRefresh() {
      this.showContentInfo();
      this.setData({
        refreshState: true,
        videoId: null
      })
      console.log("center onRefresh")
      setTimeout(() => {
        if (this.data.refreshState) {
          console.log("center refreshing")
          this.data.list = []
          canLoading = true
          loadPages = 1
          let data = this._getDemoData()
          this._loadList(data)
        }
      }, 1000)
    },
    /* 刷新停止处理 */
    abortRefresh() {
      console.log("center abortRefresh")
      if (this.data.refreshState) {
        this.setData({
          refreshState: false,
        })
      }
    },
    /* 加载更多处理 */
    onLoadMore() {
      console.log("center loadmore")
      if (canLoading) {
        this.setData({
          loadState: "loading",
        })
        setTimeout(() => {
          let data = this._getDemoData()
          loadPages++
          this._loadList(data)
        }, 1000)
      }
    },
    _loadList(data) {
      console.log("center loadlist", data)
      // if (loadPages > 3 || data.length == 0) {
      //   console.log("center finishloading")
      //   canLoading = false
      // }
      this.setData({
        list: this.data.list.concat(data),
        refreshState: false,
        loadState: "finish"
      })
    },
    showContentInfo() {
      const that = this;
      console.log("进入formSubmit,active:", this.data.active);
      wx.request({
        url: getApp().globalData.baseUrl + '/wx/home/showContentInfo',
        method: 'GET',
        data: {
          operationType: this.data.active
        },
        header: {
          "content-Type": "application/json"
        },
        success: function (res) {
          if (res.data.code == 200) {
            console.log(11);
            var contentArray = res.data.rows;
            console.log('contentArray:', contentArray);
            that.setData({
              contentArray: contentArray,
              contentArrayLength: res.data.rows.length
            })
            that._getDemoData();

          } else {
            console.log('服务器异常');
          }
        },
        fail: function (error) {
          //调用服务端登录接口失败
          console.log(error);
        }
      })
    },
    _getDemoData() {
      console.log('getdemo');
      let data = []
      for (let i = 0; i < this.data.contentArrayLength; i++) {
        console.log(this.data.contentArray[i]);
        const contentInfo = this.data.contentArray[i];
        const userInfo = contentInfo.userInfo;
        let tmp = {
          id: contentInfo.contentId,
          avatar: userInfo.avatar,
          username: userInfo.nickname,
          intro: contentInfo.description,
          commentTotal: contentInfo.userComment.length,
          likeTotal: contentInfo.likeCount,
          fanTotal: contentInfo.fanCount,
          publishTime: contentInfo.updateTime,
          contentType: contentInfo.contentType,
        };
        if (contentInfo.contentType == "0") {
          tmp.pic = {
            type: "long",
            url: contentInfo.coverPath.split(",")
          };
        } else if (contentInfo.contentType == "1") {
          tmp.video = {
            thumb: contentInfo.coverPath,
            url: contentInfo.videoPath
          };
        }
        data.push(tmp);
        console.log('data', data);
      }
      console.log(222);
      console.log('data', data);

      this.setData({
        list: data
      });
      console.log("activeData", this.data.list)
    }
  }
})