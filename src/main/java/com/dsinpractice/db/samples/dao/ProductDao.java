package com.dsinpractice.db.samples.dao;

import com.dsinpractice.db.samples.model.Product;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.dsinpractice.db.samples.model.ProductPart;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ProductDao {

    @PersistenceContext
    private EntityManager em;

    public void persist(Product product) {
        em.persist(product);
    }

    public List<Product> findAll() {
        return em.createQuery("SELECT p FROM Product p").getResultList();
    }

    public Product findOne(Integer id) {
//        String namedQuery = "findOneProductById"; //uncomment this to get a lazy initialization exception.
        String namedQuery = "findOneProductByIdWithParts";
        return (Product) em.createNamedQuery(namedQuery).
                setParameter("id", id).
                getResultList().
                get(0);
    }

    public void deletePart(ProductPart part) {
        em.remove(em.contains(part) ? part : em.merge(part));
    }

    @Transactional
    public void merge(Product product) {
        em.merge(product);
    }
}
