package ru.quantum.dto;

import java.util.ArrayList;
import java.util.List;

public class LoginResponse {
    public String token;
    public List<String> cars = new ArrayList<>();
    public int level;
    //public List<RouteDTO> routes = new ArrayList<>();
    //public List<TrafficDTO> traffic = new ArrayList<>();
    //public List<PointDTO> points = new ArrayList<>();
}