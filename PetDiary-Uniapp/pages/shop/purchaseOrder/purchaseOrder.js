import {
  Form
} from 'antd-mini/Form/form';
// import { stringify } from 'json5';

Page({
  data: {
    province: [],
    city: [],
    pid: '',
    goodsPriceList: [],
    goodList: [],
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
    if (options.pid) {
      this.setData({
        pid: options.pid
      })
    }
    this.getByPid()
    console.log(options);
    this.form = new Form();
    if (this.formRefList) {
      this.formRefList.forEach((ref) => {
        this.form.addItem(ref);
      });
    }
    try {
      await this.getProvince();
      this.format();
    } catch (error) {
      console.error('Error:', error);
    }
  },

  onChange(value, e) {
    console.log(value, e);
  },
  handleRef(ref) {
    console.log(ref);
    if (!this.formRefList) {
      this.formRefList = [];
    }
    this.formRefList.push(ref.detail);
  },
  getByPid() {
    wx.request({
      url: getApp().globalData.baseUrl + '/wx/goods/showGoodsDetailById',
      data: {
        goodId: this.data.pid
      },
      method: "GET",
      success: (res) => {
        console.log(res.data.rows[0]);
        const goodsPriceList = {
          productId: res.data.rows[0].id,
          amount: res.data.rows[0].amount,
          num: 1
        }
        const alist = this.data.goodsPriceList.concat(goodsPriceList)
        this.setData({
          goodList: res.data.rows[0],
          goodsPriceList: alist
        });
        console.log(this.data.goodsPriceList);
      }
    });
  },

  async submit() {
    const values = await this.form.submit();
    const requestData = {
      userId: getApp().globalData.userInfo.openId,
      goodsPriceList: JSON.stringify(this.data.goodsPriceList), // 将goodsPriceList转换为JSON字符串
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