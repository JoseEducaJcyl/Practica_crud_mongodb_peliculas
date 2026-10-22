package com.example.Practica_crud_mongodb_peliculas.DAO;

import com.example.Practica_crud_mongodb_peliculas.Model.Personaje;
import javafx.collections.ObservableList;

public interface IPersonaDAO {
    boolean insertar(Personaje personaje);

    boolean actualizar(Personaje personaje);

    boolean eliminar(int id);

    boolean buscarPorId(int id);

    ObservableList<Personaje> listarTodos();
}
