package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.demo.models.User;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;

@RestController
@RequestMapping("/api")
public class NewController {
    @Value("${randomuser.url}")
    private  String BASE_URL;
    @Value("${randomuser.userSize}")
    private String USERSIZE;

    private UserService userService;

    public NewController(UserService userService) {
        this.userService = userService;
    }

    @Scheduled(fixedRateString = "#{${randomuser.period} * 1000}")
    @GetMapping("/getUsers")
    public Map<String, Object> getUsers() {
    
        String url = BASE_URL + "&results=" + USERSIZE;

        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);

    
            ObjectMapper objectMapper = new ObjectMapper();

            List<User> remoteUsers = new ArrayList<User>();

            JsonNode rootNode = objectMapper.readTree(forEntity.getBody());
            //System.out.println(jsonNode);
            JsonNode users;
            if (rootNode.has("results")) {
                users = rootNode.get("results");
            } else {
                users = rootNode;
            }
            
            for (JsonNode user : users) {
                String firstName = user.get("name").get("first").textValue();
                String lastName = user.get("name").get("last").textValue();

                Map<String, Object> userMap = new HashMap<>();
                userMap.put("country", user.findPath("country").textValue());
                userMap.put("name", firstName + " " + lastName);
                userMap.put("gender", user.get("gender").textValue());
                userMap.put("email", user.get("email").textValue());
                
                User remoteUser = objectMapper.convertValue(userMap, User.class);
                remoteUsers.add(remoteUser);
            }
            this.userService.clear();
            this.userService.save(remoteUsers);
            return this.convertUsers(remoteUsers);
        } catch (NullPointerException e) {
            System.out.println("ERROR: Data missing. country, name, gender, email are required");
            // get users from db
            List<User> dbusers = (List<User>) userService.list();
            return this.convertUsers(dbusers); 
        } catch (JsonProcessingException e) {
            System.out.println("ERROR: JsonProcessing");
            // get users from db
            List<User> dbusers = (List<User>) userService.list();
            return this.convertUsers(dbusers); 
        } catch (ResourceAccessException e) {
            System.out.println("ERROR: Could not reach");
            // get users from db
            List<User> dbusers = (List<User>) userService.list();
            return this.convertUsers(dbusers); 
        }

        
        
    }

    private Map<String, Object> convertUsers(List<User> userList) {
        // create Response
        Map<String, Object> response = new HashMap<>();
        ArrayList<Map<String, Object>> countries = new ArrayList<Map<String, Object>>();
        
        // create the map for single country
        ArrayList<String> countryList = new ArrayList<String>();
        for (User user: userList) {
            String tmpcountry = user.getCountry();
            if (!countryList.contains(tmpcountry)) {
                countryList.add(tmpcountry);
                // create subobjects
                Map<String, Object> country = new HashMap<>();
                country.put("name", tmpcountry);
                ArrayList<Map<String, Object>> users = new ArrayList<Map<String, Object>>();
                country.put("users", users);
                countries.add(country);
            }
        }


        // create the map for single user an add it to country
        for (User user : userList) {
            String usersCountry = user.getCountry();
            Map<String, Object> usermap = new HashMap<>();
            usermap.put("name", user.getName());
            usermap.put("gender", user.getGender());
            usermap.put("email", user.getEmail());
            for (Map<String, Object> item: countries) {
                if (item.get("name").equals(usersCountry)) {
                    // dont know why this cast is necessary here
                    ArrayList listCast = (ArrayList) item.get("users");
                    listCast.add(usermap);
                }
            }
            
        }

        // create final response and return it
        response.put("countries", countries);
        return response; 
    }

}
