Enunciado ACEA Desenvolvimento Backend:
1) Desenvolva o estudo de caso abaixo seguindo as seguintes regras:
	A) Você deve criar o backend da aplicação do estudo de caso apresentado;
	B) A documentação do backend deve ser feito via Swagger;
	C) O backend será criado com Spring Boot e seus sub projetos (Data, Security, Batch);
	D) Faça a validação dos dados usando Bean Validations e faça o tratamento de erros personalizado;
	E) Todo CRUD deve ter paginação e filtragem de dados e ordenação na listagem dos dados inseridos;
	F) O sistema deve ter uma forma de autenticação (login do spring security com token JWT).
	G) O sistema deve possibilitar a alteração dos dados pessoais (editar perfil) e recuperação de senha;

Estudo de Caso 6

Crie um protótipo totalmente funcional chamado "Biblioteca Digital Compartilhada", a ideia é permitir que os moradores de uma comunidade compartilhem livros entre si. Assim, cada usuário pode cadastrar livros que está disposto a compartilhar e também registrar os empréstimos realizados. Além disso, o sistema permite acompanhar quais livros estão disponíveis e um histórico de empréstimos por usuário.

Sendo assim, crie um cadastro de usuário (login, senha, nome, endereco), um cadastro de livro (código, titulo) e um cadastro de registros de empréstimo (usuário solicitante, usuário proprietário, livro, data de emprestimo, cata de devolução).

O sistema deve emitir um relatório com a listagem de todos os livros registrados e disponíveis. Ofereça uma opção de filtragem por livro em um determinado período.