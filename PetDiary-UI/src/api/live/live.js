import request from '@/utils/request'

// 查询直播列表
export function listLive(query) {
  return request({
    url: '/live/live/list',
    method: 'get',
    params: query
  })
}

// 查询直播详细
export function getLive(liveId) {
  return request({
    url: '/live/live/' + liveId,
    method: 'get'
  })
}

// 新增直播
export function addLive(data) {
  return request({
    url: '/live/live',
    method: 'post',
    data: data
  })
}

// 修改直播
export function updateLive(data) {
  return request({
    url: '/live/live',
    method: 'put',
    data: data
  })
}

// 封禁直播
export function banLive(data) {
  return request({
    url: '/live/live/ban',
    method: 'put',
    data: data
  })
}

// 删除直播
export function delLive(liveId) {
  return request({
    url: '/live/live/' + liveId,
    method: 'delete'
  })
}
