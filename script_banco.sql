CREATE DATABASE academiadb;
USE academiadb;

------CRIANDO TABELAS
CREATE TABLE pessoa(
	cpf VARCHAR(12) NOT NULL,
	nome VARCHAR(100) NOT NULL,
	sexo CHAR(1) NOT NULL DEFAULT '?',
	nascimento DATE NOT NULL,
	email VARCHAR(100),
	telefone VARCHAR(12),
	cep VARCHAR(12),
	rua VARCHAR(100), 
	bairro VARCHAR(100), 
	num VARCHAR(5)
	
	PRIMARY KEY(cpf)
)
ALTER TABLE pessoa
ADD 

CREATE TABLE aluno(
	cpf VARCHAR(12) NOT NULL,
	ativo BIT NOT NULL,
	observacoes VARCHAR(200),
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


CREATE TABLE pagamento(
	id_pagamento INT IDENTITY(1,1),
	cpf_aluno VARCHAR(12) NOT NULL,
	nome_aluno VARCHAR(100),
	id_plano INT,
	plano VARCHAR(100),
	valor_total DECIMAL(9,2) NOT NULL,
	data_pagamento DATE,
	tipo_pagamento VARCHAR(100),
	status VARCHAR(100) NOT NULL
	
	PRIMARY KEY(id_pagamento)
)


---- erro com pk rss
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






exec sp_columns pessoa;
exec sp_columns aluno;
exec sp_columns instrutor;
exec sp_columns aluno_plano;



