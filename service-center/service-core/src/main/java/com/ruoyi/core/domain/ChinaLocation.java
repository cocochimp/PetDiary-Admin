package com.ruoyi.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChinaLocation implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 地址id */
    private int id;

    /** 地名id */
    private String name;

    /** 父类id */
    private int parentId;

}