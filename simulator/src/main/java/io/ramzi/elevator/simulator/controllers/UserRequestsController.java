package io.ramzi.elevator.simulator.controllers;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.ramzi.elevator.simulator.dto.UserRequestDto;
import io.ramzi.elevator.simulator.service.UserRequestService;

@RestController
public class UserRequestsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRequestsController.class);
    @Autowired
    UserRequestService userRequestService;
    
    @GetMapping("/api/v1/alive")
	public String testPresent() {
		return "I am waiting you to send the user requests file to play!";
	}

    @JsonPropertyOrder({ "user_request_sequence_id" , "user_request_id" , "timestamp", "building", "group", "elevator", "sens" })
	@RequestMapping(value = "/api/v1/process_json",method = RequestMethod.POST)
	public void processJson(@RequestBody Map<String, Object> userRequestSequence) throws Exception {        
        userRequestService.saveUserRequest(userRequestSequence);
        userRequestService.saveUserRequest(userRequestSequence);
        userRequestService.saveUserRequest(userRequestSequence);
        userRequestService.saveUserRequest(userRequestSequence);
        userRequestService.saveUserRequest(userRequestSequence);
	}

    @PostMapping(path = "/api/v1/user_request", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserRequestDto createUserRequestDto(@RequestBody Map<String, Object> body) throws JsonMappingException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();   
        String user_request = objectMapper.writeValueAsString(body);
        UserRequestDto userRequestDto = objectMapper.readValue(user_request,UserRequestDto.class); 
        LOGGER.info(userRequestDto.toString());
        return userRequestDto;
    }
    


}
