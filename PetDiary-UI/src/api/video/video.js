import request from '@/utils/request'

// 查询视频管理列表
export function listVideo(query) {
  return request({
    url: '/video/video/list',
    method: 'get',
    params: query
  })
}

// 查询视频管理详细
export function getVideo(contentId) {
  return request({
    url: '/video/video/' + contentId,
    method: 'get'
  })
}

// 新增视频管理
export function addVideo(data) {
  return request({
    url: '/video/video',
    method: 'post',
    data: data
  })
}

// 修改视频管理
export function updateVideo(data) {
  return request({
    url: '/video/video',
    method: 'put',
    data: data
  })
}

// 封禁视频管理
export function banVideo(data) {
  return request({
    url: '/video/video/ban',
    method: 'put',
    data: data
  })
}

// 删除视频管理
export function delVideo(contentId) {
  return request({
    url: '/video/video/' + contentId,
    method: 'delete'
  })
}
