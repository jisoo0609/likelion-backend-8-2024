package com.example.contents;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

// multipart/form-data를 받기 위한 연습
@Slf4j
@RestController
public class MultipartController {
    @PostMapping(value = "/multipart", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String multipart(
            @RequestParam("name") String name,
            // 받아주는 자료형을 MultipartFile로 설정
            @RequestParam("file")MultipartFile multipartFile
    ) throws IOException {
        log.info(multipartFile.getOriginalFilename());
        // 파일을 저장할 경로 + 파일명 지정
        Path downloadPath = Path.of("media/" + multipartFile.getOriginalFilename());
        // 저장한다
        multipartFile.transferTo(downloadPath);
        return "done";
    }
}
