package sg.edu.nus.iss.gamerecommender.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import sg.edu.nus.iss.gamerecommender.model.Account;
import sg.edu.nus.iss.gamerecommender.model.Game.Genre;
import sg.edu.nus.iss.gamerecommender.model.ProfileGamer;
import sg.edu.nus.iss.gamerecommender.model.User;
import sg.edu.nus.iss.gamerecommender.model.User.Role;
import sg.edu.nus.iss.gamerecommender.service.AccountService;
import sg.edu.nus.iss.gamerecommender.service.UserService;

@RestController
@RequestMapping("/api/user")
public class LoginRestController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private UserService userService;
	
	
    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody String body) {
        try {
        	JsonObject inUserJson = JsonParser.parseString(body).getAsJsonObject();	
        	String username = inUserJson.get("username").getAsString();
            String password = inUserJson.get("password").getAsString();
            String displayName = inUserJson.get("displayName").getAsString();
            
            User newUser = userService.createUser(new User(displayName," ", Role.GAMER));
            Account newAccount = accountService.createAccount(new Account(username, password, newUser));     
            return new ResponseEntity<Account>(newAccount, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody String body) {
    	 try {
    		 JsonObject loginData = JsonParser.parseString(body).getAsJsonObject();
             String username = loginData.get("username").getAsString();
             String password = loginData.get("password").getAsString();
             
             Account account = accountService.authenticate(username, password);
             if (account != null) {
            	 String sessionId = UUID.randomUUID().toString();
            	 int id = account.getUser().getId();

            	 Map<String, Object> response = new HashMap<>();
            	 response.put("sessionId", sessionId);
            	 response.put("userId", id);
            	 return ResponseEntity.ok(response);
             } else {
            	 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Login Credentials");
             }
    	 } catch (Exception e) {
             return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
         }
     }
}
