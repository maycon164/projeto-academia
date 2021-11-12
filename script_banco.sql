CREATE DATABASE academiadb;
USE academiadb;

------CRIANDO TABELAS
CREATE TABLE pessoa(
	cpf VARCHAR(12) NOT NULL,
	nome VARCHAR(100) NOT NULL,
	nascimento DATE NOT NULL,
	email VARCHAR(100),
	telefone VARCHAR(12)
	
	PRIMARY KEY(cpf)
)
ALTER TABLE pessoa
ADD rua VARCHAR(100), bairro VARCHAR(100), num VARCHAR(5)

CREATE TABLE aluno(
	cpf VARCHAR(12) NOT NULL,
	ativo BIT NOT NULL,
	observacoes VARCHAR(200)
	
	PRIMARY KEY(cpf),
	FOREIGN KEY (cpf) REFERENCES pessoa(cpf)
)


CREATE TABLE instrutor(
	cpf VARCHAR(12) NOT NULL,
	ativo BIT NOT NULL,
	especializacao VARCHAR(200)
	
	PRIMARY KEY(cpf),
	FOREIGN KEY(cpf) REFERENCES pessoa(cpf)
)

CREATE TABLE plano(
	id_plano INT IDENTITY,
	nome VARCHAR(100) NOT NULL,
	descricao VARCHAR(200),
	preco DECIMAL(10,2) NOT NULL,
	duracao INT NOT NULL,
	
	PRIMARY KEY(id_plano) 
)

CREATE TABLE instrutor_plano(
	cpf_instrutor VARCHAR(12) NOT NULL,
	id_plano INT NOT NULL
	
	FOREIGN KEY(cpf_instrutor) REFERENCES instrutor(cpf),
	FOREIGN KEY(id_plano) REFERENCES plano(id_plano)
		
)


CREATE TABLE aluno_plano(
	id_aluno_plano INT IDENTITY,
	cpf_aluno VARCHAR(12) NOT NULL,
	id_plano INT NOT NULL,
	data_inicio DATE DEFAULT GETDATE(),
	data_expiracao DATE
	
	PRIMARY KEY(id_aluno_plano),
	FOREIGN KEY(cpf_aluno) REFERENCES aluno(cpf),
	FOREIGN KEY(id_plano) REFERENCES plano(id_plano),
	
	CONSTRAINT ck_data_expiracao
	CHECK ((data_expiracao) > (data_inicio))
)
---- erro com pk rss
ALTER TABLE aluno_plano 
DROP CONSTRAINT PK__aluno_pl__16C90C0474FBA02D

DROP TABLE aluno_plano 

DROP TABLE aluno_plano;
----- o campo imc deve ser gerado na propria aplicaca
----- os campos de altura e medidas (cm), peso(g) serão convertidos na applicação

CREATE TABLE avaliacao(
	id_avaliacao INT IDENTITY,
	cpf_aluno VARCHAR(12) NOT NULL,
	cpf_instrutor VARCHAR(12) NOT NULL,
	data_avaliacao DATE,
	descricao VARCHAR(200),
	peso INT NOT NULL,
	altura INT NOT NULL,
	tipo VARCHAR(100),
	medida_cintura INT NOT NULL,
	medida_braco INT NOT NULL,
	medida_quadril INT NOT NULL,
	medida_coxa INT NOT NULL,
	imc INT NOT NULL
	
	PRIMARY KEY (id_avaliacao),
	FOREIGN KEY (cpf_aluno) REFERENCES aluno(cpf),
	FOREIGN KEY (cpf_instrutor) REFERENCES instrutor(cpf)
)

CREATE TABLE pagamento(
	id_pagamento INT IDENTITY,
	id_aluno_plano INT NOT NULL,
	data_pagamento DATE,
	valor_total DECIMAL(10, 2),
	forma_pagamento VARCHAR(50),
	situacao VARCHAR(50) DEFAULT 'A PAGAR'
	
	PRIMARY KEY(id_pagamento),
	FOREIGN KEY(id_aluno_plano) REFERENCES aluno_plano(id_aluno_plano)
)
---agora erro com fk rsrsr
ALTER TABLE pagamento
DROP CONSTRAINT FK__pagamento__id_al__619B8048

DROP TABLE pagamento;

-----INSERINDO COLUNA SEXO
ALTER TABLE pessoa
ADD sexo CHAR(1) NOT NULL DEFAULT '?'



/*ELABORAR MAIS SOBRE A FICHA DE TREINO AS TABELAS 
 * COMOS OS EQUIPAMENTOS
 * GRUPOS MUSCULARES
 * FAZER COM QUE A FICHA DE TREINO SEJA ENVIADA POR EMAIL
 * */
DROP TABLE pagamento 


SELECT CASE WHEN ('1999-10-14') > ('2001-09-21')
THEN 'VERDADE'
ELSE 'FALSO'
END AS teste


SELECT * FROM aluno_plano;
SELECT * FROM pessoa;
SELECT * FROM aluno;
SELECT * FROM instrutor;
SELECT * FROM plano;
SELECT * FROM instrutor_plano;
SELECT * FROM pagamento


SELECT p.*, a.observacoes 
FROM pessoa p, aluno a
WHERE a.cpf = p.cpf





exec sp_columns pessoa;
exec sp_columns aluno;
exec sp_columns instrutor;
exec sp_columns aluno_plano;



