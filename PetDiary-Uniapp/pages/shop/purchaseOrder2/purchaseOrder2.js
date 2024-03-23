import {
  Form
} from 'antd-mini/Form/form';

Page({
  data: {
    province: [],
    city: [],
    pids: [],
    goodsPriceList: [],
    goodList: [],
    nums:[]
  },

  getProvince() {
    return new Promise((resolve, reject) => {
      wx.request({
        url: getApp().globalData.baseUrl + '/wx/goods/showLocationByParentId',
        data: {
          parentId: 0
        },
        method: "GET",
        success: (res) => {
          console.log(res.data.rows);
          this.setData({
            province: res.data.rows
          });

          const requests = this.data.province.map((item) => {
            return new Promise((resolve, reject) => {
              wx.request({
                url: getApp().globalData.baseUrl + '/wx/goods/showLocationByParentId',
                data: {
                  parentId: item.id
                },
                method: "GET",
                success: (res) => {
                  if (!res.data.rows[0]) {
                    item.children = [{
                      id: item.id,
                      name: item.name,
                      parentId: item.parentId
                    }];
                  } else {
                    item.children = res.data.rows;
                  }
                  resolve();
                },
                fail: (res) => {
                  reject(res);
                }
              });
            });
          });

          // 等待所有请求完成
          Promise.all(requests)
            .then(() => {
              resolve();
            })
            .catch((error) => {
              reject(error);
            });
        },
        fail: (res) => {
          console.log(res);
          reject(res);
        }
      });
    });
  },
   // 格式化省市数据
   format() {
    const newData = this.data.province.map(province => {
      const newProvince = {
        label: province.name,
        value: province.id,
        children: []
      };

      province.children.forEach(city => {
        const newCity = {
          label: city.name,
          value: city.id
        };
        newProvince.children.push(newCity);
      });

      return newProvince; // 将处理后的省份数据返回
    });
    this.setData({
      province: newData
    })
  },
  handleCityPickerChange(value, selectedOption, e) {
    console.log('cityChange', value, selectedOption, e);
  },
  handleCityOnOk(value, selectedOption, e) {
    console.log('cityOk', value, selectedOption, e);
  },
  handleDismiss(e) {
    console.log('handleDismiss', e);
  },

  async onLoad(options) {
    try {
      this.setData({ 
        pids: JSON.parse(options.pids),
        nums: JSON.parse(options.nums)
      });
      
      console.log(this.data.pids); 
      await this.getProvince(); // 等待获取省份信息完成
      this.getByPid(); // 在获取省份信息后获取商品信息
      console.log(options);
  
      this.form = new Form();
      if (this.formRefList) {
        this.formRefList.forEach((ref) => {
          this.form.addItem(ref);
        });
      }
  
    } catch (error) {
      console.error('Error:', error);
    }
  },

  onChange(value, e) {
    console.log(value, e);
  },
  handleRef(ref) {
    // console.log(ref);
    if (!this.formRefList) {
      this.formRefList = [];
    }
    this.formRefList.push(ref.detail);
  },
  async getByPid() {
    try {
      const requests = this.data.pids.map(pid => {
        return new Promise((resolve, reject) => {
          wx.request({
            url: getApp().globalData.baseUrl + '/wx/goods/showGoodsDetailById',
            data: { goodId: pid },
            method: "GET",
            success: (res) => {
              // 处理请求成功的逻辑
              resolve(res.data.rows[0]);
            },
            fail: (error) => {
              reject(error);
            }
          });
        });
      });
  
      // 等待所有请求完成
      const responses = await Promise.all(requests);
  
      // 更新商品列表和总金额
      const goodList = [];
      let totalAmount = 0; // 保存所有商品总金额的变量
  
      responses.forEach((res, index) => {
        const num = this.data.nums[index];
        const amount = res.amount;
        const productId = res.id;
  
        goodList.push(res);
        const itemAmount = amount * num; // 计算当前商品的总金额
        totalAmount += itemAmount; // 累加到总金额中
      });
  
      // 将总金额保存到页面数据中
      this.setData({
        goodList: goodList,
        totalAmount: totalAmount.toFixed(2) // 新增一个字段保存所有商品的总金额
      });
  
      console.log(this.data.goodList);
      console.log('Total Amount:', this.data.totalAmount);
  
    } catch (error) {
      console.error('Error:', error);
    }
  }
,  
  

  async submit() {
    const values = await this.form.submit();
    const requestData = {
      userId: getApp().globalData.userInfo.openId,
      goodsPriceList: this.data.goodsPriceList,
      receiverName: values.name,
      receiverPhone: values.phone,
      addProv: values.city[0],
      addCity: values.city[1],
      address: values.address
    };
    
    console.log('即将发送的数据：', requestData);
  
    wx.request({
      url: getApp().globalData.baseUrl + '/wx/goods/buyGoods',
      method: "POST",
      header: {
        'Content-Type': 'application/json' // 指定请求体的内容类型为 JSON
      },
      data: requestData, // 使用requestData作为请求体的数据
      success: (res) => {
        console.log('请求成功，返回数据为：', res.data);
      },
      fail: (err) => {
        console.log('请求失败，错误信息为：', err);
      }
    })
  }
  

});