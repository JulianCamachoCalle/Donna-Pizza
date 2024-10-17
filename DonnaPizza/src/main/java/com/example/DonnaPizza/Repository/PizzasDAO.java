
package com.example.DonnaPizza.Repository;

import com.example.DonnaPizza.Model.Pizzas;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PizzasDAO extends JpaRepository<Pizzas, Long>{
    
}
