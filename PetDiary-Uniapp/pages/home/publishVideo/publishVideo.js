import {
  Form
} from 'antd-mini/Form/form';
let imageUrl = '1';

function onUpload(localFile) {
  return new Promise((resolve) => {
    console.log('上传的图片为：', localFile);
    wx.uploadFile({
      filePath: localFile.path,
      name: 'file',
      url: getApp().globalData.functionUrl + '/upload', //服务器端接收图片的路径
      success: (res) => { // 使用箭头函数确保回调中的 this 指向正确的对象
        var userJson = JSON.parse(res.data);
        console.log("上传成功：", userJson.data.url); //发送成功回调
        imageUrl = userJson.data.url
        console.log(imageUrl);
      },
      fail: (res) => { // 使用箭头函数确保回调中的 this 指向正确的对象
        console.log("上传失败：", res); //发送失败回调，可以在这里了解失败原因
      }
    });
    setTimeout(() => {
      resolve(imageUrl);
    }, 2000);
  });
}
Page({
  data: {
    imageUrl: imageUrl, // 上传的图片或视频地址
    onUpload,
    toastShow: false,
    catList: [],
    dogList: [],
    animalList: [],
    title: null,
    description: null,
    userId: '',
    contentType: 1,
    coverPath: null,
    petId: null,
    videoSrc: null, // 用于绑定选择的视频文件路径
  },

  onInput(event) {
    console.log('输入的内容：', event.detail.html);
    // 处理富文本编辑器输入内容
  },

  onLoad() {
    this.form = new Form();
    if (this.formRefList) {
      this.formRefList.forEach((ref) => {
        this.form.addItem(ref);
      });
    }
    this.setData({
      userId: getApp().globalData.userInfo.openId
    })
    this.getCat()
    this.getDog()
  },

  // 获取图片路径
  onChange(fileList) {
    // 这里的数据包括上传失败和成功的图片列表，如果需要筛选出上传成功的图片需要在此处理
    console.log('图片列表：', fileList);
    const {
      detail
    } = fileList
    var paths = detail.map((item) => {
      return item.url
    })
    var pathString = paths.join(",");
    this.setData({
      coverPath: pathString
    })
  },

  chooseImage() {
    wx.chooseImage({
      count: 1,
      success: (res) => {
        const tempFilePath = res.tempFilePaths[0];
        this.setData({
          imageUrl: tempFilePath
        });
        console.log('选择的图片地址：', tempFilePath);
        // 处理选择图片后的逻辑
      }
    });
  },

  onPickerChange(event) {
    const index = event.detail.value;
    this.setData({
      selectedTag: this.data.petTags[index]
    });
    console.log('选择的宠物标签：', this.data.petTags[index]);
    // 处理选择宠物标签后的逻辑
  },

  handleRef(ref) {
    if (!this.formRefList) {
      this.formRefList = [];
    }
    this.formRefList.push(ref.detail);
  },
  reset() {
    this.form.reset();
  },
  chooseVideo: function () {
    return new Promise((resolve, reject) => {
      wx.chooseVideo({
        sourceType: ['album', 'camera'],
        maxDuration: 60,
        camera: 'back',
        success: (res) => {
          console.log(res.tempFilePath); // 选择的视频文件路径
          wx.uploadFile({
            filePath: res.tempFilePath,
            name: 'file',
            url: getApp().globalData.functionUrl + '/upload', //服务器端接收图片的路径
            success: (res) => { // 使用箭头函数确保回调中的 this 指向正确的对象
              var userJson = JSON.parse(res.data);
              console.log("上传成功：", userJson.data.url); //发送成功回调
              this.setData({
                videoSrc: userJson.data.url
              });
              console.log(this.data.videoSrc);
              resolve(); // 视频上传成功，执行 resolve
            },
            fail: (res) => { // 使用箭头函数确保回调中的 this 指向正确的对象
              console.log("上传失败：", res); //发送失败回调，可以在这里了解失败原因
              reject(res); // 视频上传失败，执行 reject
            }
          })
        },
        fail: (error) => {
          console.log("选择视频失败：", error);
          reject(error); // 视频选择失败，执行 reject
        }
      });
    });
  },

  submitAfterChooseVideo: function () {
    // 选择视频并上传成功后执行 submit 函数
    this.chooseVideo()
      .then(() => {
        // 在视频选择并上传成功后执行 submit 函数
        this.submit();
      })
      .catch((error) => {
        console.error("选择视频或上传失败：", error);
      });
  },


  submit() {
    if (!this.data.title || !this.data.description || !this.data.coverPath || !this.data.petId || !this.data.videoSrc) {
      console.log(this.data.videoSrc);
      wx.showToast({
        title: '存在内容未填写',
        duration: 1000,
        icon: 'none'
      })
      return
    }
  
 if(this.data.videoSrc) {
  console.log(this.data.videoSrc);
  console.log(11);
  // 提示提交中
  wx.showLoading({
    title: '提交中...',
    mask: true
  });

  wx.request({
    url: getApp().globalData.baseUrl + '/wx/home/insertContentInfo',
    method: "POST",
    data: {
      title: this.data.title,
      description: this.data.description,
      userId: this.data.userId,
      contentType: this.data.contentType,
      coverPath: this.data.coverPath,
      videoPath: this.data.videoSrc,
      petId: this.data.petId,
    },
    success: (response) => {
      console.log(response);
      // 隐藏提交中提示
      wx.hideLoading();
      // 提示提交成功
      wx.showToast({
        title: '提交成功',
        icon: 'success',
        duration: 2000
      });

      // 跳转页面
      wx.redirectTo({
        url: '/pages/user/index/index',
      });
    },
    fail: (error) => {
      console.error(error);

      // 隐藏提交中提示
      wx.hideLoading();

      // 提示提交失败
      wx.showToast({
        title: '提交失败，请重试',
        icon: 'none',
        duration: 2000
      });
    }
  });
 }
  },
  

  chooseImageAndSubmit: function () {
    wx.chooseImage({
      count: 1,
      success: (res) => {
        const tempFilePath = res.tempFilePaths[0];
        this.setData({
          imageUrl: tempFilePath
        });
        console.log('选择的图片地址：', tempFilePath);

        // 视频上传成功后执行提交操作
        this.chooseVideoAndSubmit();
      }
    });
  },


  showToast() {
    this.setData({
      toastShow: true,
    });
  },
  handleCloseToast() {
    this.setData({
      toastShow: false,
    });
  },
  // 获取猫狗类型
  getCat() {
    wx.request({
      url: 'http://localhost:9501/wx/home/showPetNameByPetType?type=0',
      method: "POST",

      success: (res) => {
        const rows = res.data.rows
        this.setData({
          catList: rows
        })
        const cList = rows.map(pet => {
          return {
            label: pet.name,
            value: pet.petId
          }
        })
        const catList = {
          label: '猫',
          children: cList,
          value: '0',
        }
        const updatedAnimalList = this.data.animalList.concat(catList);
        this.setData({
          animalList: updatedAnimalList,
        });
      },
      fail: (err) => {
        console.log(err);
      }
    })
  },
  getDog() {
    wx.request({
      url: 'http://localhost:9501/wx/home/showPetNameByPetType?type=1',
      method: "POST",
      success: (res) => {
        const rows = res.data.rows
        this.setData({
          dogList: rows
        })
        const dList = rows.map(pet => {
          return {
            label: pet.name,
            value: pet.petId

          }
        })
        const dogList = {
          label: '狗',
          children: dList,
          value: '1',
        }
        const updatedAnimalList = this.data.animalList.concat(dogList);
        this.setData({
          animalList: updatedAnimalList,
        });

      },
      fail: (err) => {
        console.log(err);
      }
    })
  },
  // 获取宠物的id
  handleOk(e) {
    console.log(e.detail);
    this.setData({
      petId: e.detail[0][1]
    })
  },
  // 获取标题
  onTitleBlur(e) {
    this.setData({
      title: e.detail
    })
  },
  // 获取内容
  onContentBlur(e) {
    this.setData({
      description: e.detail
    })
  },


  deleteVideo: function () {
    // 删除已上传的视频
    this.setData({
      videoSrc: ''
    });
  }
});