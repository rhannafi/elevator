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

    @Async
    public CompletableFuture<List<UserRequestEntity>> saveUserRequest(Map<String,Object> userRequestSequence) throws Exception  {
        ObjectMapper objectMapper = new ObjectMapper();
        String user_request_json = objectMapper.writeValueAsString(userRequestSequence);

        final long start = System.currentTimeMillis();
        
        List<UserRequestEntity> userRequests = parseMap(user_request_json);
        
        LOGGER.info("Saving a list of user request of size {} records", userRequests.size());
        
        playUserRequests(userRequests);
        userRequestRepository.saveAll(userRequests);
        
        LOGGER.info("Elapsed time: {}", (System.currentTimeMillis() - start));

        

        return CompletableFuture.completedFuture(userRequests);
    }

    private List<UserRequestEntity> parseMap(String user_request_json) throws Exception {
        final List<UserRequestEntity> userRequests = new ArrayList<>();

        JSONObject fullJsonObject = (JSONObject)JSONValue.parse(user_request_json);
        JSONArray callsArray = (JSONArray) fullJsonObject.get("calls");

        String str = "";
        try {
            Iterator<JSONObject> iterator = callsArray.iterator();
            while (iterator.hasNext()) {
                
                JSONObject jsonObject = iterator.next();

                str = jsonObject.get("index").toString();
                long index = Long.valueOf(str);

                str = jsonObject.get("timestamp").toString();
                long timestamp = Long.valueOf(str);
                
                str = jsonObject.get("building").toString();
                long building = Long.valueOf(str);

                str = jsonObject.get("group").toString();
                long group = Long.valueOf(str);
            
                str = jsonObject.get("elevator").toString();
                long elevator = Long.valueOf(str);
            
                str = jsonObject.get("sens").toString();
                long sens = Long.valueOf(str);

                UserRequestEntity userRequest = new UserRequestEntity(index,timestamp, building, group, elevator, sens);

                userRequests.add(userRequest);
           }
        } catch(Exception e){
            e.printStackTrace();
        }

        return userRequests;
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

            System.out.printf("sleep during %d ms\n", period);
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
