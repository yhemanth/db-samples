package com.dsinpractice.db.samples.main;

import com.dsinpractice.db.samples.model.Product;
import com.dsinpractice.db.samples.model.ProductPart;
import com.dsinpractice.db.samples.service.ProductPartService;
import com.dsinpractice.db.samples.service.ProductService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestAppEntityDeletionFromCollection {

    private final ProductService productService;
    private final ProductPartService productPartService;
    private final ClassPathXmlApplicationContext ctx;

    public TestAppEntityDeletionFromCollection() {
        ctx = new ClassPathXmlApplicationContext("classpath:/spring.xml");
        productService = ctx.getBean(ProductService.class);
        productPartService = ctx.getBean(ProductPartService.class);
        addProducts();
    }

    private void addProducts() {
        Product bulb = buildBulb();
        productService.add(bulb);

        Product mustard = getMustard();
        productService.add(mustard);
    }

    private void runTests() {
        System.out.println("Running tests");
        System.out.println("Verify addition of entities is right");
        testProductAdded();

        // replicates problem that part does not get deleted - uncomment below line
        testSuccessfulDeleteProductPartDirect();

//        testSuccessfulDeleteProductPartFromCollectionByName();

//        testSuccessfulDeleteProductPartFromCollection();
    }

    private void testSuccessfulDeleteProductPartFromCollectionByName() {
        Product product = productService.lookupProduct(2);
        productService.deletePartByName(product, "Leaf");
        verifyDeleteAction();
    }

    private void testProductAdded() {
        Product product = productService.lookupProduct(2);
        System.out.println("Lookup result - product: " + product);

        ProductPart productPart = productPartService.lookupByName("Leaf");
        System.out.println("Lookup result - product part: " + productPart);
    }

    public static void main(String[] args) {
        TestAppEntityDeletionFromCollection app = new TestAppEntityDeletionFromCollection();
        app.runTests();
        app.cleanup();
	}

    private void cleanup() {
        ctx.close();
    }

    private void verifyDeleteAction() {
        listProductParts();
        Product product1 = productService.lookupProduct(2);
        System.out.println(product1);
    }

    private void listProductParts() {
        List<ProductPart> productParts = productPartService.listAll();

        for (ProductPart p : productParts) {
            System.out.println(p);
        }
    }

    private void testSuccessfulDeleteProductPartFromCollection() {
        Product product = productService.lookupProduct(2);
        ProductPart productPart = productPartService.lookupByName("Leaf");
        productService.deletePartFromProduct(product, productPart);
        verifyDeleteAction();
    }

    private void testSuccessfulDeleteProductPartDirect() {
        Product product = productService.lookupProduct(2);
        for (ProductPart part : product.getParts()) {
            if (part.getName().equals("Leaf")) {
                //uncommenting line below (instead of deletePart) will fail deletion - as in, deletion will be a no-op
                // See http://stackoverflow.com/a/16901857
                //productService.deletePart(part);
                productPartService.deletePart(part.getId());
            }
        }
        verifyDeleteAction();
    }

    private Product getMustard() {
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

    private Product buildBulb() {
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
