import request from '@/utils/request'

// 查询宠物类别列表
export function listPet(query) {
  return request({
    url: '/pet/pet/list',
    method: 'get',
    params: query
  })
}

// 查询宠物类别详细
export function getPet(petId) {
  return request({
    url: '/pet/pet/' + petId,
    method: 'get'
  })
}

// 新增宠物类别
export function addPet(data) {
  return request({
    url: '/pet/pet',
    method: 'post',
    data: data
  })
}

// 修改宠物类别
export function updatePet(data) {
  return request({
    url: '/pet/pet',
    method: 'put',
    data: data
  })
}

// 删除宠物类别
export function delPet(petId) {
  return request({
    url: '/pet/pet/' + petId,
    method: 'delete'
  })
}
