package com.dsinpractice.db.samples.main;

import com.dsinpractice.db.samples.model.Product;
import com.dsinpractice.db.samples.model.ProductPart;
import com.dsinpractice.db.samples.service.ProductPartService;
import com.dsinpractice.db.samples.service.ProductService;

import java.util.HashSet;
import java.util.List;
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
        productService.add(bulb);

        Product mustard = getMustard();
        productService.add(mustard);

        // just verify things got added; also get a detached instance.
        Product product = productService.lookupProduct(2);
        System.out.println("Lookup result:" + product);

        // replicates problem that part does not get deleted - uncomment below line
        //testUnsuccessfulDeleteProductPartDirect(productService, product, ctx);

        // removing from collection and relying on orphan-removal
        testSuccessfulDeleteProductPartFromCollection(ctx, productService, product);
		ctx.close();
		
	}

    private static void verifyDeleteAction(ClassPathXmlApplicationContext ctx, ProductService productService) {
        listProductParts(ctx);
        Product product1 = productService.lookupProduct(2);
        System.out.println(product1);
    }

    private static void listProductParts(ClassPathXmlApplicationContext ctx) {
        ProductPartService productPartService = ctx.getBean(ProductPartService.class);
        List<ProductPart> productParts = productPartService.listAll();

        for (ProductPart p : productParts) {
            System.out.println(p);
        }
    }

    private static void testSuccessfulDeleteProductPartFromCollection(ClassPathXmlApplicationContext ctx, ProductService productService, Product product) {
        productService.deletePartByName(product, "Leaf");
        verifyDeleteAction(ctx, productService);
    }

    private static void testUnsuccessfulDeleteProductPartDirect(ProductService productService, Product product, ClassPathXmlApplicationContext ctx) {
        for (ProductPart part : product.getParts()) {
            if (part.getName().equals("Leaf")) {
                productService.deletePart(part);
            }
        }
        verifyDeleteAction(ctx, productService);
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
