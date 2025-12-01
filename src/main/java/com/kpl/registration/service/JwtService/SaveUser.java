package com.kpl.registration.service.JwtService;

import com.kpl.registration.dto.AllOther.AdminReqVO;
import com.kpl.registration.entity.AllEntity.AdminInfo;

public interface SaveUser {
    AdminInfo saveAdminDetails(AdminReqVO adminReqVO);

}
