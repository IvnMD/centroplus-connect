package es.ies.puerto.controller;

import es.ies.puerto.model.Incidencia;
import es.ies.puerto.repository.IncidenciaRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/incidencias")
@CrossOrigin(origins = "*")
public class IncidenciaController {

    private final IncidenciaRepository repo;

    public IncidenciaController(IncidenciaRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Incidencia> getAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Incidencia> getById(@PathVariable Long id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Incidencia> create(@Valid @RequestBody Incidencia incidencia) {
        return ResponseEntity.status(201).body(repo.save(incidencia));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Incidencia> update(@PathVariable Long id, @Valid @RequestBody Incidencia incidencia) {
        if (!repo.existsById(id)) { return ResponseEntity.notFound().build(); }
        incidencia.setId(id);
        return ResponseEntity.ok(repo.save(incidencia));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) { return ResponseEntity.notFound().build(); }
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
