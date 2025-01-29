package com.projeto.service;

import com.projeto.model.Usuario;
import com.projeto.repository.UsuarioRepository;

import org.mindrot.jbcrypt.BCrypt;

public class UsuarioService extends BaseService<Usuario, Long> {

    private UsuarioRepository usuarioRepository;

    public UsuarioService() {
        super(new UsuarioRepository());
        this.usuarioRepository = new UsuarioRepository();
    }

    public Usuario cadastrarUsuario(Usuario usuario) {
        if (usuarioRepository.buscarPorLogin(usuario.getLogin()) != null) {
            System.out.println("Login já cadastrado!");
            return null;
        }

        String senhaCriptografada = BCrypt.hashpw(usuario.getSenha(), BCrypt.gensalt());
        usuario.setSenha(senhaCriptografada);

        return usuarioRepository.salvar(usuario);
    }

    public Usuario autenticarUsuario(String login, String senha) {
        Usuario usuario = usuarioRepository.buscarPorLogin(login);

        if (usuario == null) {
            System.out.println("Usuário não encontrado!");
            return null;
        }

        if (!BCrypt.checkpw(senha, usuario.getSenha())) {
            System.out.println("Senha inválida!");
            return null;
        }

        return usuario;
    }

    public Usuario buscarPorLogin(String login) {
        Usuario usuario = usuarioRepository.buscarPorLogin(login);

        if (usuario == null) {
            System.out.println("Usuário não encontrado!");
            return null;
        }

        return usuario;
    }

    public Usuario editarPerfil(Usuario usuarioAtualizado) {
        Usuario usuarioExistente = usuarioRepository.buscarPorId(usuarioAtualizado.getId());

        if (usuarioExistente == null) {
            System.out.println("Usuário não encontrado!");
            return null;
        }

        if (usuarioAtualizado.getId() != usuarioExistente.getId()
                && usuarioAtualizado.getLogin() != usuarioExistente.getLogin()
                && usuarioRepository.buscarPorLogin(usuarioAtualizado.getLogin()) != null) {
            System.out.println("Login já cadastrado!");
            return null;
        }

        usuarioExistente.setNome(usuarioAtualizado.getNome());
        usuarioExistente.setEndereco(usuarioAtualizado.getEndereco());
        usuarioExistente.setEmail(usuarioAtualizado.getEmail());
        usuarioExistente.setLogin(usuarioAtualizado.getLogin());

        return usuarioRepository.atualizar(usuarioExistente);
    }
}
