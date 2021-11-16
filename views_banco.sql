USE academiadb;

----------------------------------------CRIANDO VIEWS
---------VIEWS ALUNO
CREATE VIEW vw_aluno AS
SELECT p.nome AS aluno, p.cpf, p.sexo, a.ativo, p.nascimento, p.email, 
p.telefone, p.cep, p.bairro, p.rua, p.num, p.data_matricula, a.observacoes 
FROM pessoa p, aluno a
WHERE p.cpf = a.cpf;


--------VIEWS INSTRUTORES
CREATE VIEW vw_instrutor AS
SELECT p.nome AS instrutor, p.cpf, p.sexo, i.ativo, p.nascimento, p.email, 
p.telefone, p.cep, p.bairro, p.rua, p.num, i.especializacao
FROM pessoa p, instrutor i
WHERE p.cpf = i.cpf 


-----VIEWS INSTRUTORES E PLANOS
CREATE VIEW vw_instrutor_plano AS
SELECT vwi.instrutor, vwi.cpf, pl.nome AS plano, pl.id_plano, pl.preco, pl.duracao
FROM vw_instrutor vwi, plano pl, instrutor_plano ip
WHERE ip.cpf_instrutor = vwi.cpf
AND ip.id_plano = pl.id_plano


-----FUNCTION PARA RETORNAR INSTRUTORESDTO DE UM PLANO (id_plano)

CREATE FUNCTION func_instrutoresDTO_por_plano(@id_plano AS INT)
RETURNS TABLE
AS 
RETURN (
	SELECT vip.cpf, vip.instrutor, vip.plano
	FROM vw_instrutor_plano vip
	WHERE vip.id_plano = @id_plano
);

SELECT * FROM [dbo].[func_instrutoresDTO_por_plano](2);

---------VIEW ALUNO E PLANO

CREATE VIEW vw_aluno_plano AS
SELECT vwa.aluno, vwa.cpf, pl.nome AS plano, pl.id_plano, pl.duracao, ap.data_inicio, ap.data_expiracao
FROM vw_aluno vwa, plano pl, aluno_plano ap
WHERE ap.cpf_aluno = vwa.cpf
AND ap.id_plano = pl.id_plano

--------VIEW ALUNO DTO
CREATE VIEW vw_aluno_dto AS
SELECT va.cpf, va.aluno, DATEDIFF(YEAR, va.nascimento, GETDATE()) AS idade,
va.data_matricula, va.ativo
FROM vw_aluno va;


---------- STORED PROCEDURE PARA DELETAR UM ALUNO (ALUNO_PLANO, ALUNO, PESSOA)

CREATE PROCEDURE deletar_aluno
@cpf VARCHAR(12)
AS 
BEGIN
	
	DELETE FROM aluno_plano WHERE cpf_aluno = @cpf
	DELETE FROM aluno WHERE cpf = @cpf
	DELETE FROM pessoa WHERE cpf = @cpf
	
END

EXECUTE deletar_aluno @cpf = '11111111111'

------------------------------------- FIM POR ENQUANTO -------------------------------------
------------------------------------------

CREATE PROCEDURE concatenar
@palavra VARCHAR(100)
AS 
BEGIN
	SELECT (@palavra + ' TESTEANDO CONCATENAÇÃO') AS teste
	RETURN 1
END

EXECUTE [dbo].[concatenar] @palavra = 'OLÁ MUNDO'

--------FUNCTION PARA INSERIR UM ALUNO (NÃO FUNCIONA)

CREATE FUNCTION inserirAluno(
	@cpf VARCHAR(12),
	@nome VARCHAR(100),
	@nascimento DATE,
	@email VARCHAR(100),
	@telefone VARCHAR(12),
	@bairro VARCHAR(100),
	@rua VARCHAR(100),
	@num VARCHAR(5),
	@sexo CHARACTER(1),
	@data_matricula DATE,	
	@ativo BIT,
	@observacoes VARCHAR(200)
)
RETURNS VARCHAR(12)
BEGIN
	INSERT
	INTO
	pessoa(cpf,
	nome,
	nascimento,
	email,
	telefone,
	bairro,
	rua,
	num,
	sexo,
	data_matricula)
