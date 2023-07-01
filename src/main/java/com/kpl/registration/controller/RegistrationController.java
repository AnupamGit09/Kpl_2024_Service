package com.kpl.registration.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import com.kpl.registration.dto.AdminReqVO;
import com.kpl.registration.dto.GenericVO;
import com.kpl.registration.dto.PlayerRequetVO;
import com.kpl.registration.dto.RegistrationResponse;
import com.kpl.registration.entity.AdminInfo;
import com.kpl.registration.entity.ImageInfo;
import com.kpl.registration.repository.ImageRepo;
import com.kpl.registration.repository.PlayerRepository;
import com.kpl.registration.service.PlayerService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/kpl/registration/api")
@CrossOrigin(origins = "*")
public class RegistrationController {
	@Autowired
	PlayerService playerService;
	@Autowired
	ImageRepo imageRepo;
	@Autowired
	PlayerRepository playerRepository;

	@Autowired
	private ResourceLoader resourceLoader;

	public static final String CONTENT_DISPOSITION = "Content-Disposition";
	public static final String PDF_MIME_TYPE = "application/pdf";
	public static final String ATTACHMENT_FILENAME = "attachment; filename=";
	public static final String GENERATION_DATE = "yyMMdd";
	public static final String DATE_FORMAT = "ddMMyyyy";

	@GetMapping("/status")
	public String ApplicationStatus() {
		return "Application is Running";
	}

	@PostMapping("/completeRegistration")
	public GenericVO saveRegistration(@RequestBody PlayerRequetVO playerRequetVO,
			@RequestParam("file") MultipartFile file, @RequestParam("file") MultipartFile docFile) throws IOException {
		byte[] imageData = file.getBytes();
		byte[] docData = docFile.getBytes();
		return playerService.savePlayerInfo(playerRequetVO, imageData, docData);
	}

	@GetMapping("/getYourRegistrationStatus")
	public RegistrationResponse registrationStatus(@RequestParam String id, @RequestParam String password)
			throws IOException {
		return playerService.getRegistrationStatus(id, password);
	}

//	category specific player PDF

	@GetMapping("generate/playerPdf")
	public void generueSpecificPlayerPdf(HttpServletResponse response, @RequestParam("generue") String generue)
			throws Exception {

		response.setContentType(PDF_MIME_TYPE);
		String headerKey = CONTENT_DISPOSITION;
		String headerValue = "owner" + generue + ".pdf";
		response.setHeader(headerKey, headerValue);
		playerService.generatePdfByClassification(response, generue);

	}

	@PostMapping("/masterImage")
	public String uploadImage(@RequestParam("file") MultipartFile file, @RequestParam String name) throws IOException {
		ImageInfo imageInfo = new ImageInfo();
		imageInfo.setImageName(name);
		imageInfo.setImage(file.getBytes());
		imageRepo.save(imageInfo);
		return "Image Master data has been uploaded successfully";
	}

//	update player category to List A

	@PutMapping("/specialPlayer")
	public String updateSpecialPlayerCategory(@RequestParam List<Long> registartionIDS) throws IOException {
		playerRepository.updatePlayerCategory(registartionIDS);
		return "players category has been change to List A";
	}

//	update payment validation

	@PutMapping("/paymentUpdate")
	public String paymentUpdate(@RequestParam List<Long> registartionIDS) throws IOException {
		playerRepository.paymentUpdate(registartionIDS);
		return "payment details updated";
	}

	@GetMapping("generate/AllplayerPdf")
	public void generueSpecificPlayerPdfForCommitte(HttpServletResponse response) throws Exception {

		response.setContentType(PDF_MIME_TYPE);
		String headerKey = CONTENT_DISPOSITION;
		String headerValue = "AllPlayer" + ".pdf";
		response.setHeader(headerKey, headerValue);
		playerService.generueSpecificPlayerPdfForCommitte(response);

	}

	@GetMapping("generate/finalPlayerListPdf")
	public void generueFinalPlayerPdf(HttpServletResponse response, @RequestParam("generue") String generue)
			throws Exception {

		response.setContentType(PDF_MIME_TYPE);
		String headerKey = CONTENT_DISPOSITION;
		String headerValue = "committe" + generue + ".pdf";
		response.setHeader(headerKey, headerValue);
		playerService.generateFinalPlayerPdf(response, generue);

	}

