package com.dogsearch.demo.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) throws Exception {
        AuthenticationResponse response = authenticationService.register(request);
        if (response.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.ok(response.getToken());
        }
        return ResponseEntity.badRequest().body(response.getMessage());
    }

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequest request) throws Exception {
        AuthenticationResponse response = authenticationService.authenticate(request);
        if (response.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.ok(response.getToken());
        }
        return ResponseEntity.badRequest().body(response.getMessage());
    }
}
