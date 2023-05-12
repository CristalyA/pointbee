package br.com.pointbee.afrotech.repository;

import java.util.List;

import br.com.pointbee.afrotech.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface CategoryRepository  extends JpaRepository<Category, Long> {


    public List<Category> findAllByNameContainingIgnoreCase(@Param("name") String name);
}
