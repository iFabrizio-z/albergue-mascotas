/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.albergue.persistencia;

import com.albergue.sistema.SistemaAlbergue;
import com.albergue.modelo.*;

import com.google.gson.*;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author Fabricioo
 */
public class PersistenciaDatos {

    private static final Path ARCHIVO = Path.of("albergue.json");

    // Adaptador para LocalDate
    private static class LocalDateAdapter
            implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {

        @Override
        public JsonElement serialize(LocalDate fecha,
                java.lang.reflect.Type tipo,
                JsonSerializationContext contexto) {
            return fecha == null ? JsonNull.INSTANCE
                    : new JsonPrimitive(fecha.toString());
        }

        @Override
        public LocalDate deserialize(JsonElement json,
                java.lang.reflect.Type tipo,
                JsonDeserializationContext contexto)
                throws JsonParseException {
            return json == null || json.isJsonNull()
                    ? null : LocalDate.parse(json.getAsString());
        }
    }

    // Adaptador para Mascota
    private static class MascotaAdapter
            implements JsonSerializer<Mascota>, JsonDeserializer<Mascota> {

        @Override
        public JsonElement serialize(Mascota mascota,java.lang.reflect.Type tipo,
                JsonSerializationContext contexto) {

            JsonObject objeto = contexto.serialize(
                    mascota, mascota.getClass()).getAsJsonObject();

            objeto.addProperty("tipo", "Perro");
            return objeto;
        }

        @Override
        public Mascota deserialize(JsonElement json,
                java.lang.reflect.Type tipo,
                JsonDeserializationContext contexto)
                throws JsonParseException {

            JsonObject objeto = json.getAsJsonObject();
            String tipoMascota = objeto.get("tipo").getAsString();
            objeto.remove("tipo");

            if ("Perro".equals(tipoMascota)) {
                return contexto.deserialize(objeto, Perro.class);
            }
            throw new JsonParseException(
                    "Tipo de mascota desconocido: " + tipoMascota);
        }
    }

    // Adaptador para Donacion
    private static class DonacionAdapter
            implements JsonSerializer<Donacion>, JsonDeserializer<Donacion> {

        @Override
        public JsonElement serialize(Donacion donacion,
                java.lang.reflect.Type tipo,
                JsonSerializationContext contexto) {

            JsonObject objeto = contexto.serialize(
                    donacion, donacion.getClass()).getAsJsonObject();

            if (donacion instanceof Efectivo) {
                objeto.addProperty("tipo", "Efectivo");
            } else if (donacion instanceof Comida) {
                objeto.addProperty("tipo", "Comida");
            }

            return objeto;
        }

        @Override
        public Donacion deserialize(JsonElement json,
                java.lang.reflect.Type tipo,
                JsonDeserializationContext contexto)
                throws JsonParseException {

            JsonObject objeto = json.getAsJsonObject();
            String tipoDonacion = objeto.get("tipo").getAsString();
            objeto.remove("tipo");

            if ("Efectivo".equals(tipoDonacion)) {
                return contexto.deserialize(objeto, Efectivo.class);
            }

            if ("Comida".equals(tipoDonacion)) {
                return contexto.deserialize(objeto, Comida.class);
            }

            throw new JsonParseException(
                    "Tipo de donación desconocido: " + tipoDonacion);
        }
    }

    // Configuración de Gson
    private static Gson crearGson() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(
                        LocalDate.class, new LocalDateAdapter())
                .registerTypeAdapter(
                        Mascota.class, new MascotaAdapter())
                .registerTypeAdapter(
                        Donacion.class, new DonacionAdapter())
                .create();
    }

    // Guardar datos
    public static void guardarDatos(SistemaAlbergue sistema) {

        if (!sistema.isDatosModificados()) {
            return;
        }

        Gson gson = crearGson();

        try (Writer escritor = Files.newBufferedWriter(ARCHIVO)) {

            gson.toJson(sistema, escritor);
            sistema.setDatosModificados(false);

            System.out.println("Datos guardados correctamente.");

        } catch (IOException e) {
            System.err.println("Error al guardar: " + e.getMessage());
        }
    }

    // Cargar datos
    public static SistemaAlbergue cargarDatos() {

        if (!Files.exists(ARCHIVO)) {
            System.out.println("No existe albergue.json. Se inicia vacío.");
            return new SistemaAlbergue();
        }

        try (Reader lector = Files.newBufferedReader(ARCHIVO)) {

            Gson gson = crearGson();

            SistemaAlbergue sistema
                    = gson.fromJson(lector, SistemaAlbergue.class);

            if (sistema == null) {
                return new SistemaAlbergue();
            }

            sistema.reconectarSolicitudes();

            System.out.println("Datos cargados correctamente.");
            return sistema;

        } catch (IOException | JsonParseException e) {

            System.err.println("Error al cargar JSON: " + e.getMessage());
            return new SistemaAlbergue();
        }
    }
}
