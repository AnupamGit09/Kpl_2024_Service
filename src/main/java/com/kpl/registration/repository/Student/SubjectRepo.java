package com.kpl.registration.repository.Student;

import com.kpl.registration.entity.StudentEntity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepo extends JpaRepository<Subject, Long> {
    @Query(value = "select * from Subject where student_id=?1", nativeQuery = true)
    List<Subject> findSubjectByStudentId(Long studentId);
}
