package com.kpl.registration.controller.NewLogic;

import com.kpl.registration.service.Student.Interfaces.ContentStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/theory")
public class TheoryController {
    @Autowired
    @Qualifier("video-content")
    private ContentStrategy contentStrategy;
    @GetMapping("/content")
    public ResponseEntity<String> getContent(){
        return ResponseEntity.status(HttpStatus.OK).body(contentStrategy.content());
    }
}
