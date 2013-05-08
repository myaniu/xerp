package com.xerp.domain.store;

import java.sql.Timestamp;
import java.util.List;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Many;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;

import com.nutzside.system.domain.Organization;
import com.nutzside.system.domain.User;
//出库单表
@Table("ERP_LeaveStock")
public class LeaveStock {
	
	
	@Id
	private Long id;/* 出库单编号, 主键 */
	@Column
	private Timestamp BackDate; /* 入库时间 */
	@Column
	private String BatchNo; /* 批次编号 */
	@Column
	private Long UserId;/*出库人 ,外键 ( 参照User表)  */
	@Column
	private Long orgId;/* 出库部门 ,外键 ( 参照 Organization 表 ) */
	@Column
	private Long StoreHouseId;/* 所出仓库 ,外键 ( 参照 STOREHOUSE 表) */
	@Column
	private Long toStoreHouseId;/* 所入仓库 ,外键 ( 参照 STOREHOUSE 表) */
	@One(target = User.class, field = "UserId")
	private User users; /* 出库人 ,外键 ( 参照User表) */
	@One(target = Organization.class, field = "orgId")
	private Organization organization; /* 出库部门 ,外键 ( 参照 Organization 表 ) */
	@One(target = StoreHouse.class, field = "StoreHouseId")
	private StoreHouse storehouse; /* 所出仓库 ,外键 ( 参照 STOREHOUSE 表) */
	@One(target = StoreHouse.class, field = "StoreHouseId")
	private StoreHouse tostorehouse; /* 所入仓库 ,外键 ( 参照 STOREHOUSE 表) */
	@Many(target=LeaveStockDetail.class,field="stockid")
	private List<LeaveStockDetail> leaveStockDetailSet;// 

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Timestamp getBackDate() {
		return BackDate;
	}
	public void setBackDate(Timestamp backDate) {
		BackDate = backDate;
	}
	public String getBatchNo() {
		return BatchNo;
	}
	public void setBatchNo(String batchNo) {
		BatchNo = batchNo;
	}
	public Long getUserId() {
		return UserId;
	}
	public void setUserId(Long userId) {
		UserId = userId;
	}
	public Long getStoreHouseId() {
		return StoreHouseId;
	}
	public void setStoreHouseId(Long storeHouseId) {
		StoreHouseId = storeHouseId;
	}
	public Long getToStoreHouseId() {
		return toStoreHouseId;
	}
	public void setToStoreHouseId(Long toStoreHouseId) {
		this.toStoreHouseId = toStoreHouseId;
	}
	public User getUsers() {
		return users;
	}
	public void setUsers(User users) {
		this.users = users;
	}



	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Organization getOrganization() {
		return organization;
	}
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	public StoreHouse getStorehouse() {
		return storehouse;
	}
	public void setStorehouse(StoreHouse storehouse) {
		this.storehouse = storehouse;
	}
	public StoreHouse getTostorehouse() {
		return tostorehouse;
	}
	public void setTostorehouse(StoreHouse tostorehouse) {
		this.tostorehouse = tostorehouse;
	}
	public List<LeaveStockDetail> getLeaveStockDetailSet() {
		return leaveStockDetailSet;
	}
	public void setLeaveStockDetailSet(List<LeaveStockDetail> leaveStockDetailSet) {
		this.leaveStockDetailSet = leaveStockDetailSet;
	}
	
	
}
