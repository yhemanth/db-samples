package com.dsinpractice.db.samples.service;

import com.dsinpractice.db.samples.dao.ProductPartDao;
import com.dsinpractice.db.samples.model.ProductPart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class ProductPartService {

    @Autowired
    private ProductPartDao productPartDao;

    @Transactional(readOnly = true)
    public List<ProductPart> listAll() {
        List<ProductPart> productParts = productPartDao.findAll();
        return productParts;
    }

    public ProductPart lookupByName(String partName) {
        return productPartDao.findByName(partName);
    }

    @Transactional
    public void deletePart(Integer id) {
        productPartDao.delete(id);
    }
}
