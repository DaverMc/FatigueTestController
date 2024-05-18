package dn.ftc.util;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class Ressources {

    public static Stream<Path> stream(String path, int maxDepth) throws IOException, URISyntaxException {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        URL url = classLoader.getResource(path);
        if (url == null) {
            throw new IllegalArgumentException("Ressource path not found: " + path);
        }

        URI uri = url.toURI();
        Path filePath = Paths.get(uri);

        return Files.walk(filePath, maxDepth);
    }

    public static Stream<Path> stream(String path) throws IOException, URISyntaxException {
        return stream(path, Integer.MAX_VALUE);
    }

    public static List<Path> list(String path, int maxDepth) throws IOException, URISyntaxException {
        return stream(path, maxDepth).toList();
    }

    public static String getName(Path path) {

        return path.getFileName().toString();
    }
}
