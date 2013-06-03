package com.nutzside.system.domain;

import java.util.List;

import lombok.Data;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Many;
import org.nutz.dao.entity.annotation.ManyMany;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.json.JsonField;

//alter table shiro.system_user add  locked bit(1) NOT NULL default 0;
@Data
@Table("SYSTEM_USER")
public class User {

	@Id
	private Long id;
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 20)
	private String number;
	@Column
	@ColDefine(type = ColType.CHAR, width = 44)
	@JsonField(ignore = true)
	private String password;
	@Column
	@ColDefine(type = ColType.CHAR, width = 24)
	@JsonField(ignore = true)
	private String salt;
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 20)
	private String name;
	/**
	 * 性别 01:男;02:女（由系统枚举表维护）
	 */
	@Column
	@ColDefine(type = ColType.CHAR, width = 2)
	private String gender;
	@Column
	private Integer age;
	@Column
	private String birthday;
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 20)
	private String phone;
	@ManyMany(target = Role.class, relation = "SYSTEM_USER_ROLE", from = "USERID", to = "ROLEID")
	private List<Role> roles;
	@ManyMany(target = Message.class, relation = "SYSTEM_MESSAGE_RECEIVERUSER", from = "USERID", to = "MESSAGEID")
	private List<Message> receivedMessages;
	@Many(target = Message.class, field = "senderUserId")
	private List<Message> sentMessages;

}
