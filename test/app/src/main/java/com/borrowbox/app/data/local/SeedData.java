package com.borrowbox.app.data.local;

import com.borrowbox.app.model.PriceUnit;
import com.borrowbox.app.model.Product;
import com.borrowbox.app.model.Student;
import com.borrowbox.app.util.SecurityUtils;
import java.util.ArrayList;
import java.util.List;

public final class SeedData {

    private SeedData() {
    }

    public static List<Student> getInitialStudents() {
        List<Student> list = new ArrayList<>();
        String salt = SecurityUtils.generateSalt();
        String passHash = SecurityUtils.hashPassword("123456", salt);

        // 1. Tanjeem (Default active borrower persona)
        list.add(new Student(1, "Muhammad Tanjeem", "tanjeem@diu.edu.bd",
                passHash, salt, System.currentTimeMillis(),
                "Daffodil International University", "CSE",
                "252-15-817", 92, 4.9, 18, 14, true));

        // 2. Sabbir (Owner persona)
        list.add(new Student(2, "Md. Sabbir Hossain Shamim", "sabbir@diu.edu.bd",
                passHash, salt, System.currentTimeMillis(),
                "Daffodil International University", "CSE",
                "252-15-825", 85, 4.7, 12, 10, true));

        // 3. Himel
        list.add(new Student(3, "Chandan Acharjee Himel", "himel@diu.edu.bd",
                passHash, salt, System.currentTimeMillis(),
                "Daffodil International University", "CSE",
                "252-15-891", 96, 5.0, 24, 20, true));

        // 4. Tasnim
        list.add(new Student(4, "Mst Tasnim Binty Ekram", "tasnim@diu.edu.bd",
                passHash, salt, System.currentTimeMillis(),
                "Daffodil International University", "CSE",
                "252-15-074", 88, 4.8, 15, 12, true));

        // 5. Rafi
        list.add(new Student(5, "Md. Rafiul Islam Rafi", "rafi@diu.edu.bd",
                passHash, salt, System.currentTimeMillis(),
                "Daffodil International University", "CSE",
                "252-15-105", 78, 4.4, 9, 7, true));

        return list;
    }

    public static List<Product> getInitialProducts() {
        List<Product> list = new ArrayList<>();
        // Near Daffodil Smart City (DSC), Birulia, Savar (approx 23.8759, 90.3198)
        list.add(new Product(1, 2, "Laptop Charger (Type-C 65W)", "Electronics",
                "Original 65W fast charger suitable for HP, Dell, and MacBook Air. High quality, tested.",
                20, PriceUnit.HOUR, "Excellent", true, 23.8762, 90.3201, ""));

        list.add(new Product(2, 2, "Scientific Calculator fx-991ES Plus", "Study",
                "Permitted in all engineering and lab exams. Dual powered solar and battery.",
                15, PriceUnit.HOUR, "Excellent", true, 23.8755, 90.3195, ""));

        list.add(new Product(3, 3, "Fast Charging Power Bank 20,000mAh", "Electronics",
                "Dual USB output with PD 22.5W fast charging. Fully charged and ready to use.",
                25, PriceUnit.HOUR, "Good", true, 23.8770, 90.3210, ""));

        list.add(new Product(4, 4, "Canon EOS DSLR Camera + 50mm Lens", "Camera",
                "Great for university club events, convocations, and photography assignments.",
                100, PriceUnit.DAY, "Excellent", true, 23.8750, 90.3188, ""));

        list.add(new Product(5, 5, "Braided 4K HDMI Cable (2m)", "Accessories",
                "Perfect for lab room projector presentations and dual monitor setups.",
                10, PriceUnit.HOUR, "Good", true, 23.8765, 90.3190, ""));

        list.add(new Product(6, 2, "Ergonomic Wireless Mouse", "Accessories",
                "Silent click, 2.4GHz wireless dongle included with fresh AA battery.",
                10, PriceUnit.HOUR, "Good", true, 23.8758, 90.3205, ""));

        list.add(new Product(7, 3, "Wacom Digital Drawing Tablet", "Study",
                "Ideal for digital art, UI design sketching, and circuit diagramming.",
                40, PriceUnit.HOUR, "Excellent", true, 23.8768, 90.3192, ""));

        list.add(new Product(8, 4, "CSE Lab Coat (Size L)", "Study",
                "Clean, washed white apron/lab coat ready for physics/chemistry lab examinations.",
                15, PriceUnit.DAY, "Fair", true, 23.8748, 90.3202, ""));

        list.add(new Product(9, 5, "Windproof University Umbrella", "Accessories",
                "Strong compact folding umbrella for monsoon rain between campus gates.",
                10, PriceUnit.DAY, "Good", true, 23.8772, 90.3185, ""));

        list.add(new Product(10, 2, "7-in-1 USB-C Multiport Adapter Hub", "Electronics",
                "Includes HDMI, 3x USB 3.0, SD card reader, and 100W PD passthrough.",
                20, PriceUnit.HOUR, "Excellent", true, 23.8752, 90.3215, ""));

        list.add(new Product(11, 3, "Wireless Presentation Clicker / Pointer", "Accessories",
                "Red laser pointer with slide forward/backward controls for final defense presentations.",
                15, PriceUnit.HOUR, "Excellent", true, 23.8761, 90.3199, ""));

        list.add(new Product(12, 4, "Noise-Cancelling Bluetooth Headset", "Electronics",
                "Comfortable over-ear headphones with active noise cancellation for library study.",
                25, PriceUnit.HOUR, "Good", true, 23.8756, 90.3208, ""));

        return list;
    }

    public static List<com.borrowbox.app.model.Rental> getInitialRentals() {
        List<com.borrowbox.app.model.Rental> list = new ArrayList<>();
        long now = System.currentTimeMillis();

        // 1. Active Rental: Laptop Charger borrowed by Tanjeem (id 1) from Sabbir (id 2)
        com.borrowbox.app.model.Rental r1 = new com.borrowbox.app.model.Rental(
                1, 1, 1, 2, 2, PriceUnit.HOUR, 40, 200,
                com.borrowbox.app.model.RentalStatus.ACTIVE, "BB-892F", now - 30 * 60 * 1000L);
        r1.setStartAt(now - 30 * 60 * 1000L);
        r1.setEndAt(now + 90 * 60 * 1000L); // 1.5h remaining
        list.add(r1);

        // 2. Incoming Request: Scientific Calculator requested by Rafi (id 5) from Tanjeem (id 1)
        com.borrowbox.app.model.Rental r2 = new com.borrowbox.app.model.Rental(
                2, 2, 5, 1, 3, PriceUnit.HOUR, 45, 150,
                com.borrowbox.app.model.RentalStatus.REQUESTED, "BB-103A", now - 10 * 60 * 1000L);
        list.add(r2);

        // 3. Completed Rental in History
        com.borrowbox.app.model.Rental r3 = new com.borrowbox.app.model.Rental(
                3, 4, 3, 1, 1, PriceUnit.DAY, 100, 500,
                com.borrowbox.app.model.RentalStatus.RETURNED, "BB-442C", now - 86400000L);
        r3.setStartAt(now - 86400000L);
        r3.setEndAt(now - 86400000L + 3600000L * 4);
        list.add(r3);

        return list;
    }
}
