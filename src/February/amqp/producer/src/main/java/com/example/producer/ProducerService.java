package com.example.producer;

import com.example.producer.dto.JobPayload;
import com.example.producer.dto.JobRequest;
import com.example.producer.dto.JobStatus;
import com.example.producer.jpa.JobEntity;
import com.example.producer.jpa.JobRepository;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProducerService {
    private final RabbitTemplate rabbitTemplate;
    private final Queue jobQueue;

    private final JobRepository jobRepository;
    private final Gson gson;

    public JobStatus send(JobRequest request) {
        // Consumer가 확인할 메시지를 만든다.
        String jobId = UUID.randomUUID().toString();
        String filename = request.getFilename();
        JobPayload payload = new JobPayload(
                jobId,
                filename,
                // 서버의 어디에 파일을 저장해 두었는지
                String.format("/media/user-uploaded/raw/%s", filename)
        );
        // 전달한 정보를 데이터베이스에 기록
        JobEntity newJob = new JobEntity();
        newJob.setJobId(jobId);
        newJob.setStatus("WAIT");
        // resultPath는 Consumer가 기록해준다.
        JobStatus jobStatus = JobStatus.fromEntity(jobRepository.save(newJob));
        // 모든 정보가 준비되면 메시지를 브로커에 전송
        rabbitTemplate.convertAndSend(jobQueue.getName(), gson.toJson(payload));
        log.info("Sent Job: {}", jobId);
        // 사용자에게 응답
        return jobStatus;
    }

    public JobStatus getJobStatus(String jobId) {
        return JobStatus.fromEntity(jobRepository.findByJobId(jobId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    public void send(String message) {
        rabbitTemplate.convertAndSend(
                jobQueue.getName(), message
        );
        log.info("sent message: {}", message);
    }

}
