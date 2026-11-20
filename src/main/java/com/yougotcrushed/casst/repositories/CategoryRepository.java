package com.yougotcrushed.casst.repositories;

import com.yougotcrushed.casst.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Byte> {
}
