package ru.quantum;

import ru.quantum.domain.Inkassator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Main {
    private static final String FILE_PATH = "/data/dataMaps.json";

    public static void main(String[] args) throws IOException {
        File file = new File(Main.class.getResource(FILE_PATH).getFile());
        String dataMaps = new String(Files.readAllBytes(file.toPath()));
        Inkassator app = new Inkassator();
        app.init(5, 500, dataMaps);
        System.out.println("Graph points = " + app.getGraph().getPoints().size());
    }
}
