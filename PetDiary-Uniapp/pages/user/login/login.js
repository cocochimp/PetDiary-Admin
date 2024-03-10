// pages/login/login.js
const app = getApp()
Page({
    data: {
        wxUserInfo: {},
        columns: ["男", "女", "未知"],
        avatarUrl: null,
        nickName: null,
        gender: 2,
        signature: null
    },
    onLoad() {
        this.setData({
            avatarUrl: getApp().globalData.userInfo.avatarUrl,
            nickName: getApp().globalData.userInfo.nickName,
            gender: getApp().globalData.userInfo.gender,
            signature: getApp().globalData.userInfo.signature
        })
    },
    onChooseAvatar(e) {
        const {
            avatarUrl
        } = e.detail
        this.setData({
            avatarUrl,
        });
        const that = this;
        console.log(avatarUrl),
            wx.uploadFile({
                filePath: avatarUrl,
                name: 'file',
                url: getApp().globalData.functionUrl + '/upload', //服务器端接收图片的路径
                success: function (res) {
                    var userJson = JSON.parse(res.data);
                    console.log("上传成功：", userJson.data.url); //发送成功回调
                    that.setData({
                        avatarUrl: userJson.data.url
                    })
                },
                fail: function (res) {
                    console.log("上传失败：", res); //发送失败回调，可以在这里了解失败原因
                }
            })
    },
    formSubmit(e) {
        var UserInfo = {
            openid: getApp().globalData.userInfo.openId,
            avatar: this.data.avatarUrl,
            nickname: e.detail.value.nickname,
            gender: this.data.gender,
            brief: this.data.signature,
        };
        console.log('UserInfo:', UserInfo)
        const that = this;
        wx.request({
            url: getApp().globalData.baseUrl + '/updateInfo',
            method: 'POST',
            data: UserInfo,
            header: {
                "content-Type": "application/json"
            },
            success: function (res) {
                if (res.data.status == 200) {
                    var userJson = JSON.parse(res.data.data);
                    console.log('userJson:', userJson);
                    var user = {
                        openId: userJson.openid,
                        avatarUrl: userJson.avatar,
                        gender: userJson.gender,
                        nickName: userJson.nickname,
                        signature: userJson.brief,
                        sessionKey: userJson.sessionKey,
                        createTime: userJson.createTime,
                        updateTime: userJson.updateTime
                    };
                    console.log('user:', user);
                    wx.setStorageSync('userInfo', user);
                    that.setData({
                        hasUserInfo: true,
                        wxUserInfo: user
                    })
                    wx.setStorageSync('skey', user.skey);
                    getApp().globalData.userInfo = user;
                    console.log('globalData', getApp().globalData.userInfo)
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
    pickSex: function (e) {
        this.setData({
            gender: e.detail.value
        })
    },
    bindEquipmentId: function (e) {
        this.setData({
            signature: e.detail.value
        })
    }
})