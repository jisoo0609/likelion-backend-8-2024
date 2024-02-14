package com.example.producer;

import com.example.producer.dto.JobRequest;
import com.example.producer.dto.JobStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProducerController {
    private final ProducerService service;

//    @PostMapping("/make-job")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void makeJob(
//            @RequestParam("message")
//            String message
//    ) {
//        service.send(message);
//    }
    @PostMapping("/make-job")
    public JobStatus makeJob(
            @RequestBody
            JobRequest dto
    ) {
        return service.send(dto);
    }

    @GetMapping("/get-job")
    public JobStatus getJob(
            @RequestParam("job")
            String jobId
    ) {
        return service.getJobStatus(jobId);
    }
}
