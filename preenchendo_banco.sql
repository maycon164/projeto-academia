USE academiadb;

---------------------------------INSERINDO PESSOAS
INSERT INTO pessoa (cpf, nome, nascimento, email, telefone, rua, bairro, num) VALUES
--ALUNOS
('50409978841', 'Felipe', '2002-11-21', 'teste@gmail.com','16899-3423', 'Rua das Laranjas', 'Laranjeiras', '191'),
('82259101097', 'José', '1991-11-21', 'jose@gmail.com', '73372-7435','Avenida XV', 'Bairro A', '19'),
('41264118015', 'Maria', '1992-11-11', 'maria@gmail.com','10697-2851' , 'Rua Anecy Rocha', 'Bairro A', '159'),
('98757864039', 'Rafael', '1990-02-15', 'rafaael@gmail.com', '12378-8371' , 'Avenida Vitoria', 'Laranjeiras', '189'),
('95387925052', 'Yasmim', '2001-12-14', 'yasmim@gmail.com', '15702-4917','Rua Anecy Rocha ', 'Bairro A', '89'),

--INSTRUTORES
('39311504025', 'Samira', '1999-11-21', 'sami@gmail.com', '73837-3687','Rua dos Limoes', 'Limoeiro', '129'),
('96319340045', 'Karen', '1989-03-21', 'karen@gmail.com', '43355-5988', 'Rua dos Limoes', 'Limoeiro', '171'),
('05460595037', 'Renan', '2002-11-21', 'renan@gmail.com', '28260-8490','Rua das Laranjas', 'Laranjeiras', '28')

INSERT INTO pessoa (cpf, nome, sexo, nascimento, email, telefone, bairro, rua, num) VALUES
('62114601048', 'Eduardo', 'M', '1992-09-26', 'eduardo@gmail.com', '11934124819', 'Socorro', 'Avenida de Penido', '121'),
('66449896086', 'Rafaela', 'F', '1998-11-09', 'rafaela@gmail.com', '11973982483', 'Vila Carmosina', 'Rua Itamori', '171'),
('10866672044', 'Gabriela', 'F', '1997-01-16', 'gabi@gmail.com', '11983761201', 'Socorro', 'Avenida de Penido', '15'),
('16220299094', 'Dayana', 'F', '1990-04-26', 'dayana@gmail.com', '11998354891', 'Jardim Luso', 'Rua Professor Freitas Julião', '19')


---INSTRUTORES
INSERT INTO instrutor (cpf, ativo, especializacao) VALUES
('39311504025', 1, 'Dança e Consciência Corporal'),
('96319340045', 1, 'Condicionamento Físico no Envelhecimento'),
('05460595037', 1, 'Educação Especial')




---ALUNOS
INSERT INTO aluno(cpf, ativo) VALUES 
('50409978841', 1),
('82259101097', 1),
('41264118015', 1),
('98757864039', 0),
('95387925052', 1)


INSERT INTO aluno (cpf, ativo) VALUES
('62114601048', 1),
('66449896086', 1),
('10866672044', 1),
('16220299094', 1)


---------------------------------INSERINDO PLANOS

INSERT
	INTO
	plano(nome,
	descricao,
	preco,
	duracao)
VALUES

('Musculação',
'O treinamento de força é um exercício, ou um conjunto de exercícios, 
que ajuda os diferentes músculos do seu corpo para se tornar mais forte',
60.99,
60),

