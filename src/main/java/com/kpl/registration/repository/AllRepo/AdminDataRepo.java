package com.kpl.registration.repository.AllRepo;

import com.kpl.registration.entity.AllEntity.AdminInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminDataRepo extends JpaRepository<AdminInfo, Long> {
    @Query("select a from AdminInfo a")
    List<AdminInfo> allAdminData();
}