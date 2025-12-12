package com.kpl.registration.service.Student.Services.Theory;

import com.kpl.registration.service.Student.Interfaces.ContentStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("video-content")
public class VideoContentStrategy implements ContentStrategy {
    @Override
    public String content() {
        System.out.println("Video Content Strategy");
        return "Video Content Strategy";
    }
}
