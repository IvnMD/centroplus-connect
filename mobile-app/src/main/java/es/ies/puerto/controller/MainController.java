package es.ies.puerto.controller;

import es.ies.puerto.model.*;
import es.ies.puerto.service.ApiService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class MainController {

    private final ApiService api = new ApiService();

    // ── Tabs ──────────────────────────────────────────────────
    @FXML private TabPane tabPane;

    // ── USUARIOS ──────────────────────────────────────────────
    @FXML private ListView<Usuario> listaUsuarios;
    @FXML private TextField txtNombre, txtDni, txtEmail, txtTelefono;
    @FXML private ComboBox<String> cmbTipoUsuario;
    @FXML private TextField txtBuscarUsuario;

    // ── ACTIVIDADES ───────────────────────────────────────────
    @FXML private ListView<Actividad> listaActividades;
    @FXML private TextField txtNombreAct, txtDuracion, txtPrecio, txtPlazas;
    @FXML private ComboBox<String> cmbTipoActividad;

    // ── INSCRIPCIONES ─────────────────────────────────────────
    @FXML private ListView<Inscripcion> listaInscripciones;
    @FXML private TextField txtIdUsuarioInsc, txtIdActividadInsc;

    // ── INCIDENCIAS ───────────────────────────────────────────
    @FXML private ListView<Incidencia> listaIncidencias;
    @FXML private TextField txtAsunto, txtIdUsuarioInc;
    @FXML private TextArea txtDescripcion;

    @FXML
    public void initialize() {
        cmbTipoUsuario.setItems(FXCollections.observableArrayList("ALUMNO", "SOCIO", "AMBOS", "ADMIN"));
        cmbTipoActividad.setItems(FXCollections.observableArrayList("ACADEMICA", "DEPORTIVA"));
        cargarUsuarios();
        cargarActividades();
        cargarInscripciones();
        cargarIncidencias();
    }

    // ── USUARIOS ──────────────────────────────────────────────
    @FXML
    private void cargarUsuarios() {
        try {
            listaUsuarios.setItems(FXCollections.observableArrayList(api.getUsuarios()));
        } catch (Exception e) {
            mostrarError("Error cargando usuarios: " + e.getMessage());
        }
    }

    @FXML
    private void crearUsuario() {
        try {
            Usuario u = new Usuario();
            u.setNombre(txtNombre.getText());
            u.setDni(txtDni.getText());
            u.setEmail(txtEmail.getText());
            u.setTelefono(txtTelefono.getText());
            u.setTipoUsuario(cmbTipoUsuario.getValue());
            api.crearUsuario(u);
            cargarUsuarios();
            mostrarInfo("Usuario creado correctamente.");
        } catch (Exception e) {
            mostrarError("Error creando usuario: " + e.getMessage());
        }
    }

    @FXML
    private void eliminarUsuario() {
        Usuario sel = listaUsuarios.getSelectionModel().getSelectedItem();
        if (sel == null) { mostrarError("Selecciona un usuario."); return; }
        try {
            api.eliminarUsuario(sel.getId());
            cargarUsuarios();
            mostrarInfo("Usuario eliminado.");
        } catch (Exception e) {
            mostrarError("Error eliminando usuario: " + e.getMessage());
        }
    }

    @FXML
    private void buscarUsuario() {
        try {
            Long id = Long.parseLong(txtBuscarUsuario.getText());
            Usuario u = api.getUsuario(id);
            listaUsuarios.setItems(FXCollections.observableArrayList(u));
        } catch (Exception e) {
            mostrarError("Usuario no encontrado.");
        }
    }

    // ── ACTIVIDADES ───────────────────────────────────────────
    @FXML
    private void cargarActividades() {
        try {
            listaActividades.setItems(FXCollections.observableArrayList(api.getActividades()));
        } catch (Exception e) {
            mostrarError("Error cargando actividades: " + e.getMessage());
        }
    }

    @FXML
    private void crearActividad() {
        try {
            Actividad a = new Actividad();
            a.setNombre(txtNombreAct.getText());
            a.setTipoActividad(cmbTipoActividad.getValue());
            a.setDuracionMinutos(Integer.parseInt(txtDuracion.getText()));
            a.setPrecio(Double.parseDouble(txtPrecio.getText()));
            a.setPlazasMaximas(Integer.parseInt(txtPlazas.getText()));
            a.setPlazasOcupadas(0);
            api.crearActividad(a);
            cargarActividades();
            mostrarInfo("Actividad creada correctamente.");
        } catch (Exception e) {
            mostrarError("Error creando actividad: " + e.getMessage());
        }
    }

    @FXML
    private void eliminarActividad() {
        Actividad sel = listaActividades.getSelectionModel().getSelectedItem();
        if (sel == null) { mostrarError("Selecciona una actividad."); return; }
        try {
            api.eliminarActividad(sel.getId());
            cargarActividades();
            mostrarInfo("Actividad eliminada.");
        } catch (Exception e) {
            mostrarError("Error eliminando actividad: " + e.getMessage());
        }
    }

    // ── INSCRIPCIONES ─────────────────────────────────────────
    @FXML
    private void cargarInscripciones() {
        try {
            listaInscripciones.setItems(FXCollections.observableArrayList(api.getInscripciones()));
        } catch (Exception e) {
            mostrarError("Error cargando inscripciones: " + e.getMessage());
        }
    }

    @FXML
    private void crearInscripcion() {
        try {
            Inscripcion i = new Inscripcion();
            i.setIdUsuario(Long.parseLong(txtIdUsuarioInsc.getText()));
            i.setIdActividad(Long.parseLong(txtIdActividadInsc.getText()));
            i.setFecha(LocalDate.now().toString());
            i.setEstado("ACTIVA");
            api.crearInscripcion(i);
            cargarInscripciones();
            mostrarInfo("Inscripción creada correctamente.");
        } catch (Exception e) {
            mostrarError("Error creando inscripción: " + e.getMessage());
        }
    }

    @FXML
    private void eliminarInscripcion() {
        Inscripcion sel = listaInscripciones.getSelectionModel().getSelectedItem();
        if (sel == null) { mostrarError("Selecciona una inscripción."); return; }
        try {
            api.eliminarInscripcion(sel.getId());
            cargarInscripciones();
            mostrarInfo("Inscripción eliminada.");
        } catch (Exception e) {
            mostrarError("Error eliminando inscripción: " + e.getMessage());
        }
    }

    // ── INCIDENCIAS ───────────────────────────────────────────
    @FXML
    private void cargarIncidencias() {
        try {
            listaIncidencias.setItems(FXCollections.observableArrayList(api.getIncidencias()));
        } catch (Exception e) {
            mostrarError("Error cargando incidencias: " + e.getMessage());
        }
    }

    @FXML
    private void crearIncidencia() {
        try {
            Incidencia i = new Incidencia();
            i.setIdUsuario(Long.parseLong(txtIdUsuarioInc.getText()));
            i.setAsunto(txtAsunto.getText());
            i.setDescripcion(txtDescripcion.getText());
            i.setFecha(LocalDate.now().toString());
            i.setEstado("ABIERTA");
            api.crearIncidencia(i);
            cargarIncidencias();
            mostrarInfo("Incidencia creada correctamente.");
        } catch (Exception e) {
            mostrarError("Error creando incidencia: " + e.getMessage());
        }
    }

    @FXML
    private void eliminarIncidencia() {
        Incidencia sel = listaIncidencias.getSelectionModel().getSelectedItem();
        if (sel == null) { mostrarError("Selecciona una incidencia."); return; }
        try {
            api.eliminarIncidencia(sel.getId());
            cargarIncidencias();
            mostrarInfo("Incidencia eliminada.");
        } catch (Exception e) {
            mostrarError("Error eliminando incidencia: " + e.getMessage());
        }
    }

    // ── Helpers ───────────────────────────────────────────────
    private void mostrarError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        alert.showAndWait();
    }

    private void mostrarInfo(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        alert.showAndWait();
    }
}
