package br.com.pointbee.afrotech.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "tb_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String userName;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String userType;

}
