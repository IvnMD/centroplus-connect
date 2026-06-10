package es.ies.puerto.controller;

import es.ies.puerto.model.Actividad;
import es.ies.puerto.repository.ActividadRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/actividades")
@CrossOrigin(origins = "*")
public class ActividadController {

    private final ActividadRepository repo;

    public ActividadController(ActividadRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Actividad> getAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Actividad> getById(@PathVariable Long id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Actividad> create(@Valid @RequestBody Actividad actividad) {
        return ResponseEntity.status(201).body(repo.save(actividad));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Actividad> update(@PathVariable Long id, @Valid @RequestBody Actividad actividad) {
        if (!repo.existsById(id)) { return ResponseEntity.notFound().build(); }
        actividad.setId(id);
        return ResponseEntity.ok(repo.save(actividad));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) { return ResponseEntity.notFound().build(); }
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
