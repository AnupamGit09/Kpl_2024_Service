package com.kpl.registration.dto.NewLogic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectResponseListVO {
    private Long studentId;
    private String Name;
    private double average;
    private List<SubjectResponseVO> subjectRes;
}
