// pages/user/test01/test01.js
Page({
    /**
     * 页面的初始数据
     */
    properties: {
        // imgUrls: Array,
    },

    data: {
        currentIndex: 0,
        carouselImgUrls: [
            "http://img.boqiicdn.com/Data/BK/P/img50961416195246.jpg",
            "http://img.boqiicdn.com/Data/BK/P/imagick22281435572067.png",
            "http://img.boqiicdn.com/Data/BK/P/imagick12541435573782.png",
            "http://img.boqiicdn.com/Data/BK/P/img45811406628222.jpg",
            "http://img.boqiicdn.com/Data/BK/P/img60371407461398.jpg"
        ],
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad(options) {console.log('onLoad')},

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
})

// components/theSwiper.js
// Component({
//     /**
//      * 组件的属性列表
//      */
//     properties: {
//         imgUrls: Array,
//     },

//     /**
//      * 组件的初始数据
//      */
//     data: {
//         currentIndex: 0,
//         carouselImgUrls: [
//             "http://img.boqiicdn.com/Data/BK/P/img50961416195246.jpg",
//             "http://img.boqiicdn.com/Data/BK/P/imagick22281435572067.png",
//             "http://img.boqiicdn.com/Data/BK/P/imagick12541435573782.png",
//             "http://img.boqiicdn.com/Data/BK/P/img45811406628222.jpg",
//             "http://img.boqiicdn.com/Data/BK/P/img60371407461398.jpg"
//         ],
//     },
//     /**
//      * 组件的方法列表
//      */
//     methods: {
//         swiperChange(e) {
//             this.setData({
//                 currentIndex: e.detail.current
//             });
//         },
//         // 在 methods 对象中定义按钮点击事件处理函数
//         onLoginButtonClick: function () {
//             // 触发登录接口
//             wx.login({
//                 success: function (res) {
//                     // 登录成功，获取到用户临时登录凭证 code
//                     var code = res.code;
//                     console.log(code);
//                     // 将 code 发送给后台服务器
//                     // 具体的发送逻辑请根据自己的需求实现
//                 },
//                 fail: function (res) {
//                     // 登录失败，进行错误处理
//                     console.error('登录失败：', res);
//                 }
//             });
//         },
//         shouquanClick: function () {
//             // 微信授权
//             wx.getUserInfo({
//                 success: function (res) {
//                     console.log(res.encryptedData);
//                     console.log(res.iv);
//                 }
//             })
//         }
//     }
// });
/*

<view class="dots-box own-class">
  <view class="dots {{currentIndex == index ? 'bg-333' : ''}}" wx: for="{{ imgUrls }}" wx:key="{{ index }}"></view>
</view >
*/