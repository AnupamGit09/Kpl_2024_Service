package com.kpl.registration.repository.Student;

import com.kpl.registration.entity.StudentEntity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface StudentRepo extends JpaRepository<Student, Long> {

    @Query(value = "select * from Student where first_name=?1 and last_name=?2", nativeQuery = true)
    List<Student> existingStuWithSameName(String firstName, String lastName);


}
