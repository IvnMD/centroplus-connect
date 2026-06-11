package es.ies.puerto.service;

import java.util.List;

import org.springframework.stereotype.Service;

import es.ies.puerto.model.Actividad;
import es.ies.puerto.model.Inscripcion;
import es.ies.puerto.repository.ActividadRepository;
import es.ies.puerto.repository.InscripcionRepository;

@Service
public class InscripcionService {

    private final InscripcionRepository inscripcionRepository;
    private final ActividadRepository actividadRepository;

    public InscripcionService(InscripcionRepository inscripcionRepository,
            ActividadRepository actividadRepository) {
        this.inscripcionRepository = inscripcionRepository;
        this.actividadRepository = actividadRepository;
    }

    public List<Inscripcion> findAll() {
        return inscripcionRepository.findAll();
    }

    public Inscripcion findById(Long id) {
        if (!inscripcionRepository.existsById(id)) {
            throw new IllegalArgumentException("Inscripcion no encontrada con id: " + id);
        }
        return inscripcionRepository.findById(id).get();
    }

    public Inscripcion save(Inscripcion inscripcion) {
        if (inscripcion.getIdActividad() == null) {
            throw new IllegalArgumentException("La inscripcion debe tener una actividad");
        }
        if (inscripcion.getIdUsuario() == null) {
            throw new IllegalArgumentException("La inscripcion debe tener un usuario");
        }

        if (!actividadRepository.existsById(inscripcion.getIdActividad())) {
            throw new IllegalArgumentException("La actividad no existe");
        }

        Actividad actividad = actividadRepository.findById(inscripcion.getIdActividad()).get();
        if (actividad.getPlazasOcupadas() >= actividad.getPlazasMaximas()) {
            throw new IllegalArgumentException("La actividad no tiene plazas disponibles");
        }

        actividad.setPlazasOcupadas(actividad.getPlazasOcupadas() + 1);
        actividadRepository.save(actividad);

        return inscripcionRepository.save(inscripcion);
    }

    public Inscripcion update(Long id, Inscripcion inscripcion) {
        if (!inscripcionRepository.existsById(id)) {
            throw new IllegalArgumentException("Inscripcion no encontrada con id: " + id);
        }
        inscripcion.setId(id);
        return inscripcionRepository.save(inscripcion);
    }

    public void deleteById(Long id) {
        if (!inscripcionRepository.existsById(id)) {
            throw new IllegalArgumentException("Inscripcion no encontrada con id: " + id);
        }

        Inscripcion inscripcion = inscripcionRepository.findById(id).get();
        if (actividadRepository.existsById(inscripcion.getIdActividad())) {
            Actividad actividad = actividadRepository.findById(inscripcion.getIdActividad()).get();
            actividad.setPlazasOcupadas(actividad.getPlazasOcupadas() - 1);
            actividadRepository.save(actividad);
        }

        inscripcionRepository.deleteById(id);
    }
}