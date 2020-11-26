package com.kiwihouse.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 	菜单资源Bean
 * @author cmx
 *
 */
@ToString
@Setter
@Getter
public class MenuRes {
	private Integer id;
	private Integer menuId;
	private Integer resourceId;
	private String menuName;
	private String resName;
	private Integer roleId;
}
