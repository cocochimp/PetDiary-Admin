// pages/social/animal/animal.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
        id:0,
        petInfo:{
        }
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
  }

})