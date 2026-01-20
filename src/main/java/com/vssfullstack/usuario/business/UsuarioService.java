package com.vssfullstack.usuario.business;


import com.vssfullstack.usuario.business.converter.UsuarioConverter;
import com.vssfullstack.usuario.business.dto.UsuarioDTO;
import com.vssfullstack.usuario.infrastructure.entity.Usuario;
import com.vssfullstack.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;

    public UsuarioDTO salvarUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(
                usuarioRepository.save(usuario)
        );
    }

}
