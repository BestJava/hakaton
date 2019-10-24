package ru.quantum.dto;

import java.util.ArrayList;
import java.util.List;

public class LoginResponse {
    String token;
    List<String> cars = new ArrayList<>();
    int level;
}