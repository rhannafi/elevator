package io.ramzi.elevator.simulator.controllers;

import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

@RestController
public class UserRequestsPlayer {
    @GetMapping("/api/v1/test")
	public String testPresent() {
		return "I am waiting you to send the user requests file to play!";
	}

    @JsonPropertyOrder({ "timestamp", "building", "group", "elevator", "sens" })
	@RequestMapping(value = "/api/v1/process",method = RequestMethod.POST)
	public Object process(@RequestBody Map<String, Object> payload) {

        ObjectMapper objectMapper = new ObjectMapper();
        try {            
            String json = objectMapper.writeValueAsString(payload);            
            JSONObject jsonObject = (JSONObject)JSONValue.parse(json);
            JSONArray callsArray = (JSONArray) jsonObject.get("calls");
            System.out.println(callsArray.toString());

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return payload;
	}



}
