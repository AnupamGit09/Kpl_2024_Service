package com.kpl.registration.dto.AdminView;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminViewVO {
    private Long adminId;
    private String id;
    private String password;
    private String roleCode;
}
