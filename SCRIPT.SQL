CREATE DATABASE biblioteca;


CREATE TABLE usuarios(
	id SERIAL PRIMARY KEY,
	nome VARCHAR(150) NOT NULL,
	email VARCHAR(150) NOT NULL,
	senha VARCHAR(90) NOT NULL
);

CREATE TABLE livros(
	id SERIAL PRIMARY KEY,
	titulo VARCHAR(150) NOT NULL,
	autor VARCHAR(90) NOT NULL,
	genero VARCHAR(90) NOT NULL,
	disponiveis INT NOT NULL
	
);

CREATE TABLE alugueis(
	id SERIAL PRIMARY KEY,
	id_usuario INT NOT NULL,
	id_livros INT NOT NULL,
	data_locacao DATE NOT NULL,
	data_devolucao DATE NOT NULL,
	FOREIGN KEY(id_usuario) REFERENCES usuarios(id) ON DELETE CASCADE,
	FOREIGN KEY(id_livros) REFERENCES livros(id) ON DELETE CASCADE
	);

INSERT INTO livros (titulo, autor, genero, disponiveis) 
VALUES 
('O Senhor dos Anéis', 'J.R.R. Tolkien', 'Fantasia', 5),
('1984', 'George Orwell', 'Ficção Distópica', 3),
('Dom Quixote', 'Miguel de Cervantes', 'Clássico', 2),
('Orgulho e Preconceito', 'Jane Austen', 'Romance', 4),
('O Pequeno Príncipe', 'Antoine de Saint-Exupéry', 'Infantil', 6),
('A Revolução dos Bichos', 'George Orwell', 'Fábula', 7),
('Cem Anos de Solidão', 'Gabriel García Márquez', 'Realismo Mágico', 3),
('Moby Dick', 'Herman Melville', 'Aventura', 4),
('O Grande Gatsby', 'F. Scott Fitzgerald', 'Romance', 2),
('Crime e Castigo', 'Fyodor Dostoevsky', 'Drama', 5);

INSERT INTO usuarios (nome, email, senha)
VALUES
    ('Carlos Eduardo', 'carloseduardo@example.com', 'senha123'),
    ('Maria Silva', 'mariasilva@example.com', 'senha456'),
    ('João Pereira', 'joaopereira@example.com', 'senha789'),
    ('Ana Souza', 'anasouza@example.com', 'senhaabc'),
    ('Lucas Almeida', 'lucasalmeida@example.com', 'senhadef');
INSERT INTO alugueis (id_usuario, id_livros, data_locacao, data_devolucao)
VALUES
    (1, 2, '2024-09-01', '2024-09-16'), -- 15 dias após a locação
    (2, 3, '2024-08-20', '2024-09-05'), -- vencido
    (3, 1, '2024-09-05', '2024-09-20'), -- vencido
    (4, 4, '2024-09-15', '2024-09-30'), -- vencido
    (1, 5, '2024-10-01', '2024-10-16'), -- vencido

-- Inserindo aluguéis que ainda não venceram
    (2, 1, '2024-10-10', '2024-10-25'), -- ainda não vencido
    (3, 2, '2024-10-12', '2024-10-27'), -- ainda não vencido
    (4, 3, '2024-10-05', '2024-10-20'); -- ainda não vencido
