// pages/user/test01/test01.js
Page({
    /**
     * 页面的初始数据
     */
    properties: {
        // imgUrls: Array,
    },

    data: {
        active1:0,
        active2:0,
        currentIndex: 0,
        carouselImgUrls: [
            "http://img.boqiicdn.com/Data/BK/P/img50961416195246.jpg",
            "http://img.boqiicdn.com/Data/BK/P/imagick22281435572067.png",
            "http://img.boqiicdn.com/Data/BK/P/imagick12541435573782.png",
            "http://img.boqiicdn.com/Data/BK/P/img45811406628222.jpg",
            "http://img.boqiicdn.com/Data/BK/P/img60371407461398.jpg"
        ],
        List:[]
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad(options) {this.getList()},

    /**
     * 生命周期函数--监听页面初次渲染完成
     */
    onReady() {
        console.log('onReady')
    },

    /**
     * 生命周期函数--监听页面显示
     */
    onShow() {
        console.log('3')
        if (typeof this.getTabBar === 'function' &&
            this.getTabBar()) {
            console.log('onshow0')
            this.getTabBar().setData({
                active: 3
            })
        };
    },

    /**
     * 生命周期函数--监听页面隐藏
     */
    onHide() {
        console.log('onHide')
    },

    /**
     * 生命周期函数--监听页面卸载
     */
    onUnload() {
        console.log('onUnload')
    },

    /**
     * 页面相关事件处理函数--监听用户下拉动作
     */
    onPullDownRefresh() {

    },

    /**
     * 页面上拉触底事件的处理函数
     */
    onReachBottom() {

    },

    /**
     * 用户点击右上角分享
     */
    onShareAppMessage() {

    },

    gotoShibie: function (params) {   
        wx.navigateTo({      
            url: '/pages/social/shibie/shibie',    //要跳转到的页面路径
        })  
    },
    // 获取表单数据
    getList:function () {
        wx.request({
            url: 'https://example.com/ajax?dataType=member',
            dataType: 'json',
            success: (res) => {
              const {data} = res;
              this.setData({
                list: data.list
              });
              console.log(data.list[0].nations[0].breeds);
            }
          });
    },
    // 控制激活1
    switchTab1: function(e) {
        console.log("switchTab1 called");
        const index = e.currentTarget.dataset.index;
        console.log("index:", index);
        if (index != this.data.active1) {
            this.setData({
                active1: index,
                active2:0,
            });
        }
    },
    // 控制激活2
    switchTab2: function(e) {
        const index = e.currentTarget.dataset.index;
        if (index != this.data.active2) {
            this.setData({
                active2: index
            });
        }
    }
})
