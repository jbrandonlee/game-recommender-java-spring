package sg.edu.nus.iss.gamerecommender.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.nus.iss.gamerecommender.model.Account;
import sg.edu.nus.iss.gamerecommender.model.User;
import sg.edu.nus.iss.gamerecommender.model.User.Role;
import sg.edu.nus.iss.gamerecommender.service.AccountService;
import sg.edu.nus.iss.gamerecommender.service.UserService;

@RestController
@RequestMapping("/api/users")
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
    
    @PostMapping("/genre")
    public ResponseEntity<?> storeGenres(@RequestBody String body){
    	try {
    		JsonObject genreJson=JsonParser.parseString(body).getAsJsonObject();
    		return new ResponseEntity<>(HttpStatus.CREATED);
    	}catch(Exception e) {
    		return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    	}
    }
    
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
//        boolean isAuthenticated = userService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
//        if (isAuthenticated) {
//            String sessionId = UUID.randomUUID().toString();
//            Map<String, Object> response = new HashMap<>();
//            response.put("sessionId", sessionId);
//            return ResponseEntity.ok(response);
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Login Credentials");
//        }
//    }

}
