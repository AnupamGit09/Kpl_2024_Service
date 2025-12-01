package com.kpl.registration.entity.StudentEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Subject implements Serializable {
    private static final long serialVersionUID = 3980366880241829960L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "subject_id")
    @Column(name = "subject_id")
    private Long subjectId;

    @Column(name = "usn")
    private String usn;

    @Column(name = "subject")
    private String subject;

    @Column(name = "marks")
    private long marks;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;
}
