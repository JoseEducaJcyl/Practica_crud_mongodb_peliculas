package com.example.Practica_crud_mongodb_peliculas.Conexion;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.Properties;

public class ConnectionDB {
    private static final String URI = "mongodb://localhost:27017";
    // private static final String URI = "mongodb://admin:1234@localhost:27017/?authSource=admin";
    private static final String BASE_DATOS = "peliculas_infantiles";
    private static final String COLECCION = "personajes";

    private static MongoClient cliente;
    private static MongoDatabase baseDatos;

    /** Abre la conexión con el servidor y selecciona la base de datos. */
    public static void conectar() {
        cliente = MongoClients.create(URI);
        baseDatos = cliente.getDatabase(BASE_DATOS);
    }

    /** Devuelve la colección "personajes" (se crea sola al insertar el primer documento). */
    public static MongoCollection<Document> getColeccion() {
        if (baseDatos == null) {
            throw new IllegalStateException("Primero debes llamar a conectar()");
        }
        return baseDatos.getCollection(COLECCION);
    }

    /** Cierra la conexión. */
    public static void cerrar() {
        if (cliente != null) {
            cliente.close();
            cliente = null;
            baseDatos = null;
        }
    }
}
