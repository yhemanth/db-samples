package com.dsinpractice.db.samples.service;

import com.dsinpractice.db.samples.dao.ProductDao;
import com.dsinpractice.db.samples.model.Product;
import com.dsinpractice.db.samples.model.ProductPart;

import java.util.Collection;
import java.util.List;

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
