package br.com.pointbee.afrotech.controller;


import br.com.pointbee.afrotech.model.User;
import br.com.pointbee.afrotech.security.UserDetailsImpl;
import br.com.pointbee.afrotech.security.UserRequestLogin;
import br.com.pointbee.afrotech.repository.UserRepository;
import br.com.pointbee.afrotech.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {


    @Autowired
    private UserService service;

    @Autowired
    private UserRepository repository;

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAll(){
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id){
        return  repository.findById(id)
                .map(response -> ResponseEntity.ok(response))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/login")
    public ResponseEntity<UserDetailsImpl> autenticationUser (@RequestBody UserRequestLogin user){
        return service.loginUser(user)
                .map(response -> ResponseEntity.ok(response))
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @PostMapping("/register")
    public ResponseEntity<User> postUser(@RequestBody User user){
        return service.registerUser(user)
                .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response))
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @PutMapping("/update")
    public ResponseEntity<User> putUser(@RequestBody User user){
        return service.updateUser(user)
                .map(response -> ResponseEntity.status(HttpStatus.OK).body(response))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

}
