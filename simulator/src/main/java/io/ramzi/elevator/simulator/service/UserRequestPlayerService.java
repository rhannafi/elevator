package io.ramzi.elevator.simulator.service;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import  io.ramzi.elevator.simulator.dao.entity.UserRequestEntity;

@Service
public class UserRequestPlayerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRequestPlayerService.class);

    @Async("taskExecutor")
    public void playUserRequests(int i,List<UserRequestEntity> userRequests){

        MDC.clear();
        MDC.put("request_id", String.valueOf(UUID.randomUUID()));
        LOGGER.info("MDC request_id from  playUserRequests : " + MDC.get("request_id"));
        Iterator<UserRequestEntity> iterator = userRequests.iterator();


        Long oldTimeStamp = 0L;
        Long newTimeStamp = 0L;
        Long period = 0L;

        while (iterator.hasNext()) {
            UserRequestEntity userRequestEntity = iterator.next();
            newTimeStamp = userRequestEntity.getTimestamp();
            
            period = newTimeStamp.longValue() - oldTimeStamp.longValue();

            //LOGGER.info("Player {} sleep during {} ms after player user request {} of sequence {}", String.valueOf(i) , period,userRequestEntity.getId());
            LOGGER.info("MDC request_id from  playUserRequests : " + MDC.get("request_id"));
            try {
                TimeUnit.MILLISECONDS.sleep(period);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }

            oldTimeStamp = Long.valueOf(newTimeStamp);
        }
    }
    
}