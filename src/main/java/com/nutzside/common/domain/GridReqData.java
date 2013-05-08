package com.nutzside.common.domain;

import lombok.Data;

/**
 * @author Administrator
 * Grid向后台请求的参数的集合封装成的对象，包含了排序和分页的具体要求
 */
@Data
public class GridReqData {

	/**
	 * 显示第几页
	 */
	private int page;

	/**
	 * 每页显示的记录数
	 */
	private int rows;

	/**
	 * 排序字段
	 */
	private String sidx;

	/**
	 * 升序/降序
	 */
	private String sord;


}