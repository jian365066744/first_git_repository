package com.ronglian.qd_qrcode.test.dao;
import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ronglian.qd_qrcode.test.entity.UserInfo;

public interface UserInfoDao extends JpaRepository<UserInfo, Integer>,JpaSpecificationExecutor<UserInfo>{
	UserInfo findById(Integer id);
	List<UserInfo> findByIdGreaterThanAndNameLike(Integer id,String name);
	@Query("from UserInfo where name=:name")
	List<UserInfo> findByName(@Param("name")String name);
}