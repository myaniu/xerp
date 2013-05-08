package com.nutzside.system.domain;

import java.sql.Timestamp;
import java.util.List;

import lombok.Data;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.ManyMany;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;
@Data
@Table("SYSTEM_MESSAGE")
public class Message {
	@Id
	private Long id;
	@One(target = User.class, field = "senderUserId")
	private User sender;
	@Column
	private Long senderUserId;
	@ManyMany(target = User.class, relation = "SYSTEM_MESSAGE_RECEIVERUSER", from = "MESSAGEID", to = "USERID")
	private List<User> receivers;
	@ManyMany(target = PoolFile.class, relation = "SYSTEM_MESSAGE_POOLFILE", from = "MESSAGEID", to = "POOLFILEID")
	private List<PoolFile> attachments;
	@Column
	private Timestamp date;
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 500)
	private String title;
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 10000)
	private String content;
	/**
	 * 消息状态 0：草稿；1：已发送；2：已删除(发信人方删除)
	 */
	@Column
	private Integer state;
	@Column
	private Integer type;

	
}