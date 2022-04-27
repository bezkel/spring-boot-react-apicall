package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.demo.models.User;
import com.example.demo.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public Iterable<User> list() {
        return userService.list();
    }

    @RequestMapping(value="/get", method=RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getUsers() {
        
        // get users from db
        List<User> dbusers = (List<User>) userService.list();
        // create Response
        Map<String, Object> response = new HashMap<>();
        ArrayList<Map<String, Object>> countries = new ArrayList<Map<String, Object>>();
        
        // create the map for single country
        ArrayList<String> countryList = new ArrayList<String>();
        for (User user: dbusers) {
            String tmpcountry = user.getLocation().getCountry();
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
        for (User user : dbusers) {
            String usersCountry = user.getLocation().getCountry();
            Map<String, Object> usermap = new HashMap<>();
            usermap.put("name", user.getName().getFirst() + " " +  user.getName().getLast());
            usermap.put("gender", user.getGender());
            usermap.put("email", user.getEmail());
            for (Map<String, Object> item: countries) {
                if (item.get("name") == usersCountry) {
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
