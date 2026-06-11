package es.ies.puerto.service;

import es.ies.puerto.model.Usuario;
import es.ies.puerto.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsuarioService {

  private final UsuarioRepository usuarioRepository;

  public UsuarioService(UsuarioRepository usuarioRepository) {
    this.usuarioRepository = usuarioRepository;
  }

  public List<Usuario> findAll() {
    return usuarioRepository.findAll();
  }

  public Usuario findById(Long id) {
    if (!usuarioRepository.existsById(id)) {
      throw new IllegalArgumentException("Usuario no encontrado con id: " + id);
    }
    return usuarioRepository.findById(id).get();
  }

  public Usuario save(Usuario usuario) {
    if (usuario.getNombre() == null || usuario.getNombre().isEmpty()) {
      throw new IllegalArgumentException("El nombre del usuario no puede estar vacio");
    }
    if (usuario.getDni() == null || usuario.getDni().isEmpty()) {
      throw new IllegalArgumentException("El DNI no puede estar vacio");
    }
    return usuarioRepository.save(usuario);
  }

  public Usuario update(Long id, Usuario usuario) {
    if (!usuarioRepository.existsById(id)) {
      throw new IllegalArgumentException("Usuario no encontrado con id: " + id);
    }
    usuario.setId(id);
    return usuarioRepository.save(usuario);
  }

  public void deleteById(Long id) {
    if (!usuarioRepository.existsById(id)) {
      throw new IllegalArgumentException("Usuario no encontrado con id: " + id);
    }
    usuarioRepository.deleteById(id);
  }
}