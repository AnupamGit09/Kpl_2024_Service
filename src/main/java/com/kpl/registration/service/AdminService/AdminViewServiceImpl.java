package com.kpl.registration.service.AdminService;

import com.kpl.registration.dto.AdminView.AdminViewCount;
import com.kpl.registration.dto.AdminView.AdminViewVO;
import com.kpl.registration.repository.AdminDataRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminViewServiceImpl implements AdminViewService {
    @Autowired
    AdminDataRepo adminDataRepo;
    @Autowired
    ModelMapper modelMapper;

    public AdminViewCount getAllAdminData() {
        List<AdminViewVO> adminList = new ArrayList<>();

        adminDataRepo.allAdminData().forEach(adminInfo ->
                adminList.add(modelMapper.map(adminInfo, AdminViewVO.class))
        );
        return new AdminViewCount(adminList.size(),adminList);
    }
}
