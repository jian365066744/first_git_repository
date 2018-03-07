/**
 * 
 */
package com.ronglian.qd_qrcode.test.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/** 
* @author zhangjian 
* @version JDK1.8
* 创建时间：2017年12月2日 上午10:55:49 
* 类说明 :
*/
@Entity
@Table(name="info")
public class Info implements Serializable{

	/** 
	* @author zhangjian
	* @version JDK1.8
	* 创建时间：2017年12月2日 上午10:55:49 
	* 方法说明 :
	*/
	@Id
	private Integer id;
	private String sex ;
	private String age;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	@ManyToOne
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
}