('Boxe','Boxe ou pugilismo é um esporte de combate no qual os lutadores 
calçam luvas acolchoadas e utilizam as mãos para atacar e defender', 99.99, 45),

('Natação', 'um dos exercícios mais completos por movimentar grande parte 
dos músculos e articulações do corpo', 100.29, 60),

('Ginástica', ' a prática de uma série de movimentos exigentes de força, flexibilidade 
e coordenação motora para fins únicos de aperfeiçoamento físico e mental', 45.99, 30)

--------------------------------- RELACIONANDO PLANOS COM INSTRUTORES
INSERT INTO instrutor_plano(cpf_instrutor, id_plano) VALUES
('05460595037', 1),
('05460595037', 2),
('39311504025', 3),
('39311504025', 1),
('96319340045', 4)

--------------------------------- RELACIONANDO PLANOS COM ALUNOS
INSERT INTO aluno_plano(cpf_aluno, id_plano, data_inicio, data_expiracao) VALUES
('50409978841', 1, default, DATEADD(DAY, 60, GETDATE())),
('82259101097', 1, default, DATEADD(DAY, 60, GETDATE())),
('95387925052', 3, default, DATEADD(DAY, 60, GETDATE())),
('98757864039', 2, default, DATEADD(DAY, 45, GETDATE())),
('41264118015', 4, default, DATEADD(DAY, 30, GETDATE()))


--------------------------------- RELACIONANDO PLANO_ALUNO COM PAGAMENTOS
/*
 * 1 maria, ginastica 45.99
 * 2 felipe, musculacao 60.99
 * 3 jose, musculacao,
 * 4 yasmim, natacao 100.29
 * 5 rafael, boxe 99.99
 * */
INSERT INTO pagamento (id_aluno_plano, data_pagamento, valor_total, forma_pagamento, situacao) VALUES
(1, null, 45.99, null, 'NÃO PAGO' ),
(2, null, 60.99, null, 'NÃO PAGO' ),
(3, GETDATE(), 60.99, 'DINHEIRO', 'PAGO' ),
(4, GETDATE(), 100.29, 'CARTÃO', 'PAGO' ),
(5, null, 99.99, null, 'NÃO PAGO' )

SELECT * FROM pagamento;
DELETE pagamento;

--------------------------------- POR ENQUANTO O SISTEMA NÃO TEM AVALIAÇÕES FEITASA

SELECT * FROM aluno_plano;

UPDATE pessoa
SET email = 'felipe@gmail.com'
WHERE nome = 'Felipe'

----QUERY QUE RETORNA TODOS OS INSTRUTORES
SELECT p.*, i.ativo, i.especializacao
FROM pessoa p, instrutor i 
WHERE p.cpf = i.cpf 

---QUERY RETORNA TODOS OS ALUNOS
SELECT p.*, a.ativo, a.observacoes
FROM pessoa p, aluno a
WHERE p.cpf = a.cpf 

-----QUERY QUE RETORNA OS INTRUTORES E OS PLANOS QUE ELES AUXILIAM
SELECT i.cpf, pe.nome, p.nome AS plano
FROM instrutor i, plano p, instrutor_plano ip, pessoa pe
WHERE pe.cpf = i.cpf 
AND ip.cpf_instrutor = i.cpf 
AND ip.id_plano = p.id_plano 

---QUERY QUE RETORNA OS NOME DO ALUNO, O PLANO QUE ASSINOU, DATA_INICIO, DATA_EXPIRACAO DO PLANO
SELECT ap.id_aluno_plano AS id, a.cpf, p.nome, pl.nome AS plano, pl.preco, pl.duracao AS dias,
CONVERT(CHAR(11), ap.data_inicio, 103) AS inicio, 
CONVERT(CHAR(11),ap.data_expiracao, 103 ) as expira
FROM pessoa p, aluno a, plano pl, aluno_plano ap 
WHERE p.cpf = a.cpf
AND pl.id_plano = ap.id_plano 
AND a.cpf = ap.cpf_aluno

---QUERY QUE RETORNA nome_aluno, nome_plano, data_pagamento, valor_total
SELECT p.nome, a.cpf, pl.nome, pag.data_pagamento, pag.valor_total
FROM pessoa p, aluno a, plano pl, pagamento pag, aluno_plano ap
WHERE p.cpf = a.cpf
AND ap.cpf_aluno = a.cpf
AND ap.id_plano = pl.id_plano
AND pag.id_aluno_plano = ap.id_aluno_plano 

---------------------------------PRA VOCE TENTAR DEPOIS

---QUERY QUE RETORNA nome_aluno, plano, nome_instrutor
----- TAMBEM NÃO CONSEGUI FAZER ESSE
SELECT p.nome AS aluno, pl.nome AS plano, p.nome AS instrutor
FROM pessoa p, plano pl, aluno a, instrutor i, aluno_plano ap, instrutor_plano ip 
WHERE p.cpf = a.cpf 
AND p.cpf = i.cpf 
AND ip.cpf_instrutor = i.cpf 
AND ip.id_plano = pl.id_plano 
AND ap.cpf_aluno = a.cpf 
AND ap.id_plano = pl.id_plano 

---QUERY QUE RETORNA nome_aluno, plano, data_pagamento, valor_total, nome_instrutor, data_inicio, data_expiracao
----- NÃO CONSIGUE FAZER ESSA, TENTAR DEPOIS 
SELECT p.nome, a.cpf, pl.nome, pag.data_pagamento, pag.valor_total, p.nome as instrutor
FROM pessoa p, aluno a, plano pl, pagamento pag, aluno_plano ap, instrutor i, instrutor_plano ip 
WHERE p.cpf = a.cpf
AND ap.cpf_aluno = a.cpf
AND ap.id_plano = pl.id_plano
AND ip.cpf_instrutor = i.cpf 
AND pag.id_aluno_plano = ap.id_aluno_plano 



exec sp_columns pessoa;
exec sp_columns instrutor;
exec sp_columns aluno;
exec sp_columns plano;
exec sp_columns instrutor_plano;
exec sp_columns aluno_plano;
exec sp_columns pagamento;


