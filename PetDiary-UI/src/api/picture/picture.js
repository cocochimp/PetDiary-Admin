import request from '@/utils/request'

// 查询图文管理列表
export function listPicture(query) {
  return request({
    url: '/picture/picture/list',
    method: 'get',
    params: query
  })
}

// 查询图文管理详细
export function getPicture(contentId) {
  return request({
    url: '/picture/picture/' + contentId,
    method: 'get'
  })
}

// 新增图文管理
export function addPicture(data) {
  return request({
    url: '/picture/picture',
    method: 'post',
    data: data
  })
}

// 修改图文管理
export function updatePicture(data) {
  return request({
    url: '/picture/picture',
    method: 'put',
    data: data
  })
}

// 拉黑图文管理
export function banPicture(data) {
  return request({
    url: '/picture/picture/ban',
    method: 'put',
    data: data
  })
}

// 删除图文管理
export function delPicture(contentId) {
  return request({
    url: '/picture/picture/' + contentId,
    method: 'delete'
  })
}
