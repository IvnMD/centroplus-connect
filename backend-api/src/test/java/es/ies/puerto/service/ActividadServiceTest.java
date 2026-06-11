package es.ies.puerto.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import es.ies.puerto.repository.ActividadRepository;

@ExtendWith(MockitoExtension.class)
class ActividadServiceTest {

  @Mock
  private ActividadRepository actividadRepository;

  @InjectMocks
  private ActividadService actividadService;

  @Test
  void findAllTest() {
    Actividad a1 = new Actividad();
    a1.setNombre("Yoga");
    a1.setTipoActividad("Deportiva");

    Actividad a2 = new Actividad();
    a2.setNombre("Matematicas");
    a2.setTipoActividad("Academica");

    when(actividadRepository.findAll()).thenReturn(Arrays.asList(a1, a2));

    List<Actividad> resultado = actividadService.findAll();

    assertEquals(2, resultado.size());
    verify(actividadRepository, times(1)).findAll();
  }

  @Test
  void findByIdExisteTest() {
    Actividad a = new Actividad();
    a.setId(1L);
    a.setNombre("Yoga");
    a.setTipoActividad("Deportiva");
    a.setPlazasMaximas(10);
    a.setPlazasOcupadas(3);

    when(actividadRepository.existsById(1L)).thenReturn(true);
    when(actividadRepository.findById(1L)).thenReturn(Optional.of(a));

    Actividad resultado = actividadService.findById(1L);

    assertEquals("Yoga", resultado.getNombre());
  }

  @Test
  void findByIdNoExisteTest() {
    when(actividadRepository.existsById(99L)).thenReturn(false);

    try {
      actividadService.findById(99L);
      fail("Deberia haber lanzado IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Actividad no encontrada con id: 99", e.getMessage());
    }
  }

  @Test
  void saveOkTest() {
    Actividad a = new Actividad();
    a.setNombre("Natacion");
    a.setTipoActividad("Deportiva");
    a.setPlazasMaximas(20);

    when(actividadRepository.save(a)).thenReturn(a);

    Actividad resultado = actividadService.save(a);

    assertNotNull(resultado);
    assertEquals("Natacion", resultado.getNombre());
    verify(actividadRepository, times(1)).save(a);
  }

  @Test
  void saveNombreVacioTest() {
    Actividad a = new Actividad();
    a.setNombre("");
    a.setPlazasMaximas(10);

    try {
      actividadService.save(a);
      fail("Deberia haber lanzado IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("El nombre de la actividad no puede estar vacio", e.getMessage());
    }
  }

  @Test
  void savePlazasCeroTest() {
    Actividad a = new Actividad();
    a.setNombre("Pilates");
    a.setPlazasMaximas(0);

    try {
      actividadService.save(a);
      fail("Deberia haber lanzado IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Las plazas maximas deben ser mayor que 0", e.getMessage());
    }
  }

  @Test
  void tienePlazasDisponiblesTest() {
    Actividad a = new Actividad();
    a.setId(1L);
    a.setNombre("Yoga");
    a.setPlazasMaximas(10);
    a.setPlazasOcupadas(5);

    when(actividadRepository.existsById(1L)).thenReturn(true);
    when(actividadRepository.findById(1L)).thenReturn(Optional.of(a));

    boolean resultado = actividadService.tienePlazasDisponibles(1L);

    assertTrue(resultado);
  }

  @Test
  void noTienePlazasDisponiblesTest() {
    Actividad a = new Actividad();
    a.setId(1L);
    a.setNombre("Yoga");
    a.setPlazasMaximas(10);
    a.setPlazasOcupadas(10);

    when(actividadRepository.existsById(1L)).thenReturn(true);
    when(actividadRepository.findById(1L)).thenReturn(Optional.of(a));

    boolean resultado = actividadService.tienePlazasDisponibles(1L);

    assertFalse(resultado);
  }
}