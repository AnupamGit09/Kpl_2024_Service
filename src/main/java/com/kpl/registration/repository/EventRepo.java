package com.kpl.registration.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kpl.registration.entity.EventCount;

@Repository
public interface EventRepo extends JpaRepository<EventCount, Long> {
	@Transactional
	@Modifying
	@Query(value = "update event_count set rules_pdf=rules_pdf+1  where event_id=1", nativeQuery = true)
	void updateRulesPdfCount();

	@Transactional
	@Modifying
	@Query(value = "update event_count set owner_pdf=owner_pdf+1  where event_id=1", nativeQuery = true)
	void updateOwnerPdfCount();

}