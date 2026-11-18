package com.borrowbox.app.data.local;

import com.borrowbox.app.model.ChatMessage;
import com.borrowbox.app.model.Dispute;
import com.borrowbox.app.model.PriceUnit;
import com.borrowbox.app.model.Product;
import com.borrowbox.app.model.Rental;
import com.borrowbox.app.model.RentalStatus;
import com.borrowbox.app.model.Student;
import com.borrowbox.app.model.User;
import com.borrowbox.app.util.SecurityUtils;
import java.util.ArrayList;
import java.util.List;

public final class SeedData {

    private SeedData() {
    }

    public static List<Student> getInitialStudents() {
        List<Student> list = new ArrayList<>();
        String salt = SecurityUtils.generateSalt();
        String studentPassHash = SecurityUtils.hashPassword("123456", salt);
        String adminPassHash = SecurityUtils.hashPassword("admin123", salt);
        long now = System.currentTimeMillis();

        // 1. Tanjeem (Default active borrower persona - Module 1 lead)
        list.add(new Student(1, "Muhammad Tanjeem", "tanjeem@diu.edu.bd",
                studentPassHash, salt, now - 30L * 86400000L,
                "Daffodil International University", "CSE",
                "252-15-817", 92, 4.9, 18, 14, true,
                false, "Active", 0, 0,
                User.ROLE_STUDENT, "01711-817001", true));

        // 2. Sabbir (Owner persona - Module 2 lead)
        list.add(new Student(2, "Md. Sabbir Hossain Shamim", "sabbir@diu.edu.bd",
                studentPassHash, salt, now - 28L * 86400000L,
                "Daffodil International University", "CSE",
                "252-15-825", 85, 4.7, 12, 10, true,
                false, "Active", 0, 0,
                User.ROLE_STUDENT, "01711-825002", true));

        // 3. Himel (Module 3 lead)
        list.add(new Student(3, "Chandan Acharjee Himel", "himel@diu.edu.bd",
                studentPassHash, salt, now - 25L * 86400000L,
                "Daffodil International University", "CSE",
                "252-15-891", 96, 5.0, 24, 20, true,
                false, "Active", 0, 0,
                User.ROLE_STUDENT, "01711-891003", true));

        // 4. Tasnim (Module 4 lead)
        list.add(new Student(4, "Mst Tasnim Binty Ekram", "tasnim@diu.edu.bd",
                studentPassHash, salt, now - 20L * 86400000L,
                "Daffodil International University", "CSE",
                "252-15-074", 88, 4.8, 15, 12, true,
                false, "Active", 0, 0,
                User.ROLE_STUDENT, "01711-074004", true));

        // 5. Rafi (Module 5 lead)
        list.add(new Student(5, "Md. Rafiul Islam Rafi", "rafi@diu.edu.bd",
                studentPassHash, salt, now - 15L * 86400000L,
                "Daffodil International University", "CSE",
                "252-15-105", 78, 4.4, 9, 7, true,
                false, "Active", 1, 0,
                User.ROLE_STUDENT, "01711-105005", true));

        // 6. Admin Account (DIU CSE Proctorial / Lab Admin)
        list.add(new Student(99, "DIU Campus Administrator", "admin@diu.edu.bd",
                adminPassHash, salt, now - 60L * 86400000L,
                "Daffodil International University", "CSE Administration",
                "ADMIN-01", 100, 5.0, 0, 0, true,
                false, "Active", 0, 0,
                User.ROLE_ADMIN, "01711-999999", true));

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

    public static List<Rental> getInitialRentals() {
        List<Rental> list = new ArrayList<>();
        long now = System.currentTimeMillis();

        // 1. Active Rental: Laptop Charger borrowed by Tanjeem (id 1) from Sabbir (id 2)
        Rental r1 = new Rental(
                1, 1, 1, 2, 2, PriceUnit.HOUR, 40, 200,
                RentalStatus.ACTIVE, "BB-892F", now - 30 * 60 * 1000L);
        r1.setStartAt(now - 30 * 60 * 1000L);
        r1.setEndAt(now + 90 * 60 * 1000L); // 1.5h remaining
        list.add(r1);

        // 2. Incoming Request: Scientific Calculator requested by Rafi (id 5) from Tanjeem (id 1)
        Rental r2 = new Rental(
                2, 2, 5, 1, 3, PriceUnit.HOUR, 45, 150,
                RentalStatus.REQUESTED, "BB-103A", now - 10 * 60 * 1000L);
        list.add(r2);

        // 3. Completed Rental in History
        Rental r3 = new Rental(
                3, 4, 3, 1, 1, PriceUnit.DAY, 100, 500,
                RentalStatus.RETURNED, "BB-442C", now - 86400000L);
        r3.setStartAt(now - 86400000L);
        r3.setEndAt(now - 86400000L + 3600000L * 4);
        list.add(r3);

        return list;
    }

    public static List<ChatMessage> getInitialChatMessages() {
        List<ChatMessage> list = new ArrayList<>();
        long now = System.currentTimeMillis();

        // 1. Thread: Tanjeem (id 1) & Sabbir (id 2) on Product 1 (Laptop Charger)
        list.add(new ChatMessage(
                1, 1, 2, 1,
                "আসসালামু আলাইকুম তানজীম ভাই! ল্যাপটপ চার্জারটি প্রয়োজন হলে জানাতে পারেন।",
                now - (35 * 60 * 1000L), false, "SEEN", false));

        list.add(new ChatMessage(
                2, 1, 1, 2,
                "ভাইয়া, চার্জারটি কি টাইপ-সি ম্যাকবুক এয়ার সাপোর্ট করে?",
                now - (20 * 60 * 1000L), true, "SEEN", false));

        list.add(new ChatMessage(
                3, 1, 2, 1,
                "হ্যাঁ সম্পূর্ণ সাপোর্ট করে, ৬৫ ওয়াট ফাস্ট চার্জিং টেস্ট করা। সেন্ট্রাল লাইব্রেরির সামনে এসে নিতে পারবেন।",
                now - (12 * 60 * 1000L), false, "SEEN", false));

        // 2. Thread: Tanjeem (id 1) & Tasnim (id 4) on Product 4 (DSLR Camera)
        list.add(new ChatMessage(
                4, 4, 4, 1,
                "আসসালামু আলাইকুম! Canon DSLR ক্যামেরাটি আজ ক্লাব ইভেন্টের জন্য রেডি আছে।",
                now - (60 * 60 * 1000L), false, "SEEN", false));

        list.add(new ChatMessage(
                5, 4, 1, 4,
                "আপু, সাথে কি মেমরি কার্ড ও চার্জার দেওয়া হবে?",
                now - (40 * 60 * 1000L), true, "SEEN", false));

        list.add(new ChatMessage(
                6, 4, 4, 1,
                "হ্যাঁ, ৬৪ জিবি হাই-স্পিড মেমরি কার্ড, চার্জার এবং ব্যাগ একসাথে পেয়ে যাবেন।",
                now - (25 * 60 * 1000L), false, "SEEN", false));

        return list;
    }

    public static List<Dispute> getInitialDisputes() {
        List<Dispute> list = new ArrayList<>();
        long now = System.currentTimeMillis();

        // 1. Open Dispute (Under Review)
        list.add(new Dispute(
                1, 3, 1, "Muhammad Tanjeem",
                3, "Chandan Acharjee Himel",
                4, "Canon EOS DSLR Camera + 50mm Lens",
                Dispute.TYPE_DAMAGED,
                "Minor scratch on lens front element cap discovered during equipment return. Requesting 100 BDT repair deposit deduction.",
                "evidence_lens_scratch.jpg",
                500.0,
                Dispute.STATUS_UNDER_REVIEW,
                "",
                100,
                now - (24 * 60 * 60 * 1000L),
                0L));

        // 2. Resolved Dispute
        list.add(new Dispute(
                2, 1, 2, "Md. Sabbir Hossain Shamim",
                1, "Muhammad Tanjeem",
                1, "Laptop Charger (Type-C 65W)",
                Dispute.TYPE_LATE_RETURN,
                "Returned 25 minutes past scheduled slot due to DIU CSE lab exam extension. Late fee dispute submitted for review.",
                "",
                200.0,
                Dispute.STATUS_RESOLVED,
                "Late fee waived under valid academic emergency clause. Full deposit of 200 BDT released to borrower.",
                100,
                now - (48 * 60 * 60 * 1000L),
                now - (12 * 60 * 60 * 1000L)));

        return list;
    }
}
