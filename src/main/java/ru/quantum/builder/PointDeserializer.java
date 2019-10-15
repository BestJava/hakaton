package ru.quantum.builder;

import com.google.gson.*;
import ru.quantum.domain.Point;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Класс-обработчик для парсинга JSON данных по точкам графа
 */
public class PointDeserializer implements JsonDeserializer<List<Point>> {
    @Override
    public List<Point> deserialize(JsonElement jsonElement, Type type,
                                   JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        List<Point> data = new Gson().fromJson(jsonElement, type);
        data.forEach(Point::calcWeightPoint);
        return data;
    }
}
