// mock/data.js
var Mock = require("./WxMock.js"); 

// 模拟用户数据
const userData = Mock.mock({
  'users|5-10': [{
    'id|+1': 1,
    'name': '@cname',
    'age|18-60': 1,
    'gender|1': ['male', 'female']
  }]
});

module.exports = {
  userData
};
