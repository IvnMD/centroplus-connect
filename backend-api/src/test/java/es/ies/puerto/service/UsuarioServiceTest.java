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

import es.ies.puerto.model.Usuario;
import es.ies.puerto.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

  @Mock
  private UsuarioRepository usuarioRepository;

  @InjectMocks
  private UsuarioService usuarioService;

  @Test
  void findAllTest() {
    Usuario u1 = new Usuario();
    u1.setNombre("Ivan");
    u1.setDni("12345678A");

    Usuario u2 = new Usuario();
    u2.setNombre("Maria");
    u2.setDni("87654321B");

    when(usuarioRepository.findAll()).thenReturn(Arrays.asList(u1, u2));

    List<Usuario> resultado = usuarioService.findAll();

    assertEquals(2, resultado.size());
    verify(usuarioRepository, times(1)).findAll();
  }

  @Test
  void findByIdExisteTest() {
    Usuario u = new Usuario();
    u.setId(1L);
    u.setNombre("Ivan");
    u.setDni("12345678A");

    when(usuarioRepository.existsById(1L)).thenReturn(true);
    when(usuarioRepository.findById(1L)).thenReturn(Optional.of(u));

    Usuario resultado = usuarioService.findById(1L);

    assertEquals("Ivan", resultado.getNombre());
  }

  @Test
  void findByIdNoExisteTest() {
    when(usuarioRepository.existsById(99L)).thenReturn(false);

    try {
      usuarioService.findById(99L);
      fail("Deberia haber lanzado IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Usuario no encontrado con id: 99", e.getMessage());
    }
  }

  @Test
  void saveOkTest() {
    Usuario u = new Usuario();
    u.setNombre("Ivan");
    u.setDni("12345678A");

    when(usuarioRepository.save(u)).thenReturn(u);

    Usuario resultado = usuarioService.save(u);

    assertNotNull(resultado);
    verify(usuarioRepository, times(1)).save(u);
  }

  @Test
  void saveNombreVacioTest() {
    Usuario u = new Usuario();
    u.setNombre("");
    u.setDni("12345678A");

    try {
      usuarioService.save(u);
      fail("Deberia haber lanzado IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("El nombre del usuario no puede estar vacio", e.getMessage());
    }
  }

  @Test
  void deleteByIdExisteTest() {
    when(usuarioRepository.existsById(1L)).thenReturn(true);

    usuarioService.deleteById(1L);

    verify(usuarioRepository, times(1)).deleteById(1L);
  }

  @Test
  void deleteByIdNoExisteTest() {
    when(usuarioRepository.existsById(99L)).thenReturn(false);

    try {
      usuarioService.deleteById(99L);
      fail("Deberia haber lanzado IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Usuario no encontrado con id: 99", e.getMessage());
    }
  }
}