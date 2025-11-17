package com.kpl.registration.repository;

import com.kpl.registration.entity.AdminInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminDataRepo extends JpaRepository<AdminInfo, Long> {
    @Query(value = "select * from admin_mst_table", nativeQuery = true)
    List<AdminInfo> allAdminData();
}