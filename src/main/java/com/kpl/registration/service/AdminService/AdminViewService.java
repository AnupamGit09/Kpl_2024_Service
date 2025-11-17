package com.kpl.registration.service.AdminService;

import com.kpl.registration.dto.AdminView.AdminViewCount;

public interface AdminViewService {
    AdminViewCount getAllAdminData();

    AdminViewCount restCall();

    AdminViewCount feignCall();
}
