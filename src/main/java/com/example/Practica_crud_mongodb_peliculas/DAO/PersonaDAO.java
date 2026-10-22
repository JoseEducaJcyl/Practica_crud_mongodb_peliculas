package com.example.Practica_crud_mongodb_peliculas.DAO;

import com.example.Practica_crud_mongodb_peliculas.Model.Personaje;
import javafx.collections.ObservableList;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class PersonaDAO implements IPersonaDAO {
    @Override
    public boolean insertar(Personaje personaje) {
        return false;
    }

    @Override
    public boolean actualizar(Personaje personaje) {
        return false;
    }

    @Override
    public boolean eliminar(int id) {
        return false;
    }

    @Override
    public boolean buscarPorId(int id) {
        return false;
    }

    @Override
    public ObservableList<Personaje> listarTodos() {
        return null;
    }

}
