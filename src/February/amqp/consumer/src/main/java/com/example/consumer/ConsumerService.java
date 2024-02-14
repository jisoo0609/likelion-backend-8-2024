package com.example.consumer;

import com.example.consumer.dto.JobPayload;
import com.example.consumer.jpa.JobEntity;
import com.example.consumer.jpa.JobRepository;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsumerService {
    private final JobRepository jobRepository;
    private final Gson gson;

    @RabbitListener(queues = "#{queue.name}")
    public void receive(String message) throws InterruptedException {
        log.info("received: {}", message);
        // 처리해야할 작업을 받는다.
        JobPayload jobPayload = gson.fromJson(message, JobPayload.class);
        String jobId = jobPayload.getJobId();
        log.info("job id: {}", jobId);
        // DB에서 처리할 작업의 상세정보를 받아왔다.
        JobEntity jobEntity = jobRepository.findByJobId(jobId)
                // 잘못된 정보가 포함된 요청은 다시 처리하지 않도록
                .orElseThrow(() -> new AmqpRejectAndDontRequeueException(jobId));

        // 처리 되기 전에 처리 중이라고 상태를 업데이트 한다.
        jobEntity.setStatus("PROCESSING");
        jobEntity = jobRepository.save(jobEntity);
        log.info("Start processing: {}", jobId);

        // 6초 대기
        Thread.sleep(12000);

        // 작업이 완료되었다.
        jobEntity.setStatus("DONE");
        jobEntity.setResultPath(
                String.format("/media/user-uploaded/processed/%s", jobPayload.getFilename())
        );
        jobRepository.save(jobEntity);
        log.info("completed: {}", jobId);
    }
}
