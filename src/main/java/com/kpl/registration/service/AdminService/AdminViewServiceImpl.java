package com.kpl.registration.service.AdminService;

import com.kpl.registration.dto.AdminView.AdminViewCount;
import com.kpl.registration.dto.AdminView.AdminViewVO;
import com.kpl.registration.exception.KPLException;
import com.kpl.registration.feign.InternalFeignCall;
import com.kpl.registration.repository.AdminDataRepo;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AdminViewServiceImpl implements AdminViewService {
    @Autowired
    AdminDataRepo adminDataRepo;
    @Autowired
    ModelMapper modelMapper;
    @Value("${spring.profiles.active}")
    private String activeProfile;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private HttpServletRequest request;
    @Value("${base.url}")
    private String baseUrl;
    @Autowired
    private InternalFeignCall internalFeignCall;

    public AdminViewCount getAllAdminData() {
        try {
            List<AdminViewVO> adminList = new ArrayList<>();
            adminDataRepo.allAdminData().forEach(adminInfo -> adminList.add(modelMapper.map(adminInfo, AdminViewVO.class)));
            log.info("Hey new Log added");
            log.info("------- Active Profile is {} ----------", activeProfile);
            return new AdminViewCount(adminList.size(), adminList);
        } catch (Exception e) {
            log.error("Error fetching admin data", e);
            throw new KPLException("Get admin view Failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public AdminViewCount restCall() {
        HttpHeaders headers = new HttpHeaders();
        var token = request.getHeader("Authorization").substring(7);
        headers.setBearerAuth(token);
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);
        return restTemplate.exchange(baseUrl + "/adminview/AllAdmins", HttpMethod.GET, httpEntity, AdminViewCount.class).getBody();
    }

    public AdminViewCount feignCall() {
        return internalFeignCall.getAdminData();
    }
}
