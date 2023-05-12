package br.com.pointbee.afrotech.repository;


import br.com.pointbee.afrotech.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    public List<Product> findAllByStatusContainingIgnoreCase(@Param("status") String status);

}
