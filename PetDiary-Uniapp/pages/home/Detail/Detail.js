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
    comments: [],
    newComment: '',
    contentId: 1
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    this.setData({
      contentId: options.contentId
    })
    console.log(11);
    wx.request({
      url: getApp().globalData.baseUrl + `/wx/home/showContentInfoById?contentId=${options.contentId}`,
      method: "GET",
      success: (res) => {
        const { data } = res;
        console.log(data.rows);
        data.rows.forEach(item => {
          if (item.coverPath) {
            item.coverPath = item.coverPath.split(',');
          }
        });
    
        // 发起请求获取每条评论的用户信息
        const requests = data.rows[0].userComment.map(comment => {
          return new Promise((resolve, reject) => {
            wx.request({
              url: getApp().globalData.baseUrl + `/wx/home/showUserDetailInfo?userId=${comment.userId}`,
              method: "GET",
              success: (res) => {
                comment.username = res.data.data.userInfo.nickname;
                comment.img = res.data.data.userInfo.avatar;
                resolve(comment);
              },
              fail: reject
            });
          });
        });
    
        // 等待所有请求完成
        Promise.all(requests)
          .then(comments => {
            this.setData({
              detailList: data.rows[this.data.skipId],
              comments: comments
            });
            console.log(this.data.comments);
          })
          .catch(error => {
            console.error('请求失败：', error);
            // 处理请求失败的情况
          });
      },
      fail: (error) => {
        console.error('请求失败：', error);
        // 处理请求失败的情况
      }
    });
    
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
  },
  onBlur(e) {
    this.setData({
      newComment: e.detail
    })
    console.log(this.data.newComment);
  },
  pushNew() {
    wx.request({
      url: getApp().globalData.baseUrl + '/wx/home/addUserComment',
      method: "POST",
      data: {
        userId: getApp().globalData.userInfo.openId,
        contentId: this.data.contentId,
        commentInfo: this.data.newComment,
        parentCommentId: 0
      },
      success: (res) => {
        console.log(res);
        // 判断评论添加是否成功
        if (res.data.status == 200) {
          wx.showToast({
            title: '评论成功',
            icon: 'success',
            duration: 2000
          });
          // 评论成功后重新渲染评论
          this.reloadComments();
        } else {
          wx.showToast({
            title: '评论失败，请重试',
            icon: 'none',
            duration: 2000
          });
        }
      },
      fail: (err) => {
        console.error('评论请求失败', err);
        wx.showToast({
          title: '评论失败，请重试',
          icon: 'none',
          duration: 2000
        });
      }
    })
  },
  
  // 重新加载评论
  reloadComments() {
    wx.request({
      url: getApp().globalData.baseUrl + `/wx/home/showContentInfoById?contentId=${this.data.contentId}`,
      method: "GET",
      success: (res) => {
        const { data } = res;
        data.rows.forEach(item => {
          if (item.coverPath) {
            item.coverPath = item.coverPath.split(',');
          }
        });
  
        // 发起请求获取每条评论的用户信息
        const requests = data.rows[0].userComment.map(comment => {
          return new Promise((resolve, reject) => {
            wx.request({
              url: getApp().globalData.baseUrl + `/wx/home/showUserDetailInfo?userId=${comment.userId}`,
              method: "GET",
              success: (res) => {
                comment.username = res.data.data.userInfo.nickname;
                comment.img = res.data.data.userInfo.avatar;
                resolve(comment);
              },
              fail: reject
            });
          });
        });
  
        // 等待所有请求完成
        Promise.all(requests)
          .then(comments => {
            this.setData({
              detailList: data.rows[this.data.skipId],
              comments: comments,
              newComment: '' // 清空输入框内容
            });
            console.log(this.data.comments);
          })
          .catch(error => {
            console.error('请求失败：', error);
            // 处理请求失败的情况
          });
      },
      fail: (error) => {
        console.error('请求失败：', error);
        // 处理请求失败的情况
      }
    });
  }
  

})