package es.ies.puerto.controller;

import es.ies.puerto.model.Inscripcion;
import es.ies.puerto.repository.InscripcionRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/inscripciones")
@CrossOrigin(origins = "*")
public class InscripcionController {

    private final InscripcionRepository repo;

    public InscripcionController(InscripcionRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Inscripcion> getAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inscripcion> getById(@PathVariable Long id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Inscripcion> create(@Valid @RequestBody Inscripcion inscripcion) {
        return ResponseEntity.status(201).body(repo.save(inscripcion));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Inscripcion> update(@PathVariable Long id, @Valid @RequestBody Inscripcion inscripcion) {
        if (!repo.existsById(id)) { return ResponseEntity.notFound().build(); }
        inscripcion.setId(id);
        return ResponseEntity.ok(repo.save(inscripcion));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) { return ResponseEntity.notFound().build(); }
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
