package com.borrowbox.app;

import com.borrowbox.app.model.PriceUnit;
import com.borrowbox.app.model.Product;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for Product Catalog & Media Management module (Member 02 Assignment).
 */
public class ProductCatalogTest {

    @Test
    public void testProductCreationAndFields() {
        List<String> imageUrls = Arrays.asList("https://storage.firebase.com/img1.jpg", "https://storage.firebase.com/img2.jpg");
        Product product = new Product("PROD_101", 5L, "Scientific Calculator", "Study",
                "Casio fx-991EX calculator in excellent condition.", 50.0, 300.0, "Excellent", true, imageUrls);

        assertEquals("PROD_101", product.getProductId());
        assertEquals(5L, product.getOwnerId());
        assertEquals("Scientific Calculator", product.getName());
        assertEquals("Scientific Calculator", product.getProductName());
        assertEquals("Study", product.getCategory());
        assertEquals(50.0, product.getDailyRentalPrice(), 0.001);
        assertEquals(300.0, product.getSecurityDeposit(), 0.001);
        assertEquals("Excellent", product.getCondition());
        assertTrue(product.isAvailable());
        assertEquals(2, product.getImageUrls().size());
        assertEquals("https://storage.firebase.com/img1.jpg", product.getImagePath());
    }

    @Test
    public void testProductPriceUnitAndCalculations() {
        Product product = new Product();
        product.setPrice(30);
        product.setPriceUnit(PriceUnit.HOUR);
        product.setDailyRentalPrice(150.0);
        product.setSecurityDeposit(600.0);

        assertEquals(30, product.getPrice());
        assertEquals(PriceUnit.HOUR, product.getPriceUnit());
        assertEquals(150.0, product.getDailyRentalPrice(), 0.001);
        assertEquals(600.0, product.getSecurityDeposit(), 0.001);
    }

    @Test
    public void testImageUrlsEmptyFallback() {
        Product product = new Product("PROD_102", 1L, "USB Mouse", "Electronics",
                "Logitech wireless mouse", 20.0, 100.0, "Good", true, Collections.emptyList());

        assertNotNull(product.getImageUrls());
        assertTrue(product.getImageUrls().isEmpty());
        assertEquals("", product.getImagePath());
    }
}
