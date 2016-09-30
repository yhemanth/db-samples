package hu.daniel.hari.learn.spring.orm.service;

import hu.daniel.hari.learn.spring.orm.dao.ProductDao;
import hu.daniel.hari.learn.spring.orm.model.Product;

import java.util.Collection;
import java.util.List;

import hu.daniel.hari.learn.spring.orm.model.ProductPart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ProductService {

	@Autowired
	private ProductDao productDao;

	@Transactional
	public void add(Product product) {
		productDao.persist(product);
	}
	
	@Transactional
	public void addAll(Collection<Product> products) {
		for (Product product : products) {
			productDao.persist(product);
		}
	}

	@Transactional(readOnly = true)
	public List<Product> listAll() {
		List<Product> products = productDao.findAll();
		for (Product p : products) {
			p.getParts().size();
		}
		return products;
	}

    @Transactional
    public Product lookupProduct(Integer id) {
        return productDao.findOne(id);
    }

    @Transactional
    public void deletePart(ProductPart part) {
        productDao.deletePart(part);
    }
}
