package es.ies.puerto.controller;

import java.time.LocalDate;

import es.ies.puerto.model.Actividad;
import es.ies.puerto.model.Incidencia;
import es.ies.puerto.model.Inscripcion;
import es.ies.puerto.model.Usuario;
import es.ies.puerto.service.ApiService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainController {

    private final ApiService api = new ApiService();

    @FXML
    private TabPane tabPane;

    // ! USUARIOS
    @FXML
    private TableView<Usuario> tablaUsuarios;
    @FXML
    private TableColumn<Usuario, Long> colIdUsuario;
    @FXML
    private TableColumn<Usuario, String> colNombreUsuario;
    @FXML
    private TableColumn<Usuario, String> colDniUsuario;
    @FXML
    private TableColumn<Usuario, String> colTipoUsuario;
    @FXML
    private TableColumn<Usuario, String> colEmailUsuario;
    @FXML
    private TableColumn<Usuario, String> colTelefonoUsuario;
    @FXML
    private TextField txtBuscarUsuario;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtDni;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtTelefono;
    @FXML
    private ComboBox<String> cmbTipoUsuario;

    // ! ACTIVIDADES
    @FXML
    private TableView<Actividad> tablaActividades;
    @FXML
    private TableColumn<Actividad, Long> colIdActividad;
    @FXML
    private TableColumn<Actividad, String> colNombreActividad;
    @FXML
    private TableColumn<Actividad, String> colTipoActividad;
    @FXML
    private TableColumn<Actividad, Double> colPrecio;
    @FXML
    private TableColumn<Actividad, Integer> colDuracion;
    @FXML
    private TableColumn<Actividad, Integer> colPlazas;
    @FXML
    private TableColumn<Actividad, Integer> colPlazasLibres;
    @FXML
    private TextField txtNombreAct;
    @FXML
    private TextField txtDuracion;
    @FXML
    private TextField txtPrecio;
    @FXML
    private TextField txtPlazas;
    @FXML
    private ComboBox<String> cmbTipoActividad;

    // ! INSCRIPCIONES
    @FXML
    private TableView<Inscripcion> tablaInscripciones;
    @FXML
    private TableColumn<Inscripcion, Long> colIdInscripcion;
    @FXML
    private TableColumn<Inscripcion, Long> colIdUsuarioInsc;
    @FXML
    private TableColumn<Inscripcion, Long> colIdActividadInsc;
    @FXML
    private TableColumn<Inscripcion, String> colFechaInscripcion;
    @FXML
    private TableColumn<Inscripcion, String> colEstadoInscripcion;
    @FXML
    private TextField txtIdUsuarioInsc;
    @FXML
    private TextField txtIdActividadInsc;

    // ! INCIDENCIAS
    @FXML
    private TableView<Incidencia> tablaIncidencias;
    @FXML
    private TableColumn<Incidencia, Long> colIdIncidencia;
    @FXML
    private TableColumn<Incidencia, Long> colIdUsuarioInc;
    @FXML
    private TableColumn<Incidencia, String> colAsunto;
    @FXML
    private TableColumn<Incidencia, String> colEstadoIncidencia;
    @FXML
    private TableColumn<Incidencia, String> colFechaIncidencia;
    @FXML
    private TextField txtIdUsuarioInc;
    @FXML
    private TextField txtAsunto;
    @FXML
    private TextArea txtDescripcion;

    @FXML
    public void initialize() {

        colIdUsuario.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombreUsuario.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDniUsuario.setCellValueFactory(new PropertyValueFactory<>("dni"));
        colTipoUsuario.setCellValueFactory(new PropertyValueFactory<>("tipoUsuario"));
        colEmailUsuario.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTelefonoUsuario.setCellValueFactory(new PropertyValueFactory<>("telefono"));

        colIdActividad.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombreActividad.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colTipoActividad.setCellValueFactory(new PropertyValueFactory<>("tipoActividad"));
        colDuracion.setCellValueFactory(new PropertyValueFactory<>("duracionMinutos"));
        colPlazas.setCellValueFactory(new PropertyValueFactory<>("plazasMaximas"));
        colPlazasLibres.setCellValueFactory(new PropertyValueFactory<>("plazasOcupadas"));

        colIdInscripcion.setCellValueFactory(new PropertyValueFactory<>("id"));
        colIdUsuarioInsc.setCellValueFactory(new PropertyValueFactory<>("idUsuario"));
        colIdActividadInsc.setCellValueFactory(new PropertyValueFactory<>("idActividad"));
        colFechaInscripcion.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colEstadoInscripcion.setCellValueFactory(new PropertyValueFactory<>("estado"));

        colIdIncidencia.setCellValueFactory(new PropertyValueFactory<>("id"));
        colIdUsuarioInc.setCellValueFactory(new PropertyValueFactory<>("idUsuario"));
        colAsunto.setCellValueFactory(new PropertyValueFactory<>("asunto"));
        colEstadoIncidencia.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colFechaIncidencia.setCellValueFactory(new PropertyValueFactory<>("fecha"));

        cmbTipoUsuario.setItems(FXCollections.observableArrayList("ALUMNO", "SOCIO", "AMBOS", "ADMIN"));
        cmbTipoActividad.setItems(FXCollections.observableArrayList("ACADEMICA", "DEPORTIVA"));

        cargarUsuarios();
        cargarActividades();
        cargarInscripciones();
        cargarIncidencias();
    }

    @FXML
    private void cargarUsuarios() {
        try {
            tablaUsuarios.setItems(FXCollections.observableArrayList(api.getUsuarios()));
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
            limpiarCamposUsuario();
            mostrarInfo("Usuario creado correctamente.");
        } catch (Exception e) {
            mostrarError("Error creando usuario: " + e.getMessage());
        }
    }

    @FXML
    private void eliminarUsuario() {
        Usuario sel = tablaUsuarios.getSelectionModel().getSelectedItem();
        if (sel == null) {
            mostrarError("Selecciona un usuario.");
            return;
        }
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
            Long id = Long.parseLong(txtBuscarUsuario.getText().trim());
            Usuario u = api.getUsuario(id);
            tablaUsuarios.setItems(FXCollections.observableArrayList(u));
        } catch (Exception e) {
            mostrarError("Usuario no encontrado.");
        }
    }

    private void limpiarCamposUsuario() {
        txtNombre.clear();
        txtDni.clear();
        txtEmail.clear();
        txtTelefono.clear();
        cmbTipoUsuario.setValue(null);
    }

    @FXML
    private void cargarActividades() {
        try {
            tablaActividades.setItems(FXCollections.observableArrayList(api.getActividades()));
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
            a.setDuracionMinutos(Integer.parseInt(txtDuracion.getText().trim()));
            a.setPrecio(Double.parseDouble(txtPrecio.getText().trim()));
            a.setPlazasMaximas(Integer.parseInt(txtPlazas.getText().trim()));
            a.setPlazasOcupadas(0);
            api.crearActividad(a);
            cargarActividades();
            limpiarCamposActividad();
            mostrarInfo("Actividad creada correctamente.");
        } catch (Exception e) {
            mostrarError("Error creando actividad: " + e.getMessage());
        }
    }

    @FXML
    private void eliminarActividad() {
        Actividad sel = tablaActividades.getSelectionModel().getSelectedItem();
        if (sel == null) {
            mostrarError("Selecciona una actividad.");
            return;
        }
        try {
            api.eliminarActividad(sel.getId());
            cargarActividades();
            mostrarInfo("Actividad eliminada.");
        } catch (Exception e) {
            mostrarError("Error eliminando actividad: " + e.getMessage());
        }
    }

    private void limpiarCamposActividad() {
        txtNombreAct.clear();
        txtDuracion.clear();
        txtPrecio.clear();
        txtPlazas.clear();
        cmbTipoActividad.setValue(null);
    }

    @FXML
    private void cargarInscripciones() {
        try {
            tablaInscripciones.setItems(FXCollections.observableArrayList(api.getInscripciones()));
        } catch (Exception e) {
            mostrarError("Error cargando inscripciones: " + e.getMessage());
        }
    }

    @FXML
    private void crearInscripcion() {
        try {
            Inscripcion i = new Inscripcion();
            i.setIdUsuario(Long.parseLong(txtIdUsuarioInsc.getText().trim()));
            i.setIdActividad(Long.parseLong(txtIdActividadInsc.getText().trim()));
            i.setFecha(LocalDate.now().toString());
            i.setEstado("ACTIVA");
            api.crearInscripcion(i);
            cargarInscripciones();
            txtIdUsuarioInsc.clear();
            txtIdActividadInsc.clear();
            mostrarInfo("Inscripción creada correctamente.");
        } catch (Exception e) {
            mostrarError("Error creando inscripción: " + e.getMessage());
        }
    }

    @FXML
    private void eliminarInscripcion() {
        Inscripcion sel = tablaInscripciones.getSelectionModel().getSelectedItem();
        if (sel == null) {
            mostrarError("Selecciona una inscripción.");
            return;
        }
        try {
            api.eliminarInscripcion(sel.getId());
            cargarInscripciones();
            mostrarInfo("Inscripción eliminada.");
        } catch (Exception e) {
            mostrarError("Error eliminando inscripción: " + e.getMessage());
        }
    }

    @FXML
    private void cargarIncidencias() {
        try {
            tablaIncidencias.setItems(FXCollections.observableArrayList(api.getIncidencias()));
        } catch (Exception e) {
            mostrarError("Error cargando incidencias: " + e.getMessage());
        }
    }

    @FXML
    private void crearIncidencia() {
        try {
            Incidencia inc = new Incidencia();
            inc.setIdUsuario(Long.parseLong(txtIdUsuarioInc.getText().trim()));
            inc.setAsunto(txtAsunto.getText());
            inc.setDescripcion(txtDescripcion.getText());
            inc.setFecha(LocalDate.now().toString());
            inc.setEstado("ABIERTA");
            api.crearIncidencia(inc);
            cargarIncidencias();
            txtIdUsuarioInc.clear();
            txtAsunto.clear();
            txtDescripcion.clear();
            mostrarInfo("Incidencia creada correctamente.");
        } catch (Exception e) {
            mostrarError("Error creando incidencia: " + e.getMessage());
        }
    }

    @FXML
    private void eliminarIncidencia() {
        Incidencia sel = tablaIncidencias.getSelectionModel().getSelectedItem();
        if (sel == null) {
            mostrarError("Selecciona una incidencia.");
            return;
        }
        try {
            api.eliminarIncidencia(sel.getId());
            cargarIncidencias();
            mostrarInfo("Incidencia eliminada.");
        } catch (Exception e) {
            mostrarError("Error eliminando incidencia: " + e.getMessage());
        }
    }

    private void mostrarError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        alert.showAndWait();
    }

    private void mostrarInfo(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        alert.showAndWait();
    }
}