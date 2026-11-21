package com.borrowbox.app;

import com.borrowbox.app.model.Product;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for My Products management logic (Member 02 - Rafi).
 */
public class MyProductsTest {

    @Test
    public void testFilterProductsByOwnerId() {
        List<Product> allProducts = new ArrayList<>();

        Product p1 = new Product();
        p1.setId(101L);
        p1.setOwnerId(1L); // Tanjeem / Member 1
        p1.setName("Laptop Charger");

        Product p2 = new Product();
        p2.setId(102L);
        p2.setOwnerId(2L); // Rafi / Member 2
        p2.setName("Scientific Calculator");

        Product p3 = new Product();
        p3.setId(103L);
        p3.setOwnerId(2L); // Rafi / Member 2
        p3.setName("DSLR Camera");

        allProducts.add(p1);
        allProducts.add(p2);
        allProducts.add(p3);

        long targetOwnerId = 2L;
        List<Product> userProducts = new ArrayList<>();
        for (Product p : allProducts) {
            if (p.getOwnerId() == targetOwnerId) {
                userProducts.add(p);
            }
        }

        assertEquals(2, userProducts.size());
        assertEquals("Scientific Calculator", userProducts.get(0).getName());
        assertEquals("DSLR Camera", userProducts.get(1).getName());
    }

    @Test
    public void testToggleProductAvailability() {
        Product product = new Product();
        product.setId(201L);
        product.setName("HDMI Cable");
        product.setAvailable(true);

        assertTrue(product.isAvailable());

        // Toggle to unavailable (rented out)
        product.setAvailable(false);
        assertFalse(product.isAvailable());

        // Toggle back to available
        product.setAvailable(true);
        assertTrue(product.isAvailable());
    }
}
