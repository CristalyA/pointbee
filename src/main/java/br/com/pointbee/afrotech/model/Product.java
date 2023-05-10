package br.com.pointbee.afrotech.model;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.ISBN;

@Entity
@Table(name = "tb_product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
