package tr.com.getir.readingisgood.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

    @GetMapping("hello")
    public String hello(){
        return "hello world";
    }
}
