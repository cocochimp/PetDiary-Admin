import request from '@/utils/request'

// 查询用户列表列表
export function listUser(query) {
  return request({
    url: '/user/user/list',
    method: 'get',
    params: query
  })
}

// 查询用户列表详细
export function getUser(userId) {
  return request({
    url: '/user/user/' + userId,
    method: 'get'
  })
}

// 新增用户列表
export function addUser(data) {
  return request({
    url: '/user/user',
    method: 'post',
    data: data
  })
}

// 修改用户列表
export function updateUser(data) {
  return request({
    url: '/user/user',
    method: 'put',
    data: data
  })
}

// 删除用户列表
export function delUser(userId) {
  return request({
    url: '/user/user/' + userId,
    method: 'delete'
  })
}

export function banUser(data) {
  return request({
    url: '/user/user/ban',
    method: 'put',
    data: data
  })
}
