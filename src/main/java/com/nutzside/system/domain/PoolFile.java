package com.nutzside.system.domain;

import java.io.File;
import java.sql.Timestamp;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.filepool.FilePool;
import org.nutz.filepool.NutFilePool;
import org.nutz.ioc.Ioc;
import org.nutz.json.JsonField;
@Data
@Table("SYSTEM_POOLFILE")
public class PoolFile {
	@Id
	@JsonField(ignore = true)
	private Long id;
	@Column
	private Long idInPool;
	@Column
	private String name;
	@Column
	@JsonField(ignore = true)
	private String suffix;
	@Column
	@JsonField(ignore = true)
	private String poolIocName;
	@Column
	@JsonField(ignore = true)
	private Timestamp uploadDate;
	@Column
	@JsonField(ignore = true)
	private Long ownerUserId;
	private String clientLocalPath;
	private String contentType;

	
	/**
	 * 获取该文件的File对象,需提供其所在文件池的Ioc
	 * @param ioc
	 * @return
	 */
	public File getFile(Ioc ioc) {
		FilePool pool = ioc.get(NutFilePool.class, poolIocName);
		return pool.getFile(idInPool, suffix);
	}
}