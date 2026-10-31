package com.example.Practica_crud_mongodb_peliculas.Conexion;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.InputStream;
import java.util.Properties;


// Clase creada para crear la conexion a MongoDB y cargas los datos
public class ConnectionDB {

    private static MongoClient cliente;
    private static MongoDatabase baseDatos;
    private static Properties configuration;

    private static void cargarPropiedades() {
        if (configuration != null) {
            return; // Ya está cargado, no hacemos nada
        }
        configuration = new Properties();
        try (InputStream input = ConnectionDB.class.getClassLoader()
                .getResourceAsStream("configuration/config.properties")) {
            if (input == null) {
                throw new RuntimeException("No se encontró config.properties");
            }
            configuration.load(input);
        } catch (Exception e) {
            System.err.println("Error cargando config.properties: " + e.getMessage());
        }
    }

    public static MongoCollection<Document> getColeccion() {
        cargarPropiedades();
        if (cliente == null) {
            String uri = configuration.getProperty("mongo.uri", "mongodb://localhost:27017");
            String db  = configuration.getProperty("mongo.database", "personajes_peliculas");
            String col = configuration.getProperty("mongo.collection", "personajes");

            cliente = MongoClients.create(uri);
            baseDatos = cliente.getDatabase(db);
        }
        return baseDatos.getCollection(configuration.getProperty("mongo.collection", "personajes"));
    }

    public static void cerrar() {
        if (cliente != null) {
            cliente.close();
            cliente = null;
            baseDatos = null;
        }
    }
}