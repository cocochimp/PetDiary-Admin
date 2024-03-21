async function getProvince() {
  try {
    const res = await wx.request({
      url: getApp().globalData.baseUrl + '/wx/goods/showLocationByParentId',
      data: {
        parentId: 0
      },
      method: "GET",
      success: (res) => {
        console.log(res.data.rows);
      }
    });
    console.log('Request result:', res);

    if (!res.data || !res.data.rows) {
      console.error('Invalid response:', res);
      return [];
    }

    const {
      data: {
        rows
      }
    } = res;

    const provinces = [];

    for (const item of rows) {
      const childrenRes = await wx.request({
        url: getApp().globalData.baseUrl + '/wx/goods/showLocationByParentId',
        data: {
          parentId: item.id
        },
        method: "GET"
      });

      console.log('Children request result:', childrenRes);

      if (!childrenRes.data || !childrenRes.data.rows) {
        console.error('Invalid children response:', childrenRes);
        continue; // 如果子请求失败，跳过当前循环
      }

      const {
        data: {
          rows: childrenRows
        }
      } = childrenRes;

      const children = childrenRows.map(child => ({
        value: child.id,
        label: child.name
      }));

      provinces.push({
        value: item.id,
        label: item.name,
        children
      });
    }

    console.log('Provinces:', provinces);
    return provinces;
  } catch (error) {
    console.error('Request error:', error);
    return [];
  }
}

Page({
  data: {
    province: [],
    city: [],
  },

  onLoad(options) {
    this.getProvince();
  },

  async getProvince() {
    try {
      const provinces = await getProvince();
      this.setData({
        province: provinces
      });
    } catch (error) {
      console.error('Error in getProvince:', error);
    }
  },

  onShow() {},

  onShareAppMessage() {
    // 用户点击右上角分享
  }
});