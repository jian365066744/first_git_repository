package com.ronglian.qd_qrcode.test.entity;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceProperty;
import javax.persistence.Table;

import org.springframework.data.annotation.Persistent;
@Entity
@Table(name="userinfo")
public class UserInfo implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;

	@Id
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	//	@Column(name="name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
