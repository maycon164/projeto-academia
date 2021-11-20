USE academiadb;

----------------------------------------CRIANDO VIEWS
---------VIEWS ALUNO
CREATE VIEW vw_aluno AS
SELECT p.nome AS aluno, p.cpf, p.sexo, a.ativo, p.nascimento, p.email, 
p.telefone, p.cep, p.bairro, p.rua, p.num, p.data_matricula, a.observacoes 
FROM pessoa p, aluno a
WHERE p.cpf = a.cpf;

SELECT * FROM vw_aluno;

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

SELECT * FROM vw_instrutor_plano vip 

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

EXECUTE deletar_aluno @cpf = '2'

SELECT * FROM vw_aluno
SELECT * FROM  pagamento

UPDATE pagamento 
SET tipo_pagamento = 'DINHEIRO'
WHERE id_pagamento = 8

---------- STORED PROCEDURE PARA DELETAR UM INSTRUTOR (INSTRUTOR_PLANO, INSTRUTOR, PESSOA)

CREATE PROCEDURE deletar_instrutor
@cpf VARCHAR(12)
AS 
BEGIN
	
	DELETE FROM instrutor_plano WHERE cpf_instrutor = @cpf
	DELETE FROM instrutor WHERE cpf = @cpf
	DELETE FROM pessoa WHERE cpf = @cpf
	
END

EXECUTE deletar_instrutor @cpf = '93243'


------------------------------------- FIM POR ENQUANTO -------------------------------------
exec sp_columns pessoa;
exec sp_columns aluno;
exec sp_columns plano;
exec sp_columns instrutor;

exec sp_columns vw_aluno;




