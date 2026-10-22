package org.example.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.models.Pokemon;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileService {

    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Lee el fichero JSON indicado y devuelve la lista de Pokémon que contiene.
     */
    public List<Pokemon> leerPokemonJson(String ruta) {
        try {
            List<Pokemon> pokemones = mapper.readValue(
                    Path.of(ruta).toFile(),
                    new TypeReference<List<Pokemon>>() {}
            );
            return new ArrayList<>(pokemones);
        } catch (IOException e) {
            throw new UncheckedIOException("No se pudo leer el fichero JSON: " + ruta, e);
        }
    }

    /**
     * Escribe las líneas del informe en un fichero de texto (UTF-8).
     */
    public void escribirInforme(String ruta, List<String> lineas) {
        try {
            Files.write(Path.of(ruta), lineas, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException("No se pudo escribir el informe: " + ruta, e);
        }
    }

}
