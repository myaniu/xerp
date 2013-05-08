package com.nutzside.system.domain;

import java.sql.Timestamp;

import lombok.Data;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;
@Data
@Table("SYSTEM_CLIENT")
public class Client {
	@Id
	private Long id;
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 100)
	private String sessionId;
	@Column
	private Long userId;
	@One(target = User.class, field = "userId")
	private User user;
	@Column
	private Timestamp logonTime;
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 20)
	private String ipAddr;
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 200)
	private String userAgent;

	
}