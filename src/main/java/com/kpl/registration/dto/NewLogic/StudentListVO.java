package com.kpl.registration.dto.NewLogic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentListVO {
    private Long studentId;
    private String firstName;
    private String lastName;
}
