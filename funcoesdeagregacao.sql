use academiadb;

---- SELECT GERAL ?????
CREATE VIEW vw_info_geral
AS
SELECT
	(SELECT COUNT(cpf) FROM aluno) AS qtdAluno,
	(SELECT COUNT(cpf) FROM instrutor) AS qtdInstrutor,
	(SELECT COUNT(id_plano) FROM plano) AS qtdPlano
	
SELECT * FROM vw_info_geral vig 

select * from vw_info_geral vig 
		
--- QUANTIDADE DE ALUNOS MATRICULADOS EM CADA PLANO
CREATE VIEW vw_plano_aluno
AS
SELECT p.nome, count(ap.cpf_aluno) as qtdAluno
FROM plano p, aluno_plano ap  
WHERE ap.id_plano = p.id_plano  
GROUP by p.nome 

select  * from vw_plano_aluno vpa 

--- QUANTIDADE DE INSTRUTORES EM CADA PLANO
CREATE VIEW vw_plano_instrutor
AS
SELECT p.nome, count(ip.cpf_instrutor) as qtdInstrutor
FROM plano p, instrutor_plano ip 
WHERE ip.id_plano = p.id_plano  
GROUP by p.nome

select * from vw_plano_instrutor vpi 

--- PLANO COM MAIS ALUNOS
SELECT TOP 1 * FROM vw_plano_aluno 
ORDER BY qtdAluno DESC

--- PLANO COM MAIS INSTRUTORES
SELECT TOP 1  * FROM vw_plano_instrutor vpi 
ORDER BY qtdInstrutor DESC

--- PENDENCIAS PAGAMENTOS EM ABERTOS
SELECT COUNT(cpf_aluno) AS status_aberto
FROM pagamento
WHERE status = 'ABERTO'


SELECT * FROM aluno_plano ap 





