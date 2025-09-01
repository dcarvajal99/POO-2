package com.grupo7.ecommerce.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public final class CsvUtils {
    private CsvUtils() {}

    public static List<String[]> readCsv(String path) {
        List<String[]> out = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(path), StandardCharsets.UTF_8)) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                // Split simple CSV (no commas in fields expected)
                String[] parts = line.split(",", -1);
                for (int i = 0; i < parts.length; i++) parts[i] = parts[i].trim();
                out.add(parts);
            }
        } catch (IOException e) {
            // Return empty if not found or any IO issue
        }
        return out;
    }

    /**
     * Escribe filas CSV en el archivo dado. Si append es true, se anexan; si es false, se sobrescribe.
     */
    public static boolean writeCsv(String path, List<String[]> rows, boolean append) {
        try (BufferedWriter bw = Files.newBufferedWriter(
                Paths.get(path),
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                append ? StandardOpenOption.APPEND : StandardOpenOption.TRUNCATE_EXISTING)) {
            for (String[] row : rows) {
                String line = String.join(",", row == null ? new String[]{} : row);
                bw.write(line);
                bw.newLine();
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
