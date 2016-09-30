package com.dsinpractice.db.samples.dao;

import com.dsinpractice.db.samples.model.ProductPart;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class ProductPartDao {

    @PersistenceContext
    private EntityManager em;

    public List<ProductPart> findAll() {
        return em.createQuery("SELECT p FROM ProductPart p").getResultList();
    }

}
