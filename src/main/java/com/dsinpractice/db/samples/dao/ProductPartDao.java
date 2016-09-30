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

    public ProductPart findByName(String partName) {
        return (ProductPart) em.createNamedQuery("findByName").setParameter("name", partName).getResultList().get(0);
    }

    public void delete(Integer id) {
        ProductPart productPart = em.find(ProductPart.class, id);
        em.remove(em.contains(productPart) ? productPart : em.merge(productPart));
    }
}
