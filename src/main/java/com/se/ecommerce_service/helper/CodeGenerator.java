package com.se.ecommerce_service.helper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class CodeGenerator {
    public static String generateProductCode(LocalDate date) {
        String seq = randomAlphaNumeric(6);
        return String.format("PRD-%s-%s", formatDate(date), seq);
    }

    public static String generateOrderCode(LocalDate date) {
        return String.format("ORD-%s-%s", formatDate(date), randomAlphaNumeric(6));
    }

    public static String generateStockInCode(LocalDate date) {
        String seq = randomAlphaNumeric(6);
        return String.format("SIN-%s-%s", formatDate(date), seq);
    }

    public static String generateStockOutCode(LocalDate date) {
        String seq = randomAlphaNumeric(6);
        return String.format("SOUT-%s-%s", formatDate(date), seq);
    }

    public static String generateWarehouseCode(String location) {
        String seq = randomAlphaNumeric(6);
        return String.format("WH-%s-%s", location.toUpperCase(), seq);
    }

    // public static String generateSKU(String category, String color, String size) {
    //     String seq = randomAlphaNumeric(6);
    //     return String.format("SKU-%s-%s-%s-%s",
    //             category.toUpperCase(), color.toUpperCase(), size.toUpperCase(), seq);
    // }

    public static String generateReasonCode(String reason) {
        return "RR-" + reason.toUpperCase().replace(" ", "-");
    }

    private static String formatDate(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    private static String randomAlphaNumeric(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(rand.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
