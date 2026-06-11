package es.ies.puerto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.ies.puerto.model.Actividad;

@Repository
public interface ActividadRepository extends JpaRepository<Actividad, Long> {
    Actividad findActividadById(Long id);
}