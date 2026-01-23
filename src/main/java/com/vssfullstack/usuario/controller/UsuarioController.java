package com.vssfullstack.usuario.controller;

import com.vssfullstack.usuario.business.UsuarioService;
import com.vssfullstack.usuario.business.dto.EnderecoDTO;
import com.vssfullstack.usuario.business.dto.TelefoneDTO;
import com.vssfullstack.usuario.business.dto.UsuarioDTO;
import com.vssfullstack.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final JwtUtil jwtUtil;
    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;

    // Endpoint para cadastrar um novo usuário
    @PostMapping
    public ResponseEntity<UsuarioDTO> salvarUsuario(@RequestBody UsuarioDTO usuarioDTO) {

        // Chama o service para salvar o usuário e retorna o DTO salvo
        return ResponseEntity.ok(usuarioService.salvarUsuario(usuarioDTO));
    }

    // Endpoint para autenticar o usuário e gerar o token JWT
    @PostMapping("/login")
    public String login(@RequestBody UsuarioDTO usuarioDTO) {

        // Realiza a autenticação com email e senha
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        usuarioDTO.getEmail(),
                        usuarioDTO.getSenha()
                )
        );

        // Gera e retorna o token JWT com o email do usuário
        return "Bearer " + jwtUtil.generateToken(authentication.getName());
    }

    // Endpoint para buscar um usuário pelo email
    @GetMapping
    public ResponseEntity<UsuarioDTO> buscarUsuarioPorEmail(@RequestParam("email") String email) {

        // Chama o service para buscar o usuário e retorna o DTO
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorEmail(email));
    }

    // Endpoint para deletar um usuário pelo email
    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deletaUsuarioPorEmail(@PathVariable String email) {

        // Chama o service para deletar o usuário
        usuarioService.deletaUsuarioPorEmail(email);

        // Retorna resposta sem corpo
        return ResponseEntity.ok().build();
    }

    // Endpoint para atualizar os dados do usuário autenticado
    @PutMapping
    public ResponseEntity<UsuarioDTO> atualizaDadosUsuario(@RequestBody UsuarioDTO usuarioDTO,
                                                           @RequestHeader("Authorization") String token) {

        // Chama o service para atualizar os dados e retorna o DTO atualizado
        return ResponseEntity.ok(usuarioService.atualizaDadosUsuario(token, usuarioDTO));
    }

    // Endpoint para atualizar um endereço pelo ID
    @PutMapping("/endereco")
    public ResponseEntity<EnderecoDTO> atualizaDadosEndereco(@RequestBody EnderecoDTO enderecoDTO,
                                                             @RequestParam("id") Long id) {

        // Chama o service para atualizar o endereço e retorna o DTO atualizado
        return ResponseEntity.ok(usuarioService.atualizaDadosEndereco(id, enderecoDTO));
    }

    // Endpoint para atualizar um telefone pelo ID
    @PutMapping("/telefone")
    public ResponseEntity<TelefoneDTO> atualizaDadosTelefone(@RequestBody TelefoneDTO telefoneDTO,
                                                             @RequestParam("id") Long id) {

        // Chama o service para atualizar o telefone e retorna o DTO atualizado
        return ResponseEntity.ok(usuarioService.atualizaDadosTelefone(id, telefoneDTO));
    }

    // Endpoint para cadastrar um novo endereço para o usuário autenticado
    @PostMapping("/endereco")
    public ResponseEntity<EnderecoDTO> cadastraEndereco(@RequestBody EnderecoDTO enderecoDTO,
                                                        @RequestHeader("Authorization") String token) {

        // Chama o service para cadastrar o endereço e retorna o DTO salvo
        return ResponseEntity.ok(usuarioService.cadastraNovoEndereco(token, enderecoDTO));
    }

    // Endpoint para cadastrar um novo telefone para o usuário autenticado
    @PostMapping("/telefone")
    public ResponseEntity<TelefoneDTO> cadastraEndereco(@RequestBody TelefoneDTO telefoneDTO,
                                                        @RequestHeader("Authorization") String token) {

        // Chama o service para cadastrar o telefone e retorna o DTO salvo
        return ResponseEntity.ok(usuarioService.cadastraNovoTelefone(token, telefoneDTO));
    }
}
