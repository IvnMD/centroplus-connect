package es.ies.puerto.service;

import java.util.List;

import org.springframework.stereotype.Service;

import es.ies.puerto.model.Actividad;
import es.ies.puerto.repository.ActividadRepository;

@Service
public class ActividadService {

  private final ActividadRepository actividadRepository;

  public ActividadService(ActividadRepository actividadRepository) {
    this.actividadRepository = actividadRepository;
  }

  public List<Actividad> findAll() {
    return actividadRepository.findAll();
  }

  public Actividad findById(Long id) {
    if (!actividadRepository.existsById(id)) {
      throw new IllegalArgumentException("Actividad no encontrada con id: " + id);
    }
    return actividadRepository.findById(id).get();
  }

  public Actividad save(Actividad actividad) {
    if (actividad.getNombre() == null || actividad.getNombre().isEmpty()) {
      throw new IllegalArgumentException("El nombre de la actividad no puede estar vacio");
    }
    if (actividad.getPlazasMaximas() <= 0) {
      throw new IllegalArgumentException("Las plazas maximas deben ser mayor que 0");
    }
    return actividadRepository.save(actividad);
  }

  public Actividad update(Long id, Actividad actividad) {
    if (!actividadRepository.existsById(id)) {
      throw new IllegalArgumentException("Actividad no encontrada con id: " + id);
    }
    actividad.setId(id);
    return actividadRepository.save(actividad);
  }

  public void deleteById(Long id) {
    if (!actividadRepository.existsById(id)) {
      throw new IllegalArgumentException("Actividad no encontrada con id: " + id);
    }
    actividadRepository.deleteById(id);
  }

  public boolean tienePlazasDisponibles(Long id) {
    Actividad actividad = findById(id);
    return actividad.getPlazasOcupadas() < actividad.getPlazasMaximas();
  }
}