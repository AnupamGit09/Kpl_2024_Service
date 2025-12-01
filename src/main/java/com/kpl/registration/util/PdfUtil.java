package com.kpl.registration.util;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.kpl.registration.repository.AllRepo.DocRepo;
import com.kpl.registration.repository.AllRepo.ImageRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.MalformedURLException;

public class PdfUtil {
    @Autowired
    ImageRepo imageRepo;
    @Autowired
    DocRepo docRepo;
    public Image getImageData(String name) throws BadElementException, MalformedURLException, IOException {

        byte[] imageData;

        imageData = imageRepo.findByImageName(name);

        Image imageDataEn = Image.getInstance(imageData);
        imageDataEn.setAlignment(Element.ALIGN_CENTER);
        imageDataEn.scalePercent(70);
        imageDataEn.setBorderWidth(1f);
        imageDataEn.scaleToFit(50, 50);
        imageDataEn.setAlignment(Element.ALIGN_LEFT);
        imageDataEn.setBorder(Rectangle.NO_BORDER);
        return imageDataEn;
    }

    public Image getPlayerSpecificImageData(Long registrationId)
            throws BadElementException, MalformedURLException, IOException {

        byte[] imageData;

        imageData = docRepo.findByregistrationId(registrationId);

        Image imageDataEn = Image.getInstance(imageData);
        imageDataEn.setAlignment(Element.ALIGN_CENTER);
        imageDataEn.scalePercent(90);
        imageDataEn.scaleToFit(90, 90);
        imageDataEn.setAlignment(Element.ALIGN_LEFT);
        imageDataEn.setBorder(Rectangle.NO_BORDER);
        return imageDataEn;
    }

}
