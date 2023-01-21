package tr.com.getir.readingisgood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tr.com.getir.readingisgood.dto.CustomerLoginDTO;
import tr.com.getir.readingisgood.dto.CustomerRegistrationDTO;
import tr.com.getir.readingisgood.service.IAuthService;

@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    private IAuthService service;

    //todo validation
    @PostMapping(
            value = "register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> register(@RequestBody CustomerRegistrationDTO registrationDTO) {
        return ResponseEntity.ok(service.register(registrationDTO));
    }

    @PostMapping(
            value = "login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> login(@RequestBody CustomerLoginDTO loginDTO) {
        return ResponseEntity.ok(service.login(loginDTO));
    }

}
