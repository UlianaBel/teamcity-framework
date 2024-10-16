package com.example.teamc.api.generators;

import java.util.Random;

//Метод, чтобы сгенерировать id, который начинается с символа
public class IdGenerator {
    private static final String SYMBOLS = "!@#$%^&*()-+=<>?"; // Пример символов
    private static final String ALPHANUMERIC = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_";

    public static String generateInvalidId() {
        Random random = new Random();
        // Генерируем первый символ (символ)
        char firstChar = SYMBOLS.charAt(random.nextInt(SYMBOLS.length()));
        // Генерируем остальные символы (например, 5 случайных букв или цифр)
        StringBuilder idBuilder = new StringBuilder();
        idBuilder.append(firstChar);
        for (int i = 0; i < 5; i++) {
            char nextChar = ALPHANUMERIC.charAt(random.nextInt(ALPHANUMERIC.length()));
            idBuilder.append(nextChar);
        }
        return idBuilder.toString();
    }
    // Метод для генерации ID заданной длины
    public static String generateLongId(int length) {
        StringBuilder longIdBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            longIdBuilder.append("a"); // Добавляем символ 'a' для создания длинного ID
        }
        return longIdBuilder.toString();
    }
}
