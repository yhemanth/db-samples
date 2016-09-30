package com.dsinpractice.db.samples.service;

import com.dsinpractice.db.samples.dao.ProductDao;
import com.dsinpractice.db.samples.model.Product;
import com.dsinpractice.db.samples.model.ProductPart;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
        return productDao.findAll();
    }

    @Transactional
    public Product lookupProduct(Integer id) {
        return productDao.findOne(id);
    }

    @Transactional
    public void deletePart(ProductPart part) {
        productDao.deletePart(part);
    }

    @Transactional
    public void deletePartByName(Product product, String partName) {
        Set<ProductPart> parts = product.getParts();
        for (Iterator<ProductPart> i = parts.iterator(); i.hasNext(); ) {
            ProductPart productPart = i.next();
            if (productPart.getName().equals(partName)) {
                i.remove();
                break;
            }
        }
        productDao.merge(product);
    }
}
