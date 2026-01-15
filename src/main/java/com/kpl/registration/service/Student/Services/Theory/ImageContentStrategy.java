package com.kpl.registration.service.Student.Services.Theory;

import com.kpl.registration.service.Student.Interfaces.ContentStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
//@Primary
@Qualifier("image-content")
public class ImageContentStrategy implements ContentStrategy {
    @Override
    public String content() {
        System.out.println("Image Content Strategy");
        return "Image Content Strategy";
    }
}
