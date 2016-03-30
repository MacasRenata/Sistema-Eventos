
use ads_eventos;

CREATE TABLE Eventos(
id INT UNSIGNED NOT NULL auto_increment,
titulo VARCHAR(90) NOT NULL,
local VARCHAR(90) NOT NULL,
data_inicial DATE,
data_final DATE,
categoria_evento VARCHAR(45) NOT NULL,
area_evento VARCHAR(45) NOT NULL,
inscricoes BOOL NOT NULL,
data_inicial_inscricao DATE,
data_final_inscricao DATE,
limite_inscricoes INT,
submissao BOOL NOT NULL,
descricao_evento TEXT NOT NULL,
nome_organizador VARCHAR(45) NOT NULL,
email_organizador VARCHAR(45) NOT NULL,
PRIMARY KEY(id));
