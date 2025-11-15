package com.kpl.registration.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;

import com.kpl.registration.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.client.RestTemplate;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.kpl.registration.dto.AdminReqVO;
import com.kpl.registration.dto.GenericVO;
import com.kpl.registration.dto.PlayerRequetVO;
import com.kpl.registration.dto.RegistrationResponse;
import com.kpl.registration.entity.AdminInfo;
import com.kpl.registration.entity.DocInfo;
import com.kpl.registration.entity.PlayerInfo;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PlayerServiceImpl implements PlayerService {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    PlayerRepo2024 playerRepo2024;
    @Autowired
    AdminRepo adminRepo;
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    DocRepo docRepo;
    @Autowired
    OwnerRepo ownerRepo;
    @Autowired
    Configuration config;
    @Value("${spring.mail.username}")
    private String emailUsername;
//	String telegramBotUrl = "https://api.telegram.org/bot6637753416:AAHb7DHnfrvEl6Aje0RfyrumAkZjZglxXHU/sendmessage?chat_id=@kpl2023updates&text=";
//	String telegramTestBotUrl = "https://api.telegram.org/bot6637753416:AAHb7DHnfrvEl6Aje0RfyrumAkZjZglxXHU/sendmessage?chat_id=@test2017Grp&text=";

    // @Override
    public GenericVO savePlayerInfo(PlayerRequetVO playerRequetVO, byte[] imageData, byte[] docDataFront,
                                    byte[] docDataBack) throws IOException, MessagingException, TemplateException {
        GenericVO genericVO = new GenericVO();
        PlayerInfo playerInfo = new PlayerInfo();
        DocInfo docInfo = new DocInfo();
        playerInfo.setAadharNo(playerRequetVO.getAadharNo());

        playerInfo.setEmailId(playerRequetVO.getEmailId());
        playerInfo.setGenerue(playerRequetVO.getGenerue());
        playerInfo.setPhNo(playerRequetVO.getPhNo());
        playerInfo.setPinCode(playerRequetVO.getPinCode());
        playerInfo.setPlayerAddress(playerRequetVO.getPlayerAddress());
        playerInfo.setPlayerFirstName(playerRequetVO.getPlayerFirstName());
        playerInfo.setPlayerLastName(playerRequetVO.getPlayerLastName());

        playerInfo.setRegistrationTime(LocalDateTime.now(Clock.systemUTC()));
        playerInfo.setGenerue(playerRequetVO.getGenerue());
        playerInfo.setDateOfBirth(playerRequetVO.getDob());
        playerInfo.setPassword(playerRequetVO.getPassword());
        playerInfo.setLocation(playerRequetVO.getLocation());

        docInfo.setImage(imageData);
        docInfo.setDocImageFront(docDataFront);
        docInfo.setDocImageBack(docDataBack);

        var res = playerRepository.save(playerInfo);


        log.info("Registration ID for " + playerRequetVO.getPlayerFirstName() + " is " + res.getRegistrationId());
        docInfo.setRegistrationId(res.getRegistrationId());
        docRepo.save(docInfo);


        var count = playerRepository.findCount(playerRequetVO.getPhNo());
        if (count > 1) {
            String message = "Hey, @Insanebaby2017 there is a major issue :" + playerInfo.getPlayerFirstName() + " "
                    + playerInfo.getPlayerLastName() + " and his registration id and phone number are :"
                    + res.getRegistrationId() + " ," + playerInfo.getPhNo();
//			restTemplate.getForObject(telegramBotUrl + message, String.class);
        }


        String firstname = playerInfo.getPlayerFirstName();
        String message = "Hey, @RAVVAN23 we have a new Registration!,His name is :" + firstname + " "
                + playerInfo.getPlayerLastName() + " and his registration id and phone number are :"
                + res.getRegistrationId() + " ," + playerInfo.getPhNo();
//		restTemplate.getForObject(telegramBotUrl + message, String.class);

        log.info("User has been Registered successfully" + ",name : " + firstname);

        genericVO.setResponse("You have been successfully Registered");
        genericVO.setRegistrationID(res.getRegistrationId().toString());
        return genericVO;

    }

    public void sendMail(PlayerInfo playerInfo) throws MessagingException, TemplateNotFoundException,
            MalformedTemplateNameException, ParseException, IOException, TemplateException {
        Map<String, Object> model = new HashMap<>();
        String phNu = playerInfo.getPhNo().toString();
        String password = playerInfo.getPassword();
        var regID = playerRepository.findByPhNu(phNu);

        model.put("regid", regID);
        model.put("firstname", playerInfo.getPlayerFirstName());
        model.put("name", playerInfo.getPlayerFirstName() + " " + playerInfo.getPlayerLastName());
        model.put("location", playerInfo.getLocation());
        model.put("mail", playerInfo.getEmailId());
        model.put("phNo", phNu);
        model.put("category", playerInfo.getGenerue());
        model.put("address", playerInfo.getPlayerAddress());
        model.put("password", password);

        var message = javaMailSender.createMimeMessage();
        var mimeMessageHelper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        Template mailTemplate = config.getTemplate("registration.ftl");
        var htmlTemp = FreeMarkerTemplateUtils.processTemplateIntoString(mailTemplate, model);

        mimeMessageHelper.setFrom(emailUsername);
        mimeMessageHelper.setTo(playerInfo.getEmailId());
        mimeMessageHelper.setText(htmlTemp, true);
        mimeMessageHelper.setSubject(playerInfo.getPlayerFirstName()
                + ",You have been registered successfully for KPL season 5 grand Auction");
        javaMailSender.send(message);

    }

    @Override
    public void sendMailOnSold(PlayerInfo playerInfo) throws MessagingException, TemplateNotFoundException,
            MalformedTemplateNameException, ParseException, IOException, TemplateException {
        Map<String, Object> model = new HashMap<>();
        var message = javaMailSender.createMimeMessage();
        var mimeMessageHelper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        mimeMessageHelper.setFrom(emailUsername);
        Template mailTemplate = config.getTemplate("soldTeam.ftl");

        model.put("firstname", playerInfo.getPlayerFirstName());
        model.put("soldteam", playerInfo.getSoldTeam());
        model.put("soldamount", playerInfo.getSoldAmount());

        var ownerInfo = ownerRepo.ownerInformation(playerInfo.getSoldTeam());
        if (ownerInfo.isPresent()) {
            model.put("ownername", ownerInfo.get().getOwnerName());
            model.put("phnum", ownerInfo.get().getOwnerPhNo().toString());
        }

        var htmlTemp = FreeMarkerTemplateUtils.processTemplateIntoString(mailTemplate, model);
        mimeMessageHelper.setTo(playerInfo.getEmailId());
        mimeMessageHelper.setText(htmlTemp, true);
        mimeMessageHelper.setSubject(playerInfo.getPlayerFirstName() + ",Hurray! you have been sold");
        log.info("name : " + playerInfo.getPlayerFirstName() + " , Mail ID : " + playerInfo.getEmailId());
        javaMailSender.send(message);

    }

    @Override
    public void sendMailOnPaymentValidation(List<Long> registartionIDS) throws MessagingException,
            TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {
        Map<String, Object> model = new HashMap<>();
        var message = javaMailSender.createMimeMessage();
        var mimeMessageHelper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        mimeMessageHelper.setFrom(emailUsername);
        Template mailTemplate = config.getTemplate("paymentValidation.ftl");

        List<PlayerInfo> playerInfo = playerRepository.findByRegistriondList(registartionIDS);
        for (PlayerInfo info : playerInfo) {
            model.put("firstname", info.getPlayerFirstName());
            var htmlTemp = FreeMarkerTemplateUtils.processTemplateIntoString(mailTemplate, model);
            mimeMessageHelper.setTo(info.getEmailId());
            mimeMessageHelper.setText(htmlTemp, true);
            mimeMessageHelper
                    .setSubject(info.getPlayerFirstName() + ",Your payment status has been Updated");
            String text = "@RAVVAN23 @Kalajaduu13 @emotionalclown  Payment status updated for : "
                    + info.getPlayerFirstName() + " " + info.getPlayerLastName()
                    + " ,and his Mail ID,Reg ID are : " + info.getEmailId() + ","
                    + info.getRegistrationId();
            log.info(text);

//			restTemplate.getForObject(telegramBotUrl + text, String.class);
            javaMailSender.send(message);
        }

    }

    @Override
    public void resetPasswordMail(Long phNumber) throws MessagingException, TemplateNotFoundException,
            MalformedTemplateNameException, ParseException, IOException, TemplateException {
        Map<String, Object> model = new HashMap<>();
        var message = javaMailSender.createMimeMessage();
        var mimeMessageHelper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        mimeMessageHelper.setFrom(emailUsername);
        Template mailTemplate = config.getTemplate("passwordReset.ftl");

        var playerInfo = playerRepository.findByMailOrPhNumber(String.valueOf(phNumber));

        model.put("firstname", playerInfo.getPlayerFirstName());
        var htmlTemp = FreeMarkerTemplateUtils.processTemplateIntoString(mailTemplate, model);
        mimeMessageHelper.setTo(playerInfo.getEmailId());
        mimeMessageHelper.setText(htmlTemp, true);
        mimeMessageHelper.setSubject(playerInfo.getPlayerFirstName() + ",password changed");
        log.info("name : " + playerInfo.getPlayerFirstName() + " , Mail ID : " + playerInfo.getEmailId());
        javaMailSender.send(message);

    }

    @Override
    public RegistrationResponse getRegistrationStatus(String id, String password) {
        var playerExistance = playerRepository.findByMailOrPhNumberandpassword(id, password);
        if (playerExistance != null) {
            if (playerExistance.getPaymentValidation() != null) {
                playerExistance.setPaymentValidation("Completed");
            } else {
                playerExistance.setPaymentValidation("Pending Stage");
            }
            return modelMapper.map(playerExistance, RegistrationResponse.class);

        }
        RegistrationResponse registrationResponse = new RegistrationResponse();
        registrationResponse.setPlayerFirstName("No Record Found");
        registrationResponse.setPlayerLastName("No Record Found");
        return registrationResponse;
    }
//	live feed data

    @Override
    public AdminInfo saveAdminDetails(AdminReqVO adminReqVO) {
        AdminInfo adminInfo = new AdminInfo();
        adminInfo.setAdminId(adminReqVO.getAdminId());
        adminInfo.setId(adminReqVO.getId());
        adminInfo.setPassword(adminReqVO.getPassword());
        adminRepo.deletebyAdminId(adminReqVO.getAdminId());
        return adminRepo.save(adminInfo);
    }
}