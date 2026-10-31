package com.example.Practica_crud_mongodb_peliculas.Model;


// Clase Personaje como modelo de los objetos
public class Personaje {
    private int id;
    private String nombre;
    private String creadorNombre;
    private String creadorApellido;
    private String mascota;

    public Personaje() {
    }

    public Personaje(int id, String nombre, String creadorNombre, String creadorApellido, String mascota) {
        this.id = id;
        this.nombre = nombre;
        this.creadorNombre = creadorNombre;
        this.creadorApellido = creadorApellido;
        this.mascota = mascota;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCreadorNombre() {
        return creadorNombre;
    }

    public void setCreadorNombre(String creadorNombre) {
        this.creadorNombre = creadorNombre;
    }

    public String getCreadorApellido() {
        return creadorApellido;
    }

    public void setCreadorApellido(String creadorApellido) {
        this.creadorApellido = creadorApellido;
    }

    public String getMascota() {
        return mascota;
    }

    public void setMascota(String mascota) {
        this.mascota = mascota;
    }
}
