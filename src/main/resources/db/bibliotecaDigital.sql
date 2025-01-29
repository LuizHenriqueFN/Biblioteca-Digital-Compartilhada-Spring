-- Tabela Usuario
CREATE TABLE Usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    endereco VARCHAR(255),
    email VARCHAR(100) NOT NULL UNIQUE, -- Novo campo adicionado
    login VARCHAR(50) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL
);

-- Tabela Livro
CREATE TABLE Livro (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL, -- Tamanho ajustado para 100 (como no modelo)
    autor VARCHAR(100),          -- Novo campo adicionado
    ano_publicacao INT,          -- Novo campo adicionado
    disponivel BOOLEAN NOT NULL
);

-- Tabela Emprestimo
CREATE TABLE Emprestimo (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario_solicitante BIGINT NOT NULL,
    usuario_proprietario BIGINT NOT NULL,
    livro BIGINT NOT NULL,
    data_emprestimo DATE NOT NULL,
    data_devolucao DATE,
    FOREIGN KEY (usuario_solicitante) REFERENCES Usuario(id),
    FOREIGN KEY (usuario_proprietario) REFERENCES Usuario(id),
    FOREIGN KEY (livro) REFERENCES Livro(id)
);