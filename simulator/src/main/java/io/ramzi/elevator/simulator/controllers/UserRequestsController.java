package io.ramzi.elevator.simulator.controllers;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.ramzi.elevator.simulator.service.UserRequestService;

@RestController
public class UserRequestsController {

    @Autowired
    UserRequestService userRequestService;
    
    @GetMapping("/api/v1/test")
	public String testPresent() {
		return "I am waiting you to send the user requests file to play!";
	}

    @JsonPropertyOrder({ "timestamp", "building", "group", "elevator", "sens" })
	@RequestMapping(value = "/api/v1/process_json",method = RequestMethod.POST)
	public void processJsone(@RequestBody Map<String, Object> userRequestSequence) throws Exception {        
        for(int i=0 ; i<20; i++) {
            userRequestService.saveUserRequest(userRequestSequence);
        }
	}



}
