Page({
  data: {
    orders: [], // 订单数据
    type:1
  },

  onLoad(options) {
    console.log(options);
    this.setData({
      type:options.type
    })
    // 在页面加载时获取订单数据
    this.fetchOrders()
      .then(() => {
        this.format();
      })
      .catch(error => {
        console.error('Error fetching orders:', error);
      });
  },

  fetchOrders() {
    return new Promise((resolve, reject) => {
      wx.request({
        url: getApp().globalData.baseUrl + '/wx/goods/showOrderListByStatus',
        method: "GET",
        data: {
          status: this.data.type,
          userId: getApp().globalData.userInfo.openId //
        },
        success: (res) => {
          this.setData({
            orders: res.data.rows
          });
          console.log(res);
          resolve(); // 订单数据获取成功，resolve Promise
        },
        fail: (error) => {
          reject(error); // 订单数据获取失败，reject Promise
        }
      });
    });
  },

  format() {
    this.data.orders.forEach(item => {
      wx.request({
        url: getApp().globalData.baseUrl + '/wx/goods/showGoodsDetailById',
        method: "GET",
        data: {
          goodId: item.productId
        },
        success: (res) => {
          console.log(res);
          // 更新订单项的图片信息 
          const img = res.data.rows[0].coverPhoto; // 假设接口返回的数据中有图片信息
          item.img = img;
          item.name=res.data.rows[0].name; 
          console.log(img);
          // 更新数据到页面
          this.setData({
            orders: this.data.orders
          });
        },
        fail: (error) => {
          console.error('Request error:', error);
        }
      });
    });
  }
});
