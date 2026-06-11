package es.ies.puerto.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import es.ies.puerto.model.*;

import java.net.URI;
import java.net.http.*;
import java.util.List;

public class ApiService {

    private static final String BASE_URL = "http://localhost:8080/api";
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    //! USUARIOS
    
    public List<Usuario> getUsuarios() throws Exception {
        String json = get("/usuarios");
        return mapper.readValue(json, new TypeReference<List<Usuario>>() {});
    }

    public Usuario getUsuario(Long id) throws Exception {
        String json = get("/usuarios/" + id);
        return mapper.readValue(json, Usuario.class);
    }

    public Usuario crearUsuario(Usuario u) throws Exception {
        String json = post("/usuarios", mapper.writeValueAsString(u));
        return mapper.readValue(json, Usuario.class);
    }

    public Usuario actualizarUsuario(Long id, Usuario u) throws Exception {
        String json = put("/usuarios/" + id, mapper.writeValueAsString(u));
        return mapper.readValue(json, Usuario.class);
    }

    public void eliminarUsuario(Long id) throws Exception {
        delete("/usuarios/" + id);
    }

    //! ACTIVIDADES 

    public List<Actividad> getActividades() throws Exception {
        String json = get("/actividades");
        return mapper.readValue(json, new TypeReference<List<Actividad>>() {});
    }

    public Actividad getActividad(Long id) throws Exception {
        String json = get("/actividades/" + id);
        return mapper.readValue(json, Actividad.class);
    }

    public Actividad crearActividad(Actividad a) throws Exception {
        String json = post("/actividades", mapper.writeValueAsString(a));
        return mapper.readValue(json, Actividad.class);
    }

    //TODO implementar actualizarActividad cuando lo necesite la pantalla de edición

    public void eliminarActividad(Long id) throws Exception {
        delete("/actividades/" + id);
    }

    //! INSCRIPCIONES

    public List<Inscripcion> getInscripciones() throws Exception {
        String json = get("/inscripciones");
        return mapper.readValue(json, new TypeReference<List<Inscripcion>>() {});
    }

    public Inscripcion crearInscripcion(Inscripcion i) throws Exception {
        String json = post("/inscripciones", mapper.writeValueAsString(i));
        return mapper.readValue(json, Inscripcion.class);
    }

    public void eliminarInscripcion(Long id) throws Exception {
        delete("/inscripciones/" + id);
    }

    //! INCIDENCIAS
    public List<Incidencia> getIncidencias() throws Exception {
        String json = get("/incidencias");
        return mapper.readValue(json, new TypeReference<List<Incidencia>>() {});
    }

    public Incidencia crearIncidencia(Incidencia i) throws Exception {
        String json = post("/incidencias", mapper.writeValueAsString(i));
        return mapper.readValue(json, Incidencia.class);
    }

    public void eliminarIncidencia(Long id) throws Exception {
        delete("/incidencias/" + id);
    }

    //! HTTP helpers
    private String get(String path) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + path))
                .GET()
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    private String post(String path, String body) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + path))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    private String put(String path, String body) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + path))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(body))
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    private void delete(String path) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + path))
                .DELETE()
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
