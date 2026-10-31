package com.example.Practica_crud_mongodb_peliculas.DAO;

import com.example.Practica_crud_mongodb_peliculas.Model.Personaje;
import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;


// Clase PersonajeDAO que implementa los metodos de la interfaz, con el funcionamiento CRUD
public class PersonajeDAO implements IPersonajeDAO {
    private com.example.Practica_crud_mongodb_peliculas.Conexion.ConnectionDB ConnectionDB;
    private final MongoCollection<Document> coleccion = ConnectionDB.getColeccion();
    private final Gson gson = new Gson();

    @Override
    public void insertar(Personaje personaje) throws Exception {
        if (buscarPorId(personaje.getId()) != null) {
            throw new Exception("El ID " + personaje.getId() + " ya existe.");
        }
        String json = gson.toJson(personaje);
        coleccion.insertOne(Document.parse(json));
    }

    @Override
    public void actualizar(Personaje personaje) throws Exception {
        String json = gson.toJson(personaje);
        Document nuevoDoc = Document.parse(json);
        long modificados = coleccion.replaceOne(Filters.eq("id", personaje.getId()), nuevoDoc).getModifiedCount();
        if (modificados == 0) throw new Exception("No se encontró el personaje.");
    }

    @Override
    public void eliminar(int id) throws Exception {
        long borrados = coleccion.deleteOne(Filters.eq("id", id)).getDeletedCount();
        if (borrados == 0) throw new Exception("No se encontró el personaje.");
    }

    @Override
    public Personaje buscarPorId(int id) {
        Document doc = coleccion.find(Filters.eq("id", id)).first();
        if (doc == null) return null;
        return gson.fromJson(doc.toJson(), Personaje.class);
    }

    @Override
    public List<Personaje> listarTodos() {
        List<Personaje> lista = new ArrayList<>();

        for (Document doc : coleccion.find().sort(Sorts.ascending("id"))) {
            lista.add(gson.fromJson(doc.toJson(), Personaje.class));
        }
        return lista;
    }

    private int calcularSiguienteId() {
        Document maxDoc = coleccion.find().sort(Sorts.descending("id")).first();
        return maxDoc != null ? maxDoc.getInteger("id") + 1 : 1;
    }
}
