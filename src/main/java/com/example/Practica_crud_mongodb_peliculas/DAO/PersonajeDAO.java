package com.example.Practica_crud_mongodb_peliculas.DAO;

import com.example.Practica_crud_mongodb_peliculas.Conexion.ConnectionDB;
import com.example.Practica_crud_mongodb_peliculas.Model.Personaje;
import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.bson.Document;

public class PersonajeDAO implements IPersonajeDAO {
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
        long modificados = coleccion.replaceOne(Filters.eq("_id", personaje.getId()), nuevoDoc).getModifiedCount();
        if (modificados == 0) throw new Exception("No se encontró el personaje.");
    }

    @Override
    public void eliminar(int id) throws Exception {
        long borrados = coleccion.deleteOne(Filters.eq("_id", id)).getDeletedCount();
        if (borrados == 0) throw new Exception("No se encontró el personaje.");
    }

    @Override
    public Personaje buscarPorId(int id) {
        Document doc = coleccion.find(Filters.eq("_id", id)).first();
        if (doc == null) return null;
        // Document -> JSON -> Objeto Java
        return gson.fromJson(doc.toJson(), Personaje.class);
    }

    @Override
    public ObservableList<Personaje> listarTodos() {
        ObservableList<Personaje> lista = FXCollections.observableArrayList();
        for (Document doc : coleccion.find().sort(Sorts.ascending("_id"))) {
            lista.add(gson.fromJson(doc.toJson(), Personaje.class));
        }
        return lista;
    }

    private int calcularSiguienteId() {
        Document maxDoc = coleccion.find().sort(Sorts.descending("_id")).first();
        return maxDoc != null ? maxDoc.getInteger("_id") + 1 : 1;
    }
}
