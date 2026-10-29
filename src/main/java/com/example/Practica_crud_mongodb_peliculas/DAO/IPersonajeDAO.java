package com.example.Practica_crud_mongodb_peliculas.DAO;

import com.example.Practica_crud_mongodb_peliculas.Model.Personaje;
import javafx.collections.ObservableList;

public interface IPersonajeDAO {
    void insertar(Personaje personaje) throws Exception;

    void actualizar(Personaje personaje) throws Exception;

    void eliminar(int id) throws Exception;

    Personaje buscarPorId(int id);

    ObservableList<Personaje> listarTodos();
}
