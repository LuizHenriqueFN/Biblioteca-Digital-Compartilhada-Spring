package com.projeto.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Usuario", schema = "railway")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @Column(name = "endereco", length = 255)
    private String endereco;

    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;

    @Column(name = "login", length = 50, nullable = false, unique = true)
    private String login;

    @Column(name = "senha", nullable = false)
    private String senha;
}