	@GetMapping("/passwordReset")
	public String resetPassword(@RequestParam Long phNumber, @RequestParam Long pinCode, @RequestParam Long aadharNo,
			@RequestParam String password) throws IOException {
		if (phNumber.toString().length() != 10) {
			return "Phone Number Must be 10 digit";
		}
		if (pinCode.toString().length() != 6) {
			return "Pin Code Must be 6 digit";
		}
		if (aadharNo.toString().length() != 12) {
			return "Aadhaar Number Must be 12 digit";
		}
		var length = password.toString().length();
		if (!(length > 3 && length < 9)) {
			return "Password Must be between 4 to 8 character";
		}
		var phNo = playerRepository.findByPhNumber(phNumber);
		var phNobyPIN = playerRepository.findByPinCode(pinCode);
		var phNobyaadhar = playerRepository.findByAaddharNo(aadharNo);
		if (phNo != null) {
			if (pinCode.equals(phNobyPIN)) {
				if (phNo.equals(phNobyaadhar)) {
					playerRepository.updatePassword(password, phNumber);
					return "Success";
				} else {
					return "Incorrect Aadhaar Number";
				}
			} else {
				return "Incorrect Pin Code";
			}
		} else {
			return "Incorrect Phone Number";
		}
	}

	@PostMapping("/saveAdmin")
	public AdminInfo saveAdmin(@RequestBody AdminReqVO adminReqVO) throws IOException {
		return playerService.saveAdminDetails(adminReqVO);
	}

	@GetMapping("/downloadGenerueSpImage")
	public ResponseEntity<Resource> downloadImages(@RequestParam String generue) {
		// Retrieve the list of images (assuming you have it available)
		List<byte[]> images = playerRepository.findAllImageByGenerue(generue);

		try {
			// Create a temporary file for the ZIP
			File tempFile = File.createTempFile("images", ".zip");
			FileOutputStream fos = new FileOutputStream(tempFile);
			ZipOutputStream zipOut = new ZipOutputStream(fos);

			// Add each image to the ZIP file
			for (int i = 0; i < images.size(); i++) {
				byte[] imageData = images.get(i);
				String fileName = (i + 1) + ".jpg";

				// Create a new entry in the ZIP file
				zipOut.putNextEntry(new ZipEntry(fileName));

				// Write the image data to the ZIP file
				zipOut.write(imageData);

				// Close the current entry
				zipOut.closeEntry();
			}

			// Close the ZIP output stream
			zipOut.close();

			// Load the temporary ZIP file as a resource
			Resource zipFileResource = resourceLoader.getResource("file:" + tempFile.getAbsolutePath());

			// Set the appropriate response headers for file download
			HttpHeaders headers = new HttpHeaders();
			
			headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+generue+".zip");

			// Return the ZIP file as a response entity
			return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_OCTET_STREAM)
					.body(zipFileResource);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/downloadAllDocImage")
	public ResponseEntity<Resource> downloadAllDocImage() throws IOException {
		List<byte[]> images = playerRepository.findAllDoc();

		try {
			// Create a temporary file for the ZIP
			File tempFile = File.createTempFile("images", ".zip");
			FileOutputStream fos = new FileOutputStream(tempFile);
			ZipOutputStream zipOut = new ZipOutputStream(fos);

			// Add each image to the ZIP file
			for (int i = 0; i < images.size(); i++) {
				byte[] imageData = images.get(i);
				String fileName = (i + 1) + ".jpg";

				// Create a new entry in the ZIP file
				zipOut.putNextEntry(new ZipEntry(fileName));

				// Write the image data to the ZIP file
				zipOut.write(imageData);

				// Close the current entry
				zipOut.closeEntry();
			}

			// Close the ZIP output stream
			zipOut.close();

			// Load the temporary ZIP file as a resource
			Resource zipFileResource = resourceLoader.getResource("file:" + tempFile.getAbsolutePath());

			// Set the appropriate response headers for file download
			HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=documents.zip");

			// Return the ZIP file as a response entity
			return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_OCTET_STREAM)
					.body(zipFileResource);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}