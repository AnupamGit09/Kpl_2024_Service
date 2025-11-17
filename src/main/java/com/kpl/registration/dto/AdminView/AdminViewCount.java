package com.kpl.registration.dto.AdminView;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminViewCount {
    private int Count;
    private List<AdminViewVO> adminViewVO;
}
