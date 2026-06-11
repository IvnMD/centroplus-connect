package es.ies.puerto.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import es.ies.puerto.models.Actividad;
import es.ies.puerto.models.Constantes;
import es.ies.puerto.models.Inscripcion;
import es.ies.puerto.repositories.ActividadRepositoryInterface;
import es.ies.puerto.repositories.InscripcionRepositoryInterface;
import es.ies.puerto.repositories.UsuarioRepositoryInterface;

public class InscripcionService implements InscripcionServiceInterface {

    private final InscripcionRepositoryInterface inscripcionRepository;
    private final UsuarioRepositoryInterface usuarioRepository;
    private final ActividadRepositoryInterface actividadRepository;

    public InscripcionService(InscripcionRepositoryInterface inscripcionRepository,
            UsuarioRepositoryInterface usuarioRepository,
            ActividadRepositoryInterface actividadRepository) {
        this.inscripcionRepository = inscripcionRepository;
        this.usuarioRepository = usuarioRepository;
        this.actividadRepository = actividadRepository;
    }

    @Override
    public List<Inscripcion> findAll() {
        return inscripcionRepository.findAll();
    }

    @Override
    public Inscripcion findById(int id) {
        if (id <= 0) {
            return null;
        }
        return inscripcionRepository.findById(id);
    }

    @Override
    public List<Inscripcion> findByUsuario(int idUsuario) {
        if (idUsuario <= 0) {
            return new ArrayList<>();
        }
        List<Inscripcion> todas = inscripcionRepository.findAll();
        List<Inscripcion> resultado = new ArrayList<>();
        for (Inscripcion inscripcion : todas) {
            if (inscripcion.getIdUsuario() == idUsuario) {
                resultado.add(inscripcion);
            }
        }
        return resultado;
    }

    @Override
    public List<Inscripcion> findByActividad(int idActividad) {
        if (idActividad <= 0) {
            return new ArrayList<>();
        }
        List<Inscripcion> todas = inscripcionRepository.findAll();
        List<Inscripcion> resultado = new ArrayList<>();
        for (Inscripcion inscripcion : todas) {
            if (inscripcion.getIdActividad() == idActividad) {
                resultado.add(inscripcion);
            }
        }
        return resultado;
    }

    @Override
    public boolean save(Inscripcion inscripcion) {
        validarInscripcion(inscripcion);

        if (inscripcion.getFecha().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha no puede ser futura");
        }

        if (inscripcionRepository.findById(inscripcion.getId()) != null) {
            return false;
        }

        if (usuarioRepository.findById(inscripcion.getIdUsuario()) == null) {
            return false;
        }

        Actividad actividad = actividadRepository.findById(inscripcion.getIdActividad());
        if (actividad == null) {
            return false;
        }

        List<Inscripcion> todasLasInscripciones = inscripcionRepository.findAll();
        boolean yaInscrito = false;
        for (Inscripcion i : todasLasInscripciones) {
            if (i.getIdUsuario() == inscripcion.getIdUsuario()
                    && i.getIdActividad() == inscripcion.getIdActividad()
                    && Constantes.ACTIVA.equals(i.getEstado())) {
                yaInscrito = true;
            }
        }
        if (yaInscrito) {
            return false;
        }

        if (actividad.estaCompleta()) {
            throw new IllegalStateException("La actividad no tiene plazas disponibles");
        }

        return inscripcionRepository.save(inscripcion);
    }

    @Override
    public boolean update(Inscripcion inscripcion) {
        if (inscripcion == null) {
            return false;
        }
        if (inscripcionRepository.findById(inscripcion.getId()) == null) {
            return false;
        }
        return inscripcionRepository.update(inscripcion);
    }

    @Override
    public boolean delete(int id) {
        if (id <= 0) {
            return false;
        }
        return inscripcionRepository.delete(id);
    }

    @Override
    public boolean cancelar(int id) {
        if (id <= 0) {
            return false;
        }
        Inscripcion inscripcion = inscripcionRepository.findById(id);
        if (inscripcion == null) {
            return false;
        }
        if (!inscripcion.estaActiva()) {
            return false;
        }
        Actividad actividad = actividadRepository.findById(inscripcion.getIdActividad());
        if (actividad != null) {
            actividad.cancelarPlaza();
            actividadRepository.update(actividad);
        }
        inscripcion.cancelar();
        return inscripcionRepository.update(inscripcion);
    }

    private void validarInscripcion(Inscripcion inscripcion) {
        if (inscripcion == null) {
            throw new IllegalArgumentException("La inscripción no puede ser null");
        }
        if (inscripcion.getId() <= 0) {
            throw new IllegalArgumentException("El id debe ser positivo");
        }
        if (inscripcion.getIdUsuario() <= 0) {
            throw new IllegalArgumentException("El idUsuario debe ser positivo");
        }
        if (inscripcion.getIdActividad() <= 0) {
            throw new IllegalArgumentException("El idActividad debe ser positivo");
        }
    }
}