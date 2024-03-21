package com.ruoyi.core.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteCarGoodsRequest implements Serializable{
    private static final long serialVersionUID = 1L;
    private List<String> productIdList;
    private String userId;
}