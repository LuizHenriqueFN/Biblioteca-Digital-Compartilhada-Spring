package com.projeto.controller;

import java.util.List;

import com.projeto.model.Usuario;
import com.projeto.service.UsuarioService;

public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController() {
        this.usuarioService = new UsuarioService();
    }

    public Usuario cadastrarUsuario(Usuario usuario) {
        return usuarioService.cadastrarUsuario(usuario);
    }

    public List<Usuario> obterUsuarios() {
        return usuarioService.buscarTodos();
    }

    public Usuario obterUsuarioPorLogin(String login) {
        return usuarioService.buscarPorLogin(login);
    }

    public Usuario obterUsuarioPorId(Long id) {
        return usuarioService.buscarPorId(id);
    }

    public Usuario editarPerfil(Usuario usuario) {
        return usuarioService.editarPerfil(usuario);
    }

    public Usuario autenticarUsuario(String login, String senha) {
        return usuarioService.autenticarUsuario(login, senha);
    }

    public void deletarUsuario(Long id) {
        usuarioService.deletar(id);
    }

}
