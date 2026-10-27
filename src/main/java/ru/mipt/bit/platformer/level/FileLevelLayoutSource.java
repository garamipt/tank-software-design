package ru.mipt.bit.platformer.level;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import ru.mipt.bit.platformer.model.LevelLayout;

public class FileLevelLayoutSource implements LevelLayoutSource {
    private final String resourcePath;
    private final LevelLayoutParser parser = new LevelLayoutParser();

    public FileLevelLayoutSource(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    @Override
    public LevelLayout createLayout(int width, int height) {
        return parser.parse(readLines(), width, height);
    }

    private List<String> readLines() {
        InputStream input =
                FileLevelLayoutSource.class.getClassLoader().getResourceAsStream(resourcePath);
        if (input == null) {
            throw new IllegalArgumentException("Level file not found: " + resourcePath);
        }
        try (BufferedReader reader =
                new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {
            return reader.lines().toList();
        } catch (IOException e) {
            throw new UncheckedIOException("Cannot read level file: " + resourcePath, e);
        }
    }
}
