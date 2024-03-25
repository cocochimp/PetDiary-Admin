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
    hour: "00",
    minute: "00",
    second: "00",
    millisecond: "00",
    list: [],
    OperateType: 1,
    activeId: 1,
  },

  lifetimes: {
    attached() {
      this.getSeckill()
      let now = new Date(); // 获取当前日期时间
      now.setHours(0, 0, 0, 0); // 将时、分、秒、毫秒设置为0
      now.setDate(now.getDate() + 1); // 增加一天，得到第二天0点的时间

      let finishTime = now.getTime(); // 获取第二天0点的时间戳
      let nowTime = new Date().getTime();
      let time = finishTime - nowTime;
      console.log(time);
      let hour = Math.floor(time / (3600 * 1000));
      time = time % (3600 * 1000);
      let minute = Math.floor(time / (60 * 1000));
      time = time % (60 * 1000);
      let second = Math.floor(time / 1000);
      let millisecond = Math.floor(time % 1000); // 获取毫秒部分
      console.log(millisecond);
      let isFinish = false;
      const id = setInterval(() => {
        millisecond -= 1000;
        if (millisecond < 0) {
          millisecond += 1000;
          if (--second < 0) {
            second += 60;
            if (--minute < 0) {
              minute += 60;
              if (--hour < 0) {
                isFinish = true;
              }
            }
          }
        }
        if (isFinish) {
          this.setData({
            hour: "00",
            minute: "00",
            second: "00",
            millisecond: "00"
          });
          clearInterval(id);
        } else {
          this.setData({
            hour: hour < 10 ? "0" + hour : hour,
            minute: minute < 10 ? "0" + minute : minute,
            second: second < 10 ? "0" + second : second,
            millisecond: millisecond < 100 ? "0" + millisecond : millisecond // 保证毫秒部分始终为三位数
          });
        }
      }, 1000);
    }

  },

  /**
   * 组件的方法列表
   */
  methods: {
    handleImageError(e) {
      console.log(1);
      const {
        index
      } = e.currentTarget.dataset; // 获取当前图片所在的索引
      const {
        list
      } = this.data; // 获取列表数据
      list[index].coverPhoto = 'https://pet-diary.oss-cn-beijing.aliyuncs.com/2024-03/1.png'; // 将加载失败的图片替换为默认图片路径或者其他替代内容

      this.setData({
        list: list
      }); // 更新数据
      console.log(this.data.list);
    },
    getSeckill() {
      wx.request({
        url: getApp().globalData.baseUrl + `/wx/goods/showGoodsList?OperateType=${this.data.OperateType}&categoryId=${this.data.activeId}`,
        method: "GET",
        success: (res) => {
          const {
            data
          } = res
          this.setData({
            list: data.rows
          })
          console.log(data);
        }
      })
    }

  }
});