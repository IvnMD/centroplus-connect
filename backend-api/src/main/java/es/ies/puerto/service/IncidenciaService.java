package es.ies.puerto.service;

import java.util.List;

import org.springframework.stereotype.Service;

import es.ies.puerto.model.Incidencia;
import es.ies.puerto.repository.IncidenciaRepository;

@Service
public class IncidenciaService {

  private final IncidenciaRepository incidenciaRepository;

  public IncidenciaService(IncidenciaRepository incidenciaRepository) {
    this.incidenciaRepository = incidenciaRepository;
  }

  public List<Incidencia> findAll() {
    return incidenciaRepository.findAll();
  }

  public Incidencia findById(Long id) {
    if (!incidenciaRepository.existsById(id)) {
      throw new IllegalArgumentException("Incidencia no encontrada con id: " + id);
    }
    return incidenciaRepository.findById(id).get();
  }

  public Incidencia save(Incidencia incidencia) {
    if (incidencia.getAsunto() == null || incidencia.getAsunto().isEmpty()) {
      throw new IllegalArgumentException("El asunto no puede estar vacio");
    }
    if (incidencia.getIdUsuario() == null) {
      throw new IllegalArgumentException("La incidencia debe tener un usuario");
    }
    incidencia.setEstado("PENDIENTE");
    return incidenciaRepository.save(incidencia);
  }

  public Incidencia update(Long id, Incidencia incidencia) {
    if (!incidenciaRepository.existsById(id)) {
      throw new IllegalArgumentException("Incidencia no encontrada con id: " + id);
    }
    incidencia.setId(id);
    return incidenciaRepository.save(incidencia);
  }

  public void deleteById(Long id) {
    if (!incidenciaRepository.existsById(id)) {
      throw new IllegalArgumentException("Incidencia no encontrada con id: " + id);
    }
    incidenciaRepository.deleteById(id);
  }
}