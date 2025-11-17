package com.kpl.registration.feign;

import com.kpl.registration.dto.AdminView.AdminViewCount;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "internal-service", url = "${base.url}", configuration = CustomFeignConfig.class)
public interface InternalFeignCall {
    @GetMapping("/adminview/AllAdmins")
    AdminViewCount getAdminData();
}
