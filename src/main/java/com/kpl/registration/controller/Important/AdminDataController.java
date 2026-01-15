package com.kpl.registration.controller.Important;

import com.kpl.registration.dto.AdminView.AdminViewCount;
import com.kpl.registration.service.AdminService.AdminViewService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@Component
@RestController
@Slf4j
@RequestMapping("/adminView")
@CrossOrigin("*")
@Tag(name = "Admin Api",description = "All the API's related to Admins")
public class AdminDataController {
    @Autowired
    AdminViewService adminViewService;

    @GetMapping("/AllAdmins")
    public ResponseEntity<AdminViewCount> getAdminData() {
        var response = adminViewService.getAllAdminData();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/restCall")
    public ResponseEntity<AdminViewCount> restCall() {
        var res = adminViewService.restCall();
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("/feignCall")
    public ResponseEntity<AdminViewCount> feignCall() {
        var res = adminViewService.feignCall();
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

}
