package com.kiwihouse.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class MenuResModel {
    private Integer id;

    private Integer menuId;

    private Integer resourceId;
    
    private String name;

    
}