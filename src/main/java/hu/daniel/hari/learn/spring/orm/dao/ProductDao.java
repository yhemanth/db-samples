package hu.daniel.hari.learn.spring.orm.dao;

import hu.daniel.hari.learn.spring.orm.model.Product;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import hu.daniel.hari.learn.spring.orm.model.ProductPart;
import org.springframework.stereotype.Component;

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
}
