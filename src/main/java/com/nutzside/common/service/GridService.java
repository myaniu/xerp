package com.nutzside.common.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.OrderBy;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.sql.SqlCallback;
import org.nutz.lang.Strings;
import org.nutz.service.IdEntityService;

import com.nutzside.common.domain.grid.GridReqData;
import com.nutzside.common.domain.grid.StandardgridResData;
import com.nutzside.common.domain.grid.StandardgridResDataRow;
import com.nutzside.common.util.DaoUtils;

/**
 * @author Administrator
 * 该服务类用于：通过 WHERE 条件和分页信息，从数据库查询数据，并封装为AdvancedgridResData或StandardgridResData格式。
 * 使用时可以继承该类。
 * 注：实体的主键是数值型的。
 * @param <T>
 */
public abstract class GridService<T> extends IdEntityService<T> {

	public GridService(Dao dao) {
		super(dao);
	}

	

	/**
	 * 通过自定义Sql语句和排序分页信息jqReq，从数据库查询数据，将各条记录和分页信息封装为StandardJqgridResData格式。
	 * @param sql
	 * @param jqReq
	 * @return
	 */
	public StandardgridResData getStandardJqgridResData(Sql sql, GridReqData jqReq) {
		// 合计记录总数
		int count = DaoUtils.queryCount(dao(), sql.toString());
		// 创建分页信息
		Pager pager = createPager(jqReq, count);
		// sql 设置排序
		DaoUtils.addOrderBy(sql, createOrderBy(jqReq), null);
		// sql 设置分页
		sql.setPager(pager);
		// sql 设置回调
		sql.setCallback(new SqlCallback() {
			public Object invoke(Connection conn, ResultSet rs, Sql sql) throws SQLException {
				List<StandardgridResDataRow> rows = new ArrayList<StandardgridResDataRow>();
				int columnCount = rs.getMetaData().getColumnCount();
				long i = 1;
				while (rs.next()) {
					StandardgridResDataRow row = new StandardgridResDataRow();
					row.setId(i);
					for (int j = 1; j <= columnCount; j++) {
						row.addCellItem(rs.getString(j));
					}
					rows.add(row);
					i++;
				}
				return rows;
			}
		});

		// 查询
		dao().execute(sql);
		List<StandardgridResDataRow> rows = sql.getList(StandardgridResDataRow.class);

		// 开始封装jqGrid的json格式数据类
		StandardgridResData jq = new StandardgridResData();
		jq.initPager(pager);
		jq.setRows(rows);
		//jq.setSystemMessage("查询成功!", null, null);
		return jq;
	}

	/**
	 * 创建分页信息
	 * @param jqReq
	 * @param count
	 * @return
	 */
	private Pager createPager(GridReqData jqReq, int count) {
		// 获取页码
		int page = jqReq.getPage();
		int pageNumber = page == 0 ? 1 : page;
		// 获取每页记录数
		int jqReqRows = jqReq.getRows();
		int pageSize = jqReqRows == 0 ? 10 : jqReqRows;
		// 创建分页信息
		Pager pager = new Pager();
		pager.setPageNumber(pageNumber);
		pager.setPageSize(pageSize);
		pager.setRecordCount(count);
		return pager;
	}

	/**
	 * 创建排序信息
	 * @param jqReq
	 * @return
	 */
	private OrderBy createOrderBy(GridReqData jqReq) {
		// 获取排序字段
		String sidx = jqReq.getSidx();
		String sortColumn = Strings.isEmpty(sidx) ? "ID" : sidx;
		// 获取正序/逆序
		String sord = jqReq.getSord();
		String sortOrder = Strings.isEmpty(sord) ? "asc" : sord;
		// 创建排序信息
		OrderBy orderBy = Cnd.orderBy();
		if ("asc".equals(sortOrder)) {
			orderBy = orderBy.asc(sortColumn);
		} else {
			orderBy = orderBy.desc(sortColumn);
		}
		return orderBy;
	}
}