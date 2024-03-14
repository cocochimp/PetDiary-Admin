import {
  Form
} from 'antd-mini/Form/form';
import cityList from './city';

Page({
  data: {
    imageUrl: '', // 上传的图片或视频地址
    // that = this,
    onUpload(localFile) {
      return new Promise((resolve) => {
        console.log('上传的图片为：', localFile);
        wx.uploadFile({
          filePath: localFile.path,
          name: 'file',
          url: getApp().globalData.functionUrl + '/upload', //服务器端接收图片的路径
          success: function (res) {
            var userJson = JSON.parse(res.data);
            console.log("上传成功：", userJson.data.url); //发送成功回调
            // that.setData({
            //     imageUrl: userJson.data.url
            // })
          },
          fail: function (res) {
            console.log("上传失败：", res); //发送失败回调，可以在这里了解失败原因
          }
        })
        setTimeout(() => {
          resolve(imageUrl);
        }, 2000);
      });
    },
    cityList,
    toastShow: false,
    catList: [],
    dogList: [],
    animalList: []
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
    this.getCat()
    this.getDog()
  },

  onChange(fileList) {
    // 这里的数据包括上传失败和成功的图片列表，如果需要筛选出上传成功的图片需要在此处理
    console.log('图片列表：', fileList);
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
  async submit() {
    const values = await this.form.submit();
    console.log(values);
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

});