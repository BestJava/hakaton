package ru.quantum.dto;

/**
 * Запрос на логин
 */
public class LoginRequest {
    String teamName;

    @Override
    public String toString() {
        return "LoginRequest{" +
                "teamName='" + teamName + '\'' +
                '}';
    }
}