package es.ies.puerto.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.ies.puerto.model.Actividad;
import es.ies.puerto.service.ActividadService;

@RestController
@RequestMapping("/api/actividades")
public class ActividadController {

    private final ActividadService actividadService;

    public ActividadController(ActividadService actividadService) {
        this.actividadService = actividadService;
    }

    @GetMapping
    public List<Actividad> getAll() {
        return actividadService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Actividad> getById(@PathVariable Long id) {
        try {
            Actividad actividad = actividadService.findById(id);
            return ResponseEntity.ok(actividad);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/plazas")
    public ResponseEntity<String> consultarPlazas(@PathVariable Long id) {
        try {
            boolean hayPlazas = actividadService.tienePlazasDisponibles(id);
            if (hayPlazas) {
                return ResponseEntity.ok("La actividad tiene plazas disponibles");
            }
            return ResponseEntity.ok("La actividad no tiene plazas disponibles");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Actividad> create(@RequestBody Actividad actividad) {
        try {
            Actividad nueva = actividadService.save(actividad);
            return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Actividad> update(@PathVariable Long id, @RequestBody Actividad actividad) {
        try {
            Actividad actualizada = actividadService.update(id, actividad);
            return ResponseEntity.ok(actualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            actividadService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}