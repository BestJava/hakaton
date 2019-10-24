package ru.quantum.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LoginResponse {
    public String token;
    public List<String> cars = new ArrayList<>();
    public int level;

    @Override
    public String toString() {
        return "LoginResponse{" +
                "token='" + token + '\'' +
                ", cars=" + Objects.toString(cars) +
                ", level=" + level +
                '}';
    }
}