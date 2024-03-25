/* 
1 发送请求获取数据 
2 点击轮播图 预览大图
  1 给轮播图绑定点击事件
  2 调用小程序的api  previewImage 
3 点击 加入购物车
  1 先绑定点击事件
  2 获取缓存中的购物车数据 数组格式 
  3 先判断 当前的商品是否已经存在于 购物车
  4 已经存在 修改商品数据  执行购物车数量++ 重新把购物车数组 填充回缓存中
  5 不存在于购物车的数组中 直接给购物车数组添加一个新元素 新元素 带上 购买数量属性 num  重新把购物车数组 填充回缓存中
  6 弹出提示
4 商品收藏
  1 页面onShow的时候  加载缓存中的商品收藏的数据
  2 判断当前商品是不是被收藏 
    1 是 改变页面的图标
    2 不是 。。
  3 点击商品收藏按钮 
    1 判断该商品是否存在于缓存数组中
    2 已经存在 把该商品删除
    3 没有存在 把商品添加到收藏数组中 存入到缓存中即可
 */

Page({

  /**
   * 页面的初始数据
   */
  data: {
    goodsObj: {
      goods_id: 21321,
      goods_name: '小熊猫',
      goods_price: 898,
      goods_number: 100,
      goods_weight: 100,
      goods_introduce: '很可爱',
      goods_big_logo: 'https://pet-diary.oss-cn-beijing.aliyuncs.com/2024-03/%E5%90%89%E5%A8%83%E5%A8%83%E7%8A%AC.jpg',
      goods_small_logo: 'https://pet-diary.oss-cn-beijing.aliyuncs.com/2024-03/%E5%90%89%E5%A8%83%E5%A8%83%E7%8A%AC.jpg',
      goods_state: 2,
      is_del: 0,
      add_time: 123142321,
      upd_time: 123142321,
      delete_time: 0,
      pics: [{
          pics_id: 1,
          pics_mid: 'https://pet-diary.oss-cn-beijing.aliyuncs.com/2024-03/%E5%90%89%E5%A8%83%E5%A8%83%E7%8A%AC.jpg'
        },
        {
          pics_id: 2,
          pics_mid: 'https://pet-diary.oss-cn-beijing.aliyuncs.com/2024-03/%E7%9F%AD%E6%AF%9B%E7%8C%AB.jpg'
        },
      ]

    },
    // 商品是否被收藏
    isCollect: false,
    pid: 144
  },
  // 商品对象
  GoodsInfo: {},
  /**
   * 生命周期函数--监听页面加载
   */
  onShow: function () {
    let pages = getCurrentPages();
    let currentPage = pages[pages.length - 1];
    let options = currentPage.options;
    const {
      goodId
    } = options;
    this.getGoodDetail(goodId);
    this.setData({
      pid: goodId
    })


  },
  // 获取商品详情数据
  getGoodDetail(goodId) {
    wx.request({
      url: getApp().globalData.baseUrl + `/wx/goods/showGoodsDetailById?goodId=${goodId}`,
      method: "GET",
      success: (res) => {
        const {
          data: {
            rows
          }
        } = res
        console.log(rows[0].coverPhoto);
        rows.forEach(item => {
          if (item.coverPhoto) {
            item.coverPhoto = item.coverPhoto.split(',')
          }
          if (item.detailPhoto) {
            item.detailPhoto = item.detailPhoto.split(',')
          }
        })
        console.log(rows);
        this.setData({
          goodsObj: rows[0]
        })
        console.log(rows);
      }
    })
  },
  // 点击轮播图 放大预览
  handlePrevewImage(e) {
    // 1 先构造要预览的图片数组 
    const urls = this.GoodsInfo.pics.map(v => v.pics_mid);
    // 2 接收传递过来的图片url
    const current = e.currentTarget.dataset.url;
    wx.previewImage({
      current,
      urls
    });

  },
  // 点击 加入购物车
  handleCartAdd() {
    wx.request({
      url: getApp().globalData.baseUrl + '/wx/goods/addCarShop',
      method: "POST",
      data: {
        "pid": this.data.pid,
        "uid": getApp().globalData.userInfo.openId,
        "num": 1
      },
      success: (res) => {
        console.log(res);
        // 弹窗提示
        wx.showToast({
          title: '加入成功',
          icon: 'success',
          // true 防止用户 手抖 疯狂点击按钮 
          mask: true
        });
      },
      fail: (err) => {
        console.log(err);
      }
    })
  },
  bug() {
    console.log(1);
    wx.navigateTo({
      url: `/pages/shop/purchaseOrder/purchaseOrder?pid=${this.data.pid}`,
    })
  }

})