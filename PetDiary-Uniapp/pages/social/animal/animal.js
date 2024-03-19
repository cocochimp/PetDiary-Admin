// pages/social/animal/animal.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
        id:0,
        petInfo:{
        },
        refreshState: false,
    postsList: [],
    operationType: 4, //控制哪个类型数据获取
    loadState: 'finish',
    pageNum: 1,
    noMore: false,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    console.log('我是',options.id);
    this.setData({
      id:options.id
    })
    this.getDetail()
    this.getPosts();

  },
  getDetail:function() {
    wx.request({
      url: `http://localhost:9501/wx/social/queryPetDetailByPetId?petId=${this.data.id}`,
      method:"POST",
      success:(res)=>{
        const {data:{rows}}= res
        console.log(res.data.rows);
        this.setData({
          petInfo:rows[0]
        })
        console.log(this.data.petInfo.img);
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
        url: getApp().globalData.baseUrl + `/wx/home/showContentInfo?operationType=${this.data.operationType}&pageNum=${this.data.pageNum}&petId=${this.data.id}`,
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

})