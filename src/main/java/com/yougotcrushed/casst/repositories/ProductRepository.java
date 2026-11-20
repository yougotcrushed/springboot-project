package com.yougotcrushed.casst.repositories;

import com.yougotcrushed.casst.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @EntityGraph(attributePaths = "category")
    List<Product> findByCategoryId(Byte categoryId);

    @EntityGraph(attributePaths = "category")
    @Query("SELECT p FROM Product p")
    List<Product> findAllWithCategory();


}
