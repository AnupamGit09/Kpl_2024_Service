package com.kpl.registration.dto.AdminView;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({ "count", "adminViewVO" })
public class AdminViewCount {
    private int Count;
    private List<AdminViewVO> adminViewVO;
}
