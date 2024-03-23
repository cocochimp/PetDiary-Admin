// pages/shop/components/cart/cart.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {

  },

  /**
   * 组件的初始数据
   */
  data: {
    carList: [],
    selectAllStatus: false,
    totalPrice: 0, // 总价
    selectedPids: [], // 勾选了的商品的 pid 数组
    selectedPids: [], // 已勾选的商品的 pid 数组
    selectedNums: [], // 对应已勾选商品的数量数组
  },
  lifetimes: {
    attached() {
      this.getCarList()
    }
  },

  /**
   * 组件的方法列表
   */
  methods: {
    getCarList() {
      console.log(getApp().globalData.userInfo.openId);
      if (getApp().globalData.userInfo.openId) {
        wx.request({
          url: getApp().globalData.baseUrl + `/wx/goods/showCarListByUserId?uId=${getApp().globalData.userInfo.openId}`,
          method: "GET",
          success: (res) => {
            const {
              data: {
                rows
              }
            } = res
            // 为每个商品对象添加 checked 属性，默认设置为 false
            const carList = rows.map(item => ({
              ...item,
              checked: this.data.carList.find(existingItem => existingItem.pid === item.pid) ? this.data.carList.find(existingItem => existingItem.pid === item.pid).checked : false
            }));
            this.setData({
              carList
            })
            console.log(this.data.carList);
          }
        })
      }
    },

    // 减少商品数量
    handleMinus(e) {
      const data = e.currentTarget.dataset
      console.log(e.currentTarget.dataset);
      wx.request({
        url: getApp().globalData.baseUrl + '/wx/goods/addCarShop',
        method: "POST",
        data: {
          pid: data.pid,
          uid: getApp().globalData.userInfo.openId,
          num: data.num - 1
        },
        success: () => {
          this.getCarList()
        }
      })
    },
    // 增加商品数量
    handlePlus(e) {
      const data = e.currentTarget.dataset
      wx.request({
        url: getApp().globalData.baseUrl + '/wx/goods/addCarShop',
        method: "POST",
        data: {
          pid: data.pid,
          uid: getApp().globalData.userInfo.openId,
          num: data.num + 1
        },
        success: () => {
          this.getCarList()
        }
      })
    },
    // 删除商品
    del(e) {
      const data = e.currentTarget.dataset
      const pid = data.pid;
      const that = this; // 保存当前页面的上下文

      // 显示删除提示框
      wx.showModal({
        title: '提示',
        content: '确定要删除吗？',
        success(res) {
          if (res.confirm) { // 如果用户点击了确定按钮
            wx.request({
              url: getApp().globalData.baseUrl + '/wx/goods/addCarShop',
              method: "POST",
              data: {
                pid: pid,
                uid: getApp().globalData.userInfo.openId,
                num: 0
              },
              success() {
                that.getCarList(); // 删除成功后重新获取购物车列表
              }
            });
          } else if (res.cancel) {
            console.log('用户点击了取消按钮');
          }
        }
      });
    },
  
    updateSelectedItems(carList) {
      const selectedPids = carList.filter(item => item.checked)
                                    .map(item => item.pid);
      const selectedNums = carList.filter(item => item.checked)
                                    .map(item => item.num);
      this.setData({
        selectedPids,
        selectedNums,
      });
    },
    
    fuxuankuang(event) {
      const selectedPids = event.detail.value;
      console.log(selectedPids);
      const carList = this.data.carList.map(item => {
        if (selectedPids.includes(item.pid.toString())) {
          item.checked = true;
        } else {
          item.checked = false;
        }
        return item;
      });
      this.updateSelectedItems(carList); // 更新已选商品列表
  
      // 计算总价
      let totalPrice = 0;
      for (let item of carList) {
        if (item.checked) {
          totalPrice += item.amount * item.num;
        }
      }
      this.setData({
        carList,
        totalPrice: totalPrice.toFixed(2),
      });
    },
  

    selectAll(e) {
      const selectAllStatus = !this.data.selectAllStatus;
      const carList = this.data.carList.map(item => {
        return {
          ...item,
          checked: selectAllStatus
        };
      });
      this.setData({
        carList,
        selectAllStatus
      });
      // 更新选中的商品的pid数组
      const selectedPids = carList.filter(item => item.checked).map(item => item.pid.toString());
      // 更新总价
      let totalPrice = 0;
      for (let item of carList) {
        if (item.checked) {
          totalPrice += item.amount * item.num;
        }
      }
      this.setData({
        totalPrice: totalPrice.toFixed(2),
        selectedPids,
      });
    },
    bug() {
      console.log(this.data.selectedPids);
      console.log(this.data.selectedNums);
      wx.navigateTo({
        url: `/pages/shop/purchaseOrder2/purchaseOrder2?pids=${JSON.stringify(this.data.selectedPids)}&nums=${JSON.stringify(this.data.selectedNums)}`,
        
      })
    }
    
  }
})