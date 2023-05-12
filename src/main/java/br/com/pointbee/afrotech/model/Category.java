package br.com.pointbee.afrotech.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "tb_category")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private  String name;

    @NotNull
    private double payment_amount;

    @NotNull
    private int punctuation;

    @OneToMany(mappedBy = "categories", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("categories")
    private List<Product> products;
}
