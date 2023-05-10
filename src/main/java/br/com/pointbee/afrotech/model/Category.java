package br.com.pointbee.afrotech.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "tb_category_product")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name_category;

    @NotBlank
    private Double amount_payable;

    @NotBlank
    private Integer punctuation;

    @OneToMany(mappedBy = "categorys", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("categorys")
    private List<Product> services;

    public Long getId() {
        return id;
    }

    public String getName_category() {
        return name_category;
    }

    public void setName_category(String name_category) {
        this.name_category = name_category;
    }

    public Double getAmount_payable() {
        return amount_payable;
    }

    public Integer getPunctuation() {
        return punctuation;
    }

    public List<Product> getServices() {
        return services;
    }

    public void setServices(List<Product> services) {
        this.services = services;
    }
}
