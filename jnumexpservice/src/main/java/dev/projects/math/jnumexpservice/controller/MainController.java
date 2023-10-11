package dev.projects.math.jnumexpservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping(value = "/")
    public ResponseEntity<String> getIndex() {
        return ResponseEntity.ok("ok");
    }

}
