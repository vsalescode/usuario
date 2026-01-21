package com.vssfullstack.usuario.business;


import com.vssfullstack.usuario.business.converter.UsuarioConverter;
import com.vssfullstack.usuario.business.dto.UsuarioDTO;
import com.vssfullstack.usuario.infrastructure.entity.Usuario;
import com.vssfullstack.usuario.infrastructure.exceptions.ConflictException;
import com.vssfullstack.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.vssfullstack.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;

    // Salva usuario
    public UsuarioDTO salvarUsuario(UsuarioDTO usuarioDTO) {

        if (usuarioDTO == null) {
            throw new ConflictException("Dados do usuário não podem ser nulos.");
        }

        validaEmail(usuarioDTO.getEmail());
        validaSenha(usuarioDTO.getSenha());
        verificaEmailDisponivel(usuarioDTO.getEmail());

        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));

        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);

        return usuarioConverter.paraUsuarioDTO(
                usuarioRepository.save(usuario)
        );
    }

    // Verifica se email já foi cadastrado
    private void verificaEmailDisponivel(String email) {
        if (usuarioRepository.existsByEmail(email)) {
            throw new ConflictException("Email já cadastrado.");
        }
    }

    // Valida email
    private void validaEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new ConflictException("Email não pode ser vazio.");
        }

        if (!email.contains("@")) {
            throw new ConflictException("Email inválido.");
        }
    }


    // Valida senha
    private void validaSenha(String senha) {
        if (senha == null || senha.trim().isEmpty()) {
            throw new ConflictException("Senha não pode ser vazia.");
        }

        if (senha.length() < 6) {
            throw new ConflictException("Senha deve ter pelo menos 6 caracteres.");
        }
    }

    // Busca usuario por email
    public UsuarioDTO buscarUsuarioPorEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new ConflictException("Email não pode ser vazio.");
        }

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Email não encontrado."));

        return usuarioConverter.paraUsuarioDTO(usuario);
    }


    // Deleta usuario por email
    public void deletaUsuarioPorEmail(String email) {
        usuarioRepository.deleteByEmail(email);
    }


}
