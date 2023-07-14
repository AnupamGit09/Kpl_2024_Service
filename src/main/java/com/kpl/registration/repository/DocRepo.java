package com.kpl.registration.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kpl.registration.entity.DocInfo;

@Repository
public interface DocRepo extends JpaRepository<DocInfo, Long> {

	@Query(value = "select * from doc_info order by registration_id", nativeQuery = true)
	List<DocInfo> findAllImageByGenerue();
	
	@Query(value = "select image from doc_info where registration_id=?1", nativeQuery = true)
	byte[] findByregistrationId(Long registrationId);

	@Query(value = "select doc_image_front from doc_info order by registration_id", nativeQuery = true)
	List<byte[]> findAllDocFront();

	
	@Query(value = "select doc_image_back from doc_info order by registration_id", nativeQuery = true)
	List<byte[]> findAllDocBack();
}