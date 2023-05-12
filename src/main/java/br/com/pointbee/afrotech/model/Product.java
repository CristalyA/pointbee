package br.com.pointbee.afrotech.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "tb_products")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private  Long id;

    @NotBlank
    private String name_product;

    @NotBlank
    private String quantity;

    @NotBlank
    private String quality;

    @NotBlank
    private String status;


    @ManyToOne
    @JsonIgnoreProperties("products")
    private Category categories;

    @ManyToOne
    @JsonIgnoreProperties("products")
    private User user;
}
