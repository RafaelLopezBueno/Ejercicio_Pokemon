package org.example.services;

import org.example.models.Pokemon;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class InformeService {

    private static final String SEPARADOR_TITULO = "========================";
    private static final String SEPARADOR_SECCION = "------------------------";

    private Long calcularTotalPokemon(List<Pokemon> pokemones) {
        return (long) pokemones.size();
    }

    public Double calcularNivelMedio(List<Pokemon> pokemones) {
        return pokemones.stream()
                .mapToInt(Pokemon::getNivel)
                .average()
                .orElse(0.0);
    }

    public Double calcularPsMedios(List<Pokemon> pokemones) {
        return pokemones.stream()
                .mapToInt(Pokemon::getPs)
                .average()
                .orElse(0.0);
    }

    public Pokemon buscarPokemonMayorNivel(List<Pokemon> pokemones) {
        return pokemones.stream()
                .max(Comparator.comparingInt(Pokemon::getNivel))
                .orElse(null);
    }

    public Pokemon buscarPokemonMayorPs(List<Pokemon> pokemones) {
        return pokemones.stream()
                .max(Comparator.comparingInt(Pokemon::getPs))
                .orElse(null);
    }

    /**
     * Agrupa los Pokémon por tipo. Los tipos quedan ordenados alfabéticamente
     * y, dentro de cada tipo, se respeta el orden del fichero JSON.
     */
    public Map<String, List<Pokemon>> agruparPorTipo(List<Pokemon> pokemones) {
        return pokemones.stream()
                .collect(Collectors.groupingBy(
                        p -> p.getTipo().toUpperCase(),
                        TreeMap::new,
                        Collectors.toList()
                ));
    }

    public List<String> generarInforme(List<Pokemon> pokemones) {
        List<String> lineas = new ArrayList<>();

        lineas.add(SEPARADOR_TITULO);
        lineas.add(" INFORME DE POKÉMON");
        lineas.add(SEPARADOR_TITULO);
        lineas.add("");

        lineas.add("Total de Pokémon: " + calcularTotalPokemon(pokemones));
        lineas.add("");
        lineas.add(String.format(Locale.US, "Nivel medio: %.2f", calcularNivelMedio(pokemones)));
        lineas.add("");
        lineas.add(String.format(Locale.US, "PS medios: %.2f", calcularPsMedios(pokemones)));
        lineas.add("");

        Pokemon mayorNivel = buscarPokemonMayorNivel(pokemones);
        lineas.add("Pokémon con mayor nivel:");
        lineas.add(mayorNivel == null ? "-"
                : mayorNivel.getNombre() + " (Nivel " + mayorNivel.getNivel() + ")");
        lineas.add("");

        Pokemon mayorPs = buscarPokemonMayorPs(pokemones);
        lineas.add("Pokémon con más PS:");
        lineas.add(mayorPs == null ? "-"
                : mayorPs.getNombre() + " (PS " + mayorPs.getPs() + ")");
        lineas.add("");

        lineas.add(SEPARADOR_SECCION);
        lineas.add("POKÉMON POR TIPO");
        lineas.add(SEPARADOR_SECCION);

        agruparPorTipo(pokemones).forEach((tipo, lista) -> {
            lineas.add("");
            lineas.add(tipo + ": " + lista.size());
            lista.forEach(p -> lineas.add(p.getNombre()));
        });

        return lineas;
    }

}
