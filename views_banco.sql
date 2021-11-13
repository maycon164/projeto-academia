USE academiadb;


----------------------------------------CRIANDO VIWES
---------VIEWS ALUNO
CREATE VIEW vw_aluno AS
SELECT p.nome AS aluno, p.cpf, p.sexo, a.ativo, p.nascimento, p.email, 
p.telefone, p.bairro, p.rua, p.num, p.data_matricula, a.observacoes 
FROM pessoa p, aluno a
WHERE p.cpf = a.cpf;

SELECT * FROM vw_aluno;
	
--------VIEWS INSTRUTORES
CREATE VIEW vw_instrutor AS
SELECT p.nome AS instrutor, p.cpf, p.sexo, i.ativo, p.nascimento, p.email, 
p.telefone, p.bairro, p.rua, p.num, i.especializacao
FROM pessoa p, instrutor i
WHERE p.cpf = i.cpf 

SELECT * FROM vw_instrutor;

-----VIEWS INSTRUTORES E PLANOS
CREATE VIEW vw_instrutor_plano AS
SELECT vwi.instrutor, pl.nome, pl.preco, pl.duracao
FROM vw_instrutor vwi, plano pl, instrutor_plano ip
WHERE ip.cpf_instrutor = vwi.cpf
AND ip.id_plano = pl.id_plano
SELECT * FROM vw_instrutor_plano vip;

---------VIEW ALUNO E PLANO

CREATE VIEW vw_aluno_plano AS
SELECT vwa.aluno, vwa.cpf, pl.nome, pl.duracao, ap.data_inicio, ap.data_expiracao
FROM vw_aluno vwa, plano pl, aluno_plano ap
WHERE ap.cpf_aluno = vwa.cpf
AND ap.id_plano = pl.id_plano

SELECT * FROM aluno_plano ap 
exec sp_columns plano;


--------VIEW ALUNO DTO
CREATE VIEW vw_aluno_dto AS
SELECT va.cpf, va.aluno, DATEDIFF(YEAR, va.nascimento, GETDATE()) AS idade,
va.data_matricula, va.ativo
FROM vw_aluno va;

SELECT * FROM vw_aluno_dto vad 


--------FUNCTION PARA INSERIR UM ALUNO

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
	
	INSERT INTO pessoa(cpf, nome, nascimento, email, telefone, bairro, rua, num, sexo, data_matricula) VALUES 
	(@cpf, @nome, @nascimento, @email, @telefone, @bairro, @rua, @num, @sexo, @data_matricula)
	
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
SELECT TRIM('       AS MAYCON        ');


SELECT ap.id_aluno_plano AS id, a.cpf, p.nome, pl.nome AS plano, pl.preco, pl.duracao AS dias,
CONVERT(CHAR(11), ap.data_inicio, 103) AS inicio, 
CONVERT(CHAR(11),ap.data_expiracao, 103 ) as expira
FROM pessoa p, aluno a, plano pl, aluno_plano ap 
WHERE p.cpf = a.cpf
AND pl.id_plano = ap.id_plano 
AND a.cpf = ap.cpf_aluno


SELECT * FROM instrutor_plano ip;
SELECT * FROM plano p;
SELECT * FROM aluno;
SELECT * FROM pessoa;

SELECT * FROM aluno;

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



SELECT * FROM pessoa;

exec sp_columns pessoa;
exec sp_columns aluno;
exec sp_columns plano;
exec sp_columns instrutor;

exec sp_columns vw_aluno;




