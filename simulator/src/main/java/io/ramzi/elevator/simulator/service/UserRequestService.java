package io.ramzi.elevator.simulator.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import io.ramzi.elevator.simulator.dao.entity.UserRequestEntity;
import io.ramzi.elevator.simulator.dao.repository.UserRequestRepository;

@Service
public class UserRequestService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserRequestService.class);
    
    @Autowired
    private UserRequestRepository userRequestRepository;

    @Async("taskExecutor")    
    public CompletableFuture<List<UserRequestEntity>> saveUserRequest(Map<String,Object> userRequestSequence) throws Exception  {
        ObjectMapper objectMapper = new ObjectMapper();
        String user_request_json = objectMapper.writeValueAsString(userRequestSequence);

        final long start = System.currentTimeMillis();
        
        List<UserRequestEntity> userRequests = parseMap(user_request_json);
        
        LOGGER.info("Saving a list of user request of size {} records", userRequests.size());
        
        userRequestRepository.saveAll(userRequests);

        changeGroups();

        LOGGER.info("Elapsed time: {}", (System.currentTimeMillis() - start));                

        return CompletableFuture.completedFuture(userRequests);
    }

    private List<UserRequestEntity> parseMap(String user_request_json) throws Exception {
        final List<UserRequestEntity> userRequests = new ArrayList<>();

        JSONObject fullJsonObject = (JSONObject)JSONValue.parse(user_request_json);
        JSONArray callsArray = (JSONArray) fullJsonObject.get("calls");

        ObjectMapper objectMapper = new ObjectMapper();
        
        try {

            Iterator<JSONObject> iterator = callsArray.iterator();
            while (iterator.hasNext()) {
                
                JSONObject jsonObject = iterator.next();

                UserRequestEntity userRequest = objectMapper.readValue(jsonObject.toJSONString(),UserRequestEntity.class);                
                LOGGER.info("User request value is {}",userRequest.toString());

                userRequests.add(userRequest);
           }
        } catch(Exception e){
            e.printStackTrace();
        }

        return userRequests;
    }
    
    @Async("taskExecutor")
    public CompletableFuture<String> changeGroups() {
        for (int i=0 ; i<50 ; i++) {

            long size = userRequestRepository.count();
            for (long j=1L; j< size; j++) {

                UserRequestEntity userRequest = userRequestRepository.findById(1L).get();
                LOGGER.info("Changing group userrequest={} and size repo = {}" , j , size);
                userRequest.setGroup(userRequest.getElevator() + j);
                userRequestRepository.save(userRequest);
            }
        }
        return CompletableFuture.completedFuture("Changing group is done");
    }

    @Async
    public void playUserRequests(List<UserRequestEntity> userRequests){
        Iterator<UserRequestEntity> iterator = userRequests.iterator();


        Long oldTimeStamp = 0L;
        Long newTimeStamp = 0L;
        Long period = 0L;

        while (iterator.hasNext()) {
            UserRequestEntity userRequestEntity = iterator.next();
            newTimeStamp = userRequestEntity.getTimestamp();
            
            period = newTimeStamp.longValue() - oldTimeStamp.longValue();

            LOGGER.info("sleep during {} ms after player user request {} of sequence {}", period,userRequestEntity.getId());
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
