package com.kpl.registration.dto.NewLogic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectResponseVO {
    private Long subjectId;
    private String usn;
    private String subject;
    private long marks;
}
