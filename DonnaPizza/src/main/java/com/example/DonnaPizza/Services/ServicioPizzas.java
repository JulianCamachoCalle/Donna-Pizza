package com.example.DonnaPizza.Services;

import com.example.DonnaPizza.Model.Pizzas;
import com.example.DonnaPizza.Repository.PizzasDAO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioPizzas {

    @Autowired
    private PizzasDAO pizzasDao;

    //Select
    public List<Pizzas> getList() {
        return pizzasDao.findAll();
    }

    //INSERT Y UPDATE
    public Pizzas save(Pizzas pizzas) {
        return pizzasDao.save(pizzas);
    }

    //SELECT
    public Pizzas get(Long id) {
        return pizzasDao.findById(id).orElse(null);
    }

    //DELETE
    public void delete(Long id) {
        pizzasDao.deleteById(id);
    }
}
