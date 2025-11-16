package com.kpl.registration.service;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.DocumentException;
import com.kpl.registration.dto.AdminReqVO;
import com.kpl.registration.dto.RegistrationResponse;
import com.kpl.registration.entity.AdminInfo;
import com.kpl.registration.entity.PlayerInfo;

import freemarker.core.ParseException;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

public interface PlayerService {
  //	GenericVO savePlayerInfo(PlayerRequetVO playerRequetVO, byte[] imageData, byte[]
  // docDataFront,byte[] docDataBack)
  //			throws IOException, MessagingException, TemplateException ;

  RegistrationResponse getRegistrationStatus(String searchParam, String password);

  AdminInfo saveAdminDetails(AdminReqVO adminReqVO);



  void sendMailOnPaymentValidation(List<Long> registartionIDS)
      throws MessagingException,
          TemplateNotFoundException,
          MalformedTemplateNameException,
          ParseException,
          IOException,
          TemplateException;

  void resetPasswordMail(Long phNumber)
      throws MessagingException,
          TemplateNotFoundException,
          MalformedTemplateNameException,
          ParseException,
          IOException,
          TemplateException;

  void sendMailOnSold(PlayerInfo playerInfo)
      throws MessagingException,
          TemplateNotFoundException,
          MalformedTemplateNameException,
          ParseException,
          IOException,
          TemplateException;
}
