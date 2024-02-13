package sg.edu.nus.iss.gamerecommender.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.nus.iss.gamerecommender.service.AccountService;
import sg.edu.nus.iss.gamerecommender.service.UserService;

@RestController
@RequestMapping("/api/login")
public class LoginRestController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private UserService userService;
/*
    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody User inUser) {
        try {
            // User newUser = userService.save(inUser);
            return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        boolean isAuthenticated = userService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        if (isAuthenticated) {
            String sessionId = UUID.randomUUID().toString();
            Map<String, Object> response = new HashMap<>();
            response.put("sessionId", sessionId);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Login Credentials");
        }
    }
*/ 

}