VALUES 
	(@cpf,
@nome,
@nascimento,
@email,
@telefone,
@bairro,
@rua,
@num,
@sexo,
@data_matricula)
	RETURN @cpf
END
DROP FUNCTION dbo.inserirAluno;

SELECT dbo.inserirAluno('05460595037', 'MAYKHITARIAN', '2020-02-21', 'maycasso@gmail.com',
'8491312390', 'IGUATEMI', 'ANECY ROCHA', '178', 'M', GETDATE(), 1, 'MUITO ENTUSIASMADO'
);

exec sp_columns pessoa;
exec sp_columns inserirAluno;

CREATE FUNCTION TRIM(@ST VARCHAR(100))
RETURNS VARCHAR(100)
BEGIN 
	RETURN (LTRIM(RTRIM(@ST)))
END
	
DROP FUNCTION TRIM;

SELECT * FROM vw_aluno_plano vap 

SELECT * FROM instrutor_plano ip;

SELECT * FROM vw_aluno_plano vap;

SELECT * FROM aluno_plano
WHERE cpf_aluno = '82259101097'

SELECT * FROM plano;
SELECT * FROM aluno;
SELECT * FROM pessoa;

ALTER TABLE pessoa
ADD cep VARCHAR(8)

SELECT * FROM aluno_plano;
SELECT * FROM aluno WHERE cpf = '11111111111'

UPDATE aluno
SET observacoes = 'Problemas Respiratorios....'
WHERE cpf = '10866672044'

DELETE pessoa
WHERE cpf = '50409978846'

ALTER TABLE pessoa
ADD data_matricula DATE DEFAULT GETDATE();

INSERT INTO pessoa(cpf, nome, sexo, nascimento, email, telefone, bairro, rua, num) VALUES
('44570562000', 'Isabelle', 'F', '2002-12-21', 'isabel@gmail.com','188991123', 'Praia Paulistinha', 'Rua Maria Cleofas', '89'),
('97676760070', 'Leticia', 'F', '1992-03-15', 'leticia@gmail.com','11978413657', 'Jardim Iguatemi', 'Rua Professor Thomaz Aquino', '111'),
('01362539031', 'Luisa', 'F', '1994-09-21', 'luisa@gmail.com','11989447314', 'Vila Prudente', 'Rua Angelina Cidro', '89')

INSERT INTO instrutor (cpf, ativo, especializacao) VALUES
('44570562000', 1, 'Educação, Diversidade e Inclusão Social'),
('97676760070', 1, 'Dança e Consciência Corporal.'),
('01362539031', 1, 'Fundamentos da Dança')

1 MUSCULAÇÃO, 2 BOXE, 3 NATAÇÃO, 4 GINASTICA
INSERT INTO instrutor_plano(cpf_instrutor, id_plano)VALUES
('44570562000', 4),
('97676760070', 4),
('01362539031', 2)


SELECT * FROM vw_aluno

SELECT * FROM instrutor_plano ip 
SELECT * FROM vw_instrutor_plano vip 
SELECT * FROM plano
SELECT * FROM pessoa

INSERT INTO plano(nome, descricao, preco, duracao) VALUES
('Zumba', 'movimentos de diversas danças latinas com o objetivo de emagrecer e modelar o corpo. Criado ainda no ano de 2001',
100.99, 50),
('Crossfit', 'promover melhora da capacidade cardiorrespiratória, condicionamento físico e resistência muscular por meio da combinação de exercícios funcionais,',
200.99, 60),
('Muay Thai', 'Essa Luta dispõe de movimentos que ajudam na perda de massa gorda e ganho de massa magra', 
89.99, 35)

exec sp_columns pessoa;
exec sp_columns aluno;
exec sp_columns plano;
exec sp_columns instrutor;

exec sp_columns vw_aluno;




