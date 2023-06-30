package com.kpl.registration.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kpl.registration.entity.AdminInfo;

@Repository
public interface AdminRepo extends JpaRepository<AdminInfo, Long> {

	@Query(value = "select id from admin_mst_table where id=?1 and password=?2", nativeQuery = true)
	String adminLoginValidation(String id, String password);

}