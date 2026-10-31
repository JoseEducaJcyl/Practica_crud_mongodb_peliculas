package com.example.Practica_crud_mongodb_peliculas.Controller;

import com.example.Practica_crud_mongodb_peliculas.DAO.IPersonajeDAO;
import com.example.Practica_crud_mongodb_peliculas.DAO.PersonajeDAO;
import com.example.Practica_crud_mongodb_peliculas.Model.Personaje;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.Optional;


// Clase Controller que funciona como controlador de la ventana JavaFX
public class PersonajeController {

    @FXML private TableView<Personaje> tablaPersonajes;
    @FXML private TableColumn<Personaje, Integer> colId;
    @FXML private TableColumn<Personaje, String> colNombre;
    @FXML private TableColumn<Personaje, String> colCreadorNombre;
    @FXML private TableColumn<Personaje, String> colCreadorApellido;
    @FXML private TableColumn<Personaje, String> colMascota;

    @FXML private TextField txtId;
    @FXML private TextField txtNombre;
    @FXML private TextField txtCreadorNombre;
    @FXML private TextField txtCreadorApellido;
    @FXML private TextField txtMascota;

    @FXML private Button btnInsertar;
    @FXML private Button btnActualizar;
    @FXML private Button btnEliminar;
    @FXML private Button btnLimpiar;

    private IPersonajeDAO dao = new PersonajeDAO();
    private ObservableList<Personaje> listaPersonajes;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCreadorNombre.setCellValueFactory(new PropertyValueFactory<>("creadorNombre"));
        colCreadorApellido.setCellValueFactory(new PropertyValueFactory<>("creadorApellido"));
        colMascota.setCellValueFactory(new PropertyValueFactory<>("mascota"));

        cargarTabla();

        // Listener para cuando se selecciona una fila
        tablaPersonajes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                llenarFormulario(newSelection);
                txtId.setEditable(false); // Requisito: no editar ID si hay fila seleccionada
            }
        });
    }

    private void cargarTabla() {
        try {
            List<Personaje> personajes = dao.listarTodos();
            listaPersonajes = FXCollections.observableArrayList(personajes);
            tablaPersonajes.setItems(listaPersonajes);
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo conectar a la base de datos: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void llenarFormulario(Personaje p) {
        txtId.setText(String.valueOf(p.getId()));
        txtNombre.setText(p.getNombre());
        txtCreadorNombre.setText(p.getCreadorNombre());
        txtCreadorApellido.setText(p.getCreadorApellido());
        txtMascota.setText(p.getMascota());
    }

    @FXML
    void onInsertar(ActionEvent event) {
        try {
            if (!validarCampos()) return;
            Personaje p = new Personaje(
                    Integer.parseInt(txtId.getText()),
                    txtNombre.getText().trim(),
                    txtCreadorNombre.getText().trim(),
                    txtCreadorApellido.getText().trim(),
                    txtMascota.getText().trim());
            System.out.println(p.toString());
            dao.insertar(p);
            mostrarAlerta("Éxito", "Personaje insertado.", Alert.AlertType.INFORMATION);
            cargarTabla();
            limpiarFormulario();


        } catch (Exception e) {
            mostrarAlerta("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void onActualizar(ActionEvent event) {
        try {
            Personaje seleccionado = tablaPersonajes.getSelectionModel().getSelectedItem();
            if (seleccionado == null) {
                mostrarAlerta("Aviso", "Debes seleccionar un personaje de la tabla.", Alert.AlertType.WARNING);
                return;
            }
            if (!validarCampos()) return;

            seleccionado.setNombre(txtNombre.getText().trim());
            seleccionado.setCreadorNombre(txtCreadorNombre.getText().trim());
            seleccionado.setCreadorApellido(txtCreadorApellido.getText().trim());
            seleccionado.setMascota(txtMascota.getText().trim());

            dao.actualizar(seleccionado);
            mostrarAlerta("Éxito", "Personaje actualizado correctamente.", Alert.AlertType.INFORMATION);
            cargarTabla();
            limpiarFormulario();

        } catch (Exception e) {
            mostrarAlerta("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void onEliminar(ActionEvent event) {
        Personaje seleccionado = tablaPersonajes.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Aviso", "Debes seleccionar un personaje de la tabla.", Alert.AlertType.WARNING);
            return;
        }

        // Diálogo de confirmación
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Seguro que quieres eliminar a " + seleccionado.getNombre() + "?");
        Optional<ButtonType> result = confirmacion.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                dao.eliminar(seleccionado.getId());
                mostrarAlerta("Éxito", "Personaje eliminado.", Alert.AlertType.INFORMATION);
                cargarTabla();
                limpiarFormulario();
            } catch (Exception e) {
                mostrarAlerta("Error", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    void onLimpiar(ActionEvent event) {
        limpiarFormulario();
        tablaPersonajes.getSelectionModel().clearSelection();
    }

    private void limpiarFormulario() {
        txtId.clear();
        txtNombre.clear();
        txtCreadorNombre.clear();
        txtCreadorApellido.clear();
        txtMascota.clear();
        txtId.setEditable(true); // Requisito: volver a permitir editar el ID (aunque se autogenere)
    }

    private boolean validarCampos() {
        if (txtNombre.getText().trim().isEmpty() ||
                txtCreadorNombre.getText().trim().isEmpty() ||
                txtCreadorApellido.getText().trim().isEmpty()) {
            mostrarAlerta("Validación", "Nombre, Creador (nombre) y Creador (apellido) son obligatorios.", Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}