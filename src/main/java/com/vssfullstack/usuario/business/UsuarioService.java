package com.vssfullstack.usuario.business;


import com.vssfullstack.usuario.business.converter.UsuarioConverter;
import com.vssfullstack.usuario.business.dto.EnderecoDTO;
import com.vssfullstack.usuario.business.dto.TelefoneDTO;
import com.vssfullstack.usuario.business.dto.UsuarioDTO;
import com.vssfullstack.usuario.infrastructure.entity.Endereco;
import com.vssfullstack.usuario.infrastructure.entity.Telefone;
import com.vssfullstack.usuario.infrastructure.entity.Usuario;
import com.vssfullstack.usuario.infrastructure.exceptions.ConflictException;
import com.vssfullstack.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.vssfullstack.usuario.infrastructure.repository.EnderecoRepository;
import com.vssfullstack.usuario.infrastructure.repository.TelefoneRepository;
import com.vssfullstack.usuario.infrastructure.repository.UsuarioRepository;
import com.vssfullstack.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

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

        if (!usuarioRepository.existsByEmail(email)) {
            throw new ResourceNotFoundException("Email não encontrado.");
        }

        usuarioRepository.deleteByEmail(email);

    }

    // Atualiza dados do usuario
    public UsuarioDTO atualizaDadosUsuario(String token, UsuarioDTO usuarioDTO) {

        if (usuarioDTO == null) {
            throw new ConflictException("Dados do usuário não podem ser nulos.");
        }

        // Aqui busca o email do usuário através do token (email)
        String email = jwtUtil.extractUsername(token.substring(7));


        // Criptografia de senha
        usuarioDTO.setSenha(usuarioDTO.getSenha() != null ? passwordEncoder.encode(usuarioDTO.getSenha()) : null );


        // Buscou os dados do usuario do db
        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não localizado"));
        // Mescla dados que recebemos na requisição DTO com os dados do banco de dados
        Usuario usuario = usuarioConverter.atualizaUsuario(usuarioDTO, usuarioEntity);

        // Salva os dados do usuário convertido e pega os dados convertidos para usuarioDTO
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

    // Atualiza endereço
    public EnderecoDTO atualizaDadosEndereco(Long idEndereco, EnderecoDTO enderecoDTO) {

        Endereco enderecoEntity = enderecoRepository.findById(idEndereco).orElseThrow(
                () -> new ResourceNotFoundException("Id não encontrado" +idEndereco));

        Endereco endereco = usuarioConverter.atualizaEndereco(enderecoDTO, enderecoEntity);

        return  usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));

    }

    // Atualiza telefone
    public TelefoneDTO atualizaDadosTelefone(Long idTelefone, TelefoneDTO telefoneDTO) {

        Telefone telefoneEntity = telefoneRepository.findById(idTelefone).orElseThrow(
                () -> new ResourceNotFoundException("Id não encontrado" + idTelefone));

        Telefone telefone = usuarioConverter.atualizaTelefone(telefoneDTO, telefoneEntity);

        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefone));
    }


}
