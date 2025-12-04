package com.kpl.registration.service.JwtService;

import com.kpl.registration.dto.AllOther.AdminReqVO;
import com.kpl.registration.entity.AllEntity.AdminInfo;
import com.kpl.registration.repository.AllRepo.AdminRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SaveUserImpl implements SaveUser {
    @Autowired
    AdminRepo adminRepo;

    //Save Admin Data
    @Override
    public AdminInfo saveAdminDetails(AdminReqVO adminReqVO) {
        AdminInfo adminInfo = new AdminInfo();
        var response = adminRepo.findByIdAndPassword(adminReqVO.getId(), adminReqVO.getPassword());
        if (response.isEmpty()) {

            adminInfo.setId(adminReqVO.getId());
            adminInfo.setPassword(adminReqVO.getPassword());
            adminInfo.setRoleCode("R" + adminReqVO.getId());
            return adminRepo.save(adminInfo);
        }
        return response.get();
    }
}
