package com.dsinpractice.db.samples.main;

import com.dsinpractice.db.samples.model.Product;
import com.dsinpractice.db.samples.model.ProductPart;
import com.dsinpractice.db.samples.service.ProductService;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringOrmMain {
	
	public static void main(String[] args) {
		
		//Create Spring application context
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:/spring.xml");
		
		//Get service from context. (service's dependency (ProductDAO) is autowired in ProductService)
		ProductService productService = ctx.getBean(ProductService.class);
		
		//Do some data operation
        Product bulb = buildBulb();
        Product mustard = getMustard();

        productService.add(bulb);
        productService.add(mustard);

        Product product = productService.lookupProduct(2);
        System.out.println(product);

        for (ProductPart part : product.getParts()) {
            if (part.getName().equals("Leaf")) {
                productService.deletePart(part);
            }
        }

        Product product1 = productService.lookupProduct(2);
        System.out.println(product1);

//		System.out.println("listAll: " + productService.listAll());

		//Test transaction rollback (duplicated key)
		
//		try {
//			productService.addAll(Arrays.asList(new Product(3, "Book"), new Product(4, "Soap"), new Product(1, "Computer")));
//		} catch (DataAccessException dataAccessException) {
//		}
		
		//Test element list after rollback
//		System.out.println("listAll: " + productService.listAll());
		
		ctx.close();
		
	}

    private static Product getMustard() {
        Product mustard = new Product(2, "Dijone mustard");
        ProductPart mustardPart = new ProductPart();
        mustardPart.setId(200);
        mustardPart.setName("Grain");
        mustardPart.setProduct(mustard);

        ProductPart leafPart = new ProductPart();
        leafPart.setId(201);
        leafPart.setName("Leaf");
        leafPart.setProduct(mustard);

        Set<ProductPart> mustardParts = new HashSet<>();
        mustardParts.add(mustardPart);
        mustardParts.add(leafPart);
        mustard.setParts(mustardParts);

        return mustard;
    }

    private static Product buildBulb() {
        Product bulb = new Product(1, "Bulb");

        ProductPart bulbPart = new ProductPart();
        bulbPart.setId(100);
        bulbPart.setName("Filament");
        bulbPart.setProduct(bulb);

        Set<ProductPart> bulbParts = new HashSet<>();
        bulbParts.add(bulbPart);
        bulb.setParts(bulbParts);

        return bulb;
    }
}