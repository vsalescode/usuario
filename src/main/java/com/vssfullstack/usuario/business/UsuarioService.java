package com.vssfullstack.usuario.business;

import com.vssfullstack.usuario.business.converter.UsuarioConverter;
import com.vssfullstack.usuario.business.dto.EnderecoDTO;
import com.vssfullstack.usuario.business.dto.TelefoneDTO;
import com.vssfullstack.usuario.business.dto.UsuarioDTO;
import com.vssfullstack.usuario.infrastructure.entity.Endereco;
import com.vssfullstack.usuario.infrastructure.entity.Telefone;
import com.vssfullstack.usuario.infrastructure.entity.Usuario;
import com.vssfullstack.usuario.infrastructure.exceptions.BusinessException;
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

    // Salva um novo usuário no sistema
    public UsuarioDTO salvarUsuario(UsuarioDTO usuarioDTO) {

        // Verifica se o DTO é nulo
        if (usuarioDTO == null) {
            throw new ConflictException("Dados do usuário não podem ser nulos.");
        }

        // Valida regras básicas do email
        validaEmail(usuarioDTO.getEmail());

        // Valida regras básicas da senha
        validaSenha(usuarioDTO.getSenha());

        // Verifica se o email já está em uso
        verificaEmailDisponivel(usuarioDTO.getEmail());

        // Criptografa a senha antes de salvar
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));

        // Converte o DTO em entidade Usuario
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);

        // Salva no banco e converte a entidade salva em DTO
        return usuarioConverter.paraUsuarioDTO(
                usuarioRepository.save(usuario)
        );
    }

    // Verifica se o email já está cadastrado
    private void verificaEmailDisponivel(String email) {

        // Verifica no banco se já existe usuário com este email
        if (usuarioRepository.existsByEmail(email)) {
            throw new ConflictException("Email já cadastrado.");
        }
    }

    // Valida regras básicas do email
    private void validaEmail(String email) {

        // Verifica se o email é nulo ou vazio
        if (email == null || email.trim().isEmpty()) {
            throw new BusinessException("Email não pode ser vazio.");
        }

        // Verificação simples de formato
        if (!email.contains("@")) {
            throw new BusinessException("Email inválido.");
        }
    }

    // Valida regras básicas da senha
    private void validaSenha(String senha) {

        // Verifica se a senha é nula ou vazia
        if (senha == null || senha.trim().isEmpty()) {
            throw new BusinessException("Senha não pode ser vazia.");
        }

        // Verifica tamanho mínimo da senha
        if (senha.length() < 6) {
            throw new BusinessException("Senha deve ter pelo menos 6 caracteres.");
        }
    }

    // Busca um usuário pelo email
    public UsuarioDTO buscarUsuarioPorEmail(String email) {

        // Valida se o email foi informado
        if (email == null || email.trim().isEmpty()) {
            throw new ConflictException("Email não pode ser vazio.");
        }

        // Busca o usuário no banco ou lança exceção se não existir
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Email não encontrado."));

        // Converte a entidade para DTO e retorna
        return usuarioConverter.paraUsuarioDTO(usuario);
    }

    // Deleta um usuário pelo email
    public void deletaUsuarioPorEmail(String email) {

        // Verifica se o usuário existe antes de deletar
        if (!usuarioRepository.existsByEmail(email)) {
            throw new ResourceNotFoundException("Email não encontrado.");
        }

        // Deleta o usuário pelo email
        usuarioRepository.deleteByEmail(email);
    }

    // Atualiza os dados do usuário autenticado
    public UsuarioDTO atualizaDadosUsuario(String token, UsuarioDTO usuarioDTO) {

        // Verifica se o DTO é nulo
        if (usuarioDTO == null) {
            throw new ConflictException("Dados do usuário não podem ser nulos.");
        }

        // Extrai o email do token JWT removendo "Bearer "
        String email = jwtUtil.extractUsername(token.substring(7));

        // Criptografa a senha apenas se ela foi enviada
        usuarioDTO.setSenha(
                usuarioDTO.getSenha() != null
                        ? passwordEncoder.encode(usuarioDTO.getSenha())
                        : null
        );

        // Busca os dados atuais do usuário no banco
        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não encontrado")
        );

        // Mescla os dados do DTO com a entidade existente
        Usuario usuario = usuarioConverter.atualizaUsuario(usuarioDTO, usuarioEntity);

        // Salva e converte a entidade atualizada em DTO
        return usuarioConverter.paraUsuarioDTO(
                usuarioRepository.save(usuario)
        );
    }

    // Atualiza um endereço pelo ID
    public EnderecoDTO atualizaDadosEndereco(Long idEndereco, EnderecoDTO enderecoDTO) {

        // Busca o endereço pelo ID ou lança exceção se não existir
        Endereco enderecoEntity = enderecoRepository.findById(idEndereco).orElseThrow(
                () -> new ResourceNotFoundException("Id não encontrado" + idEndereco)
        );

        // Atualiza os dados do endereço com base no DTO
        Endereco endereco = usuarioConverter.atualizaEndereco(enderecoDTO, enderecoEntity);

        // Salva e converte a entidade atualizada em DTO
        return usuarioConverter.paraEnderecoDTO(
                enderecoRepository.save(endereco)
        );
    }

    // Atualiza um telefone pelo ID
    public TelefoneDTO atualizaDadosTelefone(Long idTelefone, TelefoneDTO telefoneDTO) {

        // Busca o telefone pelo ID ou lança exceção se não existir
        Telefone telefoneEntity = telefoneRepository.findById(idTelefone).orElseThrow(
                () -> new ResourceNotFoundException("Id não encontrado" + idTelefone)
        );

        // Atualiza os dados do telefone com base no DTO
        Telefone telefone = usuarioConverter.atualizaTelefone(telefoneDTO, telefoneEntity);

        // Salva e converte a entidade atualizada em DTO
        return usuarioConverter.paraTelefoneDTO(
                telefoneRepository.save(telefone)
        );
    }

    // Cadastra um novo endereço para o usuário autenticado
    public EnderecoDTO cadastraNovoEndereco(String token, EnderecoDTO enderecoDTO) {

        // Extrai o email do token JWT
        String email = jwtUtil.extractUsername(token.substring(7));

        // Busca o usuário pelo email
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não encontrado")
        );

        // Converte o DTO em entidade Endereco associando ao usuário
        Endereco endereco = usuarioConverter.paraEnderecoEntity(enderecoDTO, usuario.getId());

        // Salva o endereço no banco
        Endereco enderecoEntity = enderecoRepository.save(endereco);

        // Converte e retorna o DTO
        return usuarioConverter.paraEnderecoDTO(enderecoEntity);
    }

    // Cadastra um novo endereco para o usuário autenticado
    public TelefoneDTO cadastraNovoTelefone(String token, TelefoneDTO telefoneDTO) {

        // Extrai o email do token JWT
        String email = jwtUtil.extractUsername(token.substring(7));

        // Busca o usuário pelo email
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não encontrado")
        );

        // Converte o DTO em entidade Telefone associando ao usuário
        Telefone telefone  = usuarioConverter.paraTelefoneEntity(telefoneDTO, usuario.getId());

        //Salva o telefone no banco
        Telefone telefoneEntity = telefoneRepository.save(telefone);

        // Converte e retorna o DTO
        return usuarioConverter.paraTelefoneDTO(telefoneEntity);
    }

}
