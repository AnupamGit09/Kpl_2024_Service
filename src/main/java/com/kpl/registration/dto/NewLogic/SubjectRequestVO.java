package com.kpl.registration.dto.NewLogic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectRequestVO {
    private Long studentId;
    private String subject;
    private Long marks;
}
