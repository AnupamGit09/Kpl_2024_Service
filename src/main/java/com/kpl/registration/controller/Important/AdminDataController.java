package com.kpl.registration.controller.Important;

import com.kpl.registration.dto.AdminView.AdminViewCount;
import com.kpl.registration.service.AdminService.AdminViewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//@Component
//@RestController
@Controller
@Slf4j
@RequestMapping("/adminview")
@CrossOrigin("*")
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
