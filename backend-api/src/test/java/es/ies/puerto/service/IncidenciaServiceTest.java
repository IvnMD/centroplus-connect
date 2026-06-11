package es.ies.puerto.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import es.ies.puerto.model.Incidencia;
import es.ies.puerto.repository.IncidenciaRepository;

@ExtendWith(MockitoExtension.class)
class IncidenciaServiceTest {

  @Mock
  private IncidenciaRepository incidenciaRepository;

  @InjectMocks
  private IncidenciaService incidenciaService;

  @Test
  void findAllTest() {
    Incidencia i1 = new Incidencia();
    i1.setAsunto("Problema con el gimnasio");
    i1.setIdUsuario(1L);

    Incidencia i2 = new Incidencia();
    i2.setAsunto("Falta material en clase");
    i2.setIdUsuario(2L);

    when(incidenciaRepository.findAll()).thenReturn(Arrays.asList(i1, i2));

    List<Incidencia> resultado = incidenciaService.findAll();

    assertEquals(2, resultado.size());
    verify(incidenciaRepository, times(1)).findAll();
  }

  @Test
  void findByIdExisteTest() {
    Incidencia i = new Incidencia();
    i.setId(1L);
    i.setAsunto("Problema con el gimnasio");
    i.setIdUsuario(1L);
    i.setEstado("PENDIENTE");

    when(incidenciaRepository.existsById(1L)).thenReturn(true);
    when(incidenciaRepository.findById(1L)).thenReturn(Optional.of(i));

    Incidencia resultado = incidenciaService.findById(1L);

    assertEquals("Problema con el gimnasio", resultado.getAsunto());
  }

  @Test
  void findByIdNoExisteTest() {
    when(incidenciaRepository.existsById(99L)).thenReturn(false);

    try {
      incidenciaService.findById(99L);
      fail("Deberia haber lanzado IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Incidencia no encontrada con id: 99", e.getMessage());
    }
  }

  @Test
  void saveEstadoPendienteTest() {
    Incidencia i = new Incidencia();
    i.setAsunto("Falta calefaccion");
    i.setIdUsuario(1L);

    when(incidenciaRepository.save(i)).thenReturn(i);

    Incidencia resultado = incidenciaService.save(i);

    assertEquals("PENDIENTE", resultado.getEstado());
    verify(incidenciaRepository, times(1)).save(i);
  }

  @Test
  void saveAsuntoVacioTest() {
    Incidencia i = new Incidencia();
    i.setAsunto("");
    i.setIdUsuario(1L);

    try {
      incidenciaService.save(i);
      fail("Deberia haber lanzado IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("El asunto no puede estar vacio", e.getMessage());
    }
  }

  @Test
  void saveSinUsuarioTest() {
    Incidencia i = new Incidencia();
    i.setAsunto("Problema con el aula");
    i.setIdUsuario(null);

    try {
      incidenciaService.save(i);
      fail("Deberia haber lanzado IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("La incidencia debe tener un usuario", e.getMessage());
    }
  }

  @Test
  void deleteByIdExisteTest() {
    when(incidenciaRepository.existsById(1L)).thenReturn(true);

    incidenciaService.deleteById(1L);

    verify(incidenciaRepository, times(1)).deleteById(1L);
  }
}