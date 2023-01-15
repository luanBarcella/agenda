-- Cidade -- 
CREATE SEQUENCE cidade_sequence
INCREMENT 1
START 1;

CREATE TABLE cidade(
	id BIGINT NOT NULL DEFAULT NEXTVAL('cidade_sequence'),
	nome VARCHAR(60) NOT NULL,
	UF VARCHAR(5) NOT NULL,
	imagem VARCHAR(1000),
	PRIMARY KEY(id)
);

-- Local --
CREATE SEQUENCE local_sequence
INCREMENT 1
START 1;

CREATE TABLE local(
	id BIGINT NOT NULL DEFAULT NEXTVAL('local_sequence'),
	nome VARCHAR(60) NOT NULL,
	imagem VARCHAR(1000),
	id_cidade BIGINT,
	PRIMARY KEY(id),
	CONSTRAINT local_cidade_id FOREIGN KEY(id_cidade) REFERENCES cidade(id)
);

-- Evento --
CREATE SEQUENCE evento_sequence
INCREMENT 1
START 1;

CREATE TABLE evento(
	id BIGINT NOT NULL DEFAULT NEXTVAL('evento_sequence'),
	titulo VARCHAR(100) NOT NULL,
	data_inicio TIMESTAMP NOT NULL,
	data_fim TIMESTAMP NOT NULL,
	imagem VARCHAR(1000),
	categoria VARCHAR(100) NOT NULL,
	id_local BIGINT,
	PRIMARY KEY(id),
	CONSTRAINT evento_local_id FOREIGN KEY(id_local) REFERENCES local(id)
);

-- Pessoa --
CREATE SEQUENCE pessoa_sequence
INCREMENT 1
START 1;

CREATE TABLE pessoa(
	id BIGINT NOT NULL DEFAULT NEXTVAL('pessoa_sequence'),
	nome VARCHAR(60) NOT NULL,
	idade INT NOT NULL,
	imagem VARCHAR(1000),
	email VARCHAR(255) NOT NULL,
	id_cidade BIGINT,
	PRIMARY KEY(id),
	CONSTRAINT pessoa_cidade_id FOREIGN KEY(id_cidade) REFERENCES cidade(id)
);

-- Pessoa/Evento --
CREATE SEQUENCE pessoa_evento_sequence
INCREMENT 1
START 1;

CREATE TABLE pessoa_evento(
	id BIGINT NOT NULL DEFAULT NEXTVAL('pessoa_evento_sequence'),
	id_pessoa BIGINT,
	id_evento BIGINT,
	PRIMARY KEY(id),
	CONSTRAINT pessoa_evento_pessoa_id FOREIGN KEY(id_pessoa) REFERENCES pessoa(id),
	CONSTRAINT pessoa_evento_evento_id FOREIGN KEY(id_evento) REFERENCES evento(id)
);