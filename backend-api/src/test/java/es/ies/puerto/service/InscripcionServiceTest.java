package es.ies.puerto.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import es.ies.puerto.model.Actividad;
import es.ies.puerto.model.Inscripcion;
import es.ies.puerto.repository.ActividadRepository;
import es.ies.puerto.repository.InscripcionRepository;

@ExtendWith(MockitoExtension.class)
class InscripcionServiceTest {

  @Mock
  private InscripcionRepository inscripcionRepository;

  @Mock
  private ActividadRepository actividadRepository;

  @InjectMocks
  private InscripcionService inscripcionService;

  @Test
  void findAllTest() {
    Inscripcion i1 = new Inscripcion();
    i1.setIdUsuario(1L);
    i1.setIdActividad(1L);

    Inscripcion i2 = new Inscripcion();
    i2.setIdUsuario(2L);
    i2.setIdActividad(1L);

    when(inscripcionRepository.findAll()).thenReturn(Arrays.asList(i1, i2));

    List<Inscripcion> resultado = inscripcionService.findAll();

    assertEquals(2, resultado.size());
    verify(inscripcionRepository, times(1)).findAll();
  }

  @Test
  void findByIdExisteTest() {
    Inscripcion i = new Inscripcion();
    i.setId(1L);
    i.setIdUsuario(1L);
    i.setIdActividad(1L);
    i.setEstado("ACTIVA");

    when(inscripcionRepository.existsById(1L)).thenReturn(true);
    when(inscripcionRepository.findById(1L)).thenReturn(Optional.of(i));

    Inscripcion resultado = inscripcionService.findById(1L);

    assertEquals("ACTIVA", resultado.getEstado());
  }

  @Test
  void findByIdNoExisteTest() {
    when(inscripcionRepository.existsById(99L)).thenReturn(false);

    try {
      inscripcionService.findById(99L);
      fail("Deberia haber lanzado IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Inscripcion no encontrada con id: 99", e.getMessage());
    }
  }

  @Test
  void saveOkTest() {
    Actividad actividad = new Actividad();
    actividad.setId(1L);
    actividad.setNombre("Yoga");
    actividad.setPlazasMaximas(10);
    actividad.setPlazasOcupadas(3);

    Inscripcion i = new Inscripcion();
    i.setIdUsuario(1L);
    i.setIdActividad(1L);

    when(actividadRepository.existsById(1L)).thenReturn(true);
    when(actividadRepository.findById(1L)).thenReturn(Optional.of(actividad));
    when(inscripcionRepository.save(i)).thenReturn(i);

    Inscripcion resultado = inscripcionService.save(i);

    assertNotNull(resultado);
    verify(inscripcionRepository, times(1)).save(i);
    verify(actividadRepository, times(1)).save(actividad);
  }

  @Test
  void saveSinPlazasTest() {
    Actividad actividad = new Actividad();
    actividad.setId(1L);
    actividad.setNombre("Yoga");
    actividad.setPlazasMaximas(5);
    actividad.setPlazasOcupadas(5);

    Inscripcion i = new Inscripcion();
    i.setIdUsuario(1L);
    i.setIdActividad(1L);

    when(actividadRepository.existsById(1L)).thenReturn(true);
    when(actividadRepository.findById(1L)).thenReturn(Optional.of(actividad));

    try {
      inscripcionService.save(i);
      fail("Deberia haber lanzado IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("La actividad no tiene plazas disponibles", e.getMessage());
    }
  }

  @Test
  void deleteLibеtаPlazaTest() {
    Actividad actividad = new Actividad();
    actividad.setId(1L);
    actividad.setPlazasMaximas(10);
    actividad.setPlazasOcupadas(4);

    Inscripcion i = new Inscripcion();
    i.setId(1L);
    i.setIdUsuario(1L);
    i.setIdActividad(1L);

    when(inscripcionRepository.existsById(1L)).thenReturn(true);
    when(inscripcionRepository.findById(1L)).thenReturn(Optional.of(i));
    when(actividadRepository.existsById(1L)).thenReturn(true);
    when(actividadRepository.findById(1L)).thenReturn(Optional.of(actividad));

    inscripcionService.deleteById(1L);

    assertEquals(3, actividad.getPlazasOcupadas());
    verify(inscripcionRepository, times(1)).deleteById(1L);
  }
}