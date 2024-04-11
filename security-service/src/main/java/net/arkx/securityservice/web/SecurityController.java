package net.arkx.securityservice.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;

@RestController
public class SecurityController {
    @GetMapping("/dataTest")
    public Map<String, Object> dataTest(){
        return Map.of("message","Data test");
    }
}
