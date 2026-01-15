package com.kpl.registration.service.AdminService;

import com.kpl.registration.dto.AdminView.AdminViewCount;
import com.kpl.registration.dto.AdminView.AdminViewVO;
import com.kpl.registration.exception.KPLException;
import com.kpl.registration.feign.InternalFeignCall;
import com.kpl.registration.repository.AllRepo.AdminDataRepo;
import feign.FeignException;
import feign.RetryableException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
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
    @Value("${url}")
    private String baseUrl;
    @Autowired
    private InternalFeignCall internalFeignCall;

    @Override
    public AdminViewCount getAllAdminData() {
        try {
            List<AdminViewVO> adminList = new ArrayList<>();
            adminDataRepo.allAdminData().forEach(adminInfo ->
                    adminList.add(modelMapper.map(adminInfo, AdminViewVO.class)));
            log.info("Hey new Log added");
            log.info("------- Active Profile is {} ----------", activeProfile);
            return new AdminViewCount(adminList.size(), adminList);
        } catch (Exception e) {
            log.error("Error fetching admin data", e);
            throw new KPLException("Get admin view Failed Parent Call", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public AdminViewCount restCall() {
        try {
            HttpHeaders headers = new HttpHeaders();
            var token = request.getHeader("Authorization").substring(7);
            headers.setBearerAuth(token);
            HttpEntity<?> httpEntity = new HttpEntity<>(headers);
            var restResponse = restTemplate.exchange(baseUrl + "/adminView/AllAdmins",
                    HttpMethod.GET, httpEntity, AdminViewCount.class);
            return restResponse.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            log.error("REST call failed with status {} and body {}", ex.getStatusCode(),
                    ex.getResponseBodyAsString(), ex);
            throw new KPLException(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (ResourceAccessException ex) {
            log.error("Parent service call is unreachable", ex);
            throw new KPLException(ex.getMessage(), HttpStatus.NOT_FOUND);

        } catch (Exception ex) {
            log.error("Unexpected error while calling admin service", ex);
            throw new KPLException(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public AdminViewCount feignCall() {
        try {
            return internalFeignCall.getAdminData();
        } catch (FeignException.FeignClientException | FeignException.FeignServerException ex) {
            log.error("Feign call failed with status {} and body {}", ex.status(), ex.contentUTF8(), ex);
            throw new KPLException(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (RetryableException ex) {
            log.error("Parent service call is unreachable", ex);
            throw new KPLException(ex.getMessage(), HttpStatus.NOT_FOUND);

        } catch (Exception ex) {
            log.error("Unexpected error while calling admin service", ex);
            throw new KPLException(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }
}
