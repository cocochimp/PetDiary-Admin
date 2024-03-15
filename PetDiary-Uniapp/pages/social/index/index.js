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
        current: 0,//控制国家切换
        scrollTop: 0,
        items: [],
        nations:[],
        total:0,
        catDog:[
            {
                title: '猫',
            },
            {
                title: '狗',
            }],
        current2:0,//控制猫狗切换
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad() {
            this.getNations(() => {
                this.getPets();
            });
    },
    onChange(current,e) {
       
        console.log('current',current.detail);
        current = current.detail;
        this.setData({
            current,
            scrollTop: Math.random(),
        });
        this.getPets()
    },
    /**
     * 生命周期函数--监听页面显示
     */
    onShow() {
        if (typeof this.getTabBar === 'function' &&
            this.getTabBar()) {
            this.getTabBar().setData({
                active: 3
            })
        };
    },
    gotoShibie: function (params) {   
        wx.navigateTo({      
            url: '/pages/social/shibie/shibie',    //要跳转到的页面路径
        })  
    },
    
    
// 获取国家数据
    getNations:function(callback) {
      console.log(12);
wx.request({
    url: `http://localhost:9501/wx/social/queryNationByType?type=${this.data.current2}`,
    method: "POST",
    success: (res) => {
        console.log(res.data);
        this.setData({
            total:res.data.total,
            nations:res.data.rows
        });
        console.log(this.data.nations);
        callback()
    },
    fail:(err)=>{
      console.log(err);
    }

})
    },
        // 获取宠物列表
        getPets: function(e) {
            console.log(this.data.nations[this.data.current].nationName);
            wx.request({
                url: `http://localhost:9501/wx/social/queryListByNation?type=${this.data.current2}&nation=${this.data.nations[this.data.current].nationName}`,
                method: "POST",
                success: (res) => {
                    const { rows } = res.data;
                    console.log('国家', rows);
                    const items = [];
                    for (let i = 0; i < this.data.total; i++) {
                        items.push({
                            title: this.data.nations[i].nationName+'('+this.data.nations[i].number+')',
                            content: rows,
                        });
                    }
                    this.setData({
                        items,
                    })
                }
            })
        },
        handleChange(current) {
            let current2 = current.detail;
            this.setData({
                current2:current2,
            });
                this.getNations(() => {
                    this.getPets();
                });
        },
      
})
