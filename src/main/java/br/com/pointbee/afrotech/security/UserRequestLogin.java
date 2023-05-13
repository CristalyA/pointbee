package br.com.pointbee.afrotech.security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestLogin {

    private String email;
    private String password;
}
