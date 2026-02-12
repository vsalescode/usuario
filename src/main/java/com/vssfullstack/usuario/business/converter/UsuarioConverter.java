package com.vssfullstack.usuario.business.converter;

import com.vssfullstack.usuario.business.dto.EnderecoDTO;
import com.vssfullstack.usuario.business.dto.TelefoneDTO;
import com.vssfullstack.usuario.business.dto.UsuarioDTO;
import com.vssfullstack.usuario.infrastructure.entity.Endereco;
import com.vssfullstack.usuario.infrastructure.entity.Telefone;
import com.vssfullstack.usuario.infrastructure.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioConverter {

    // Converte UsuarioDTO para entidade Usuario
    public Usuario paraUsuario(UsuarioDTO usuarioDTO) {

        // Cria a entidade Usuario a partir dos dados do DTO
        return Usuario.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .enderecos(usuarioDTO.getEnderecos() != null ? paraListaEndereco(usuarioDTO.getEnderecos()) : null)
                .telefones(usuarioDTO.getTelefones() != null ? paraListaTelefone(usuarioDTO.getTelefones()) : null)
                .build();
    }

    // Converte lista de EnderecoDTO para lista de Endereco
    public List<Endereco> paraListaEndereco(List<EnderecoDTO> enderecoDTOS) {

        // Mapeia cada DTO para entidade Endereco
        return enderecoDTOS.stream().map(this::paraEndereco).toList();
    }

    // Converte EnderecoDTO para entidade Endereco
    public Endereco paraEndereco(EnderecoDTO enderecoDTO) {

        // Cria a entidade Endereco a partir dos dados do DTO
        return Endereco.builder()
                .rua(enderecoDTO.getRua())
                .numero(enderecoDTO.getNumero())
                .cidade(enderecoDTO.getCidade())
                .complemento(enderecoDTO.getComplemento())
                .cep(enderecoDTO.getCep())
                .estado(enderecoDTO.getEstado())
                .build();
    }

    // Converte lista de TelefoneDTO para lista de Telefone
    public List<Telefone> paraListaTelefone(List<TelefoneDTO> telefoneDTOS) {

        // Mapeia cada DTO para entidade Telefone
        return telefoneDTOS.stream().map(this::paraTelefone).toList();
    }

    // Converte TelefoneDTO para entidade Telefone
    public Telefone paraTelefone(TelefoneDTO telefoneDTO) {

        // Cria a entidade Telefone a partir dos dados do DTO
        return Telefone.builder()
                .numero(telefoneDTO.getNumero())
                .ddd(telefoneDTO.getDdd())
                .build();
    }

    // Converte entidade Usuario para UsuarioDTO
    public UsuarioDTO paraUsuarioDTO(Usuario usuario) {

        // Cria o DTO Usuario a partir da entidade
        return UsuarioDTO.builder()
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .senha(usuario.getSenha())
                .enderecos(usuario.getEnderecos() != null ? paraListaEnderecoDTO(usuario.getEnderecos()) : null)
                .telefones(usuario.getTelefones() != null ? paraListaTelefoneDTO(usuario.getTelefones()) : null)
                .build();
    }

    // Converte lista de Endereco para lista de EnderecoDTO
    public List<EnderecoDTO> paraListaEnderecoDTO(List<Endereco> enderecos) {

        // Mapeia cada entidade Endereco para DTO
        return enderecos.stream().map(this::paraEnderecoDTO).toList();
    }

    // Converte entidade Endereco para EnderecoDTO
    public EnderecoDTO paraEnderecoDTO(Endereco endereco) {

        // Cria o DTO Endereco a partir da entidade
        return EnderecoDTO.builder()
                .id(endereco.getId())
                .rua(endereco.getRua())
                .numero(endereco.getNumero())
                .cidade(endereco.getCidade())
                .complemento(endereco.getComplemento())
                .cep(endereco.getCep())
                .estado(endereco.getEstado())
                .build();
    }

    // Converte lista de Telefone para lista de TelefoneDTO
    public List<TelefoneDTO> paraListaTelefoneDTO(List<Telefone> telefones) {

        // Mapeia cada entidade Telefone para DTO
        return telefones.stream().map(this::paraTelefoneDTO).toList();
    }

    // Converte entidade Telefone para TelefoneDTO
    public TelefoneDTO paraTelefoneDTO(Telefone telefone) {

        // Cria o DTO Telefone a partir da entidade
        return TelefoneDTO.builder()
                .id(telefone.getId())
                .numero(telefone.getNumero())
                .ddd(telefone.getDdd())
                .build();
    }

    // Atualiza dados do usuário mantendo valores antigos quando DTO for nulo
    public Usuario atualizaUsuario(UsuarioDTO usuarioDTO, Usuario usuario) {

        // Cria nova entidade Usuario mesclando dados novos e antigos
        return Usuario.builder()
                .nome(usuarioDTO.getNome() != null ? usuarioDTO.getNome() : usuario.getNome())
                .id(usuario.getId())
                .senha(usuarioDTO.getSenha() != null ? usuarioDTO.getSenha() : usuario.getSenha())
                .email(usuarioDTO.getEmail() != null ? usuarioDTO.getEmail() : usuario.getEmail())
                .enderecos(usuario.getEnderecos())
                .telefones(usuario.getTelefones())
                .build();
    }

    // Atualiza dados do endereço mantendo valores antigos quando DTO for nulo
    public Endereco atualizaEndereco(EnderecoDTO enderecoDTO, Endereco endereco) {

        // Cria nova entidade Endereco mesclando dados novos e antigos
        return Endereco.builder()
                .id(endereco.getId())
                .rua(enderecoDTO.getRua() != null ? enderecoDTO.getRua() : endereco.getRua())
                .numero(enderecoDTO.getNumero() != null ? enderecoDTO.getNumero() : endereco.getNumero())
                .cidade(enderecoDTO.getCidade() != null ? enderecoDTO.getCidade() : endereco.getCidade())
                .estado(enderecoDTO.getEstado() != null ? enderecoDTO.getEstado() : endereco.getEstado())
                .cep(enderecoDTO.getCep() != null ? enderecoDTO.getCep() : endereco.getCep())
                .complemento(enderecoDTO.getComplemento() != null ? enderecoDTO.getComplemento() : endereco.getComplemento())
                .usuario_id(endereco.getUsuario_id())
                .build();
    }

    // Atualiza dados do telefone mantendo valores antigos quando DTO for nulo
    public Telefone atualizaTelefone(TelefoneDTO telefoneDTO, Telefone telefone) {

        // Cria nova entidade Telefone mesclando dados novos e antigos
        return Telefone.builder()
                .id(telefone.getId())
                .ddd(telefoneDTO.getDdd() != null ? telefoneDTO.getDdd() : telefone.getDdd())
                .numero(telefoneDTO.getNumero() != null ? telefoneDTO.getNumero() : telefone.getNumero())
                .usuario_id(telefone.getUsuario_id())
                .build();
    }

    // Converte EnderecoDTO em entidade Endereco associada a um usuário
    public Endereco paraEnderecoEntity(EnderecoDTO enderecoDTO, Long idUsuario) {

        // Cria a entidade Endereco vinculando ao id do usuário
        return Endereco.builder()
                .rua(enderecoDTO.getRua())
                .cidade(enderecoDTO.getCidade())
                .numero(enderecoDTO.getNumero())
                .estado(enderecoDTO.getEstado())
                .cep(enderecoDTO.getCep())
                .complemento(enderecoDTO.getComplemento())
                .usuario_id(idUsuario)
                .build();
    }

    // Converte TelefoneDTO em entidade Telefone associada a um usuário
    public Telefone paraTelefoneEntity(TelefoneDTO telefoneDTO, Long idUsuario) {

        // Cria a entidade Telefone vinculando ao id do usuário
        return Telefone.builder()
                .numero(telefoneDTO.getNumero())
                .ddd(telefoneDTO.getDdd())
                .usuario_id(idUsuario)
                .build();

    }
}
