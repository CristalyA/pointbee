package br.com.pointbee.afrotech.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/main")
public class BasicController {

    @GetMapping("protect")
    public String protectedRoute() {
        return "Rota Protegida";
    }

    @GetMapping("free")
    public String unprotectedRoute() {
        return "Rota Livre";
    }
}