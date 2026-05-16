-- =========================================
-- TABELA EMPRESA
-- =========================================

CREATE TABLE EMPRESA (
                         id_empresa NUMBER PRIMARY KEY,
                         nm_empresa VARCHAR2(100) NOT NULL
);

CREATE SEQUENCE SEQ_EMPRESA
    START WITH 1
    INCREMENT BY 1;

-- =========================================
-- TABELA SETOR
-- =========================================

CREATE TABLE SETOR (
                       id_setor NUMBER PRIMARY KEY,
                       EMPRESA_id_empresa NUMBER NOT NULL,
                       nm_setor VARCHAR2(100) NOT NULL,

                       CONSTRAINT SETOR_EMPRESA_FK
                           FOREIGN KEY (EMPRESA_id_empresa)
                               REFERENCES EMPRESA(id_empresa)
);

CREATE SEQUENCE SEQ_SETOR
    START WITH 1
    INCREMENT BY 1;

-- =========================================
-- TABELA EQUIPAMENTO
-- =========================================

CREATE TABLE EQUIPAMENTO (
                             id_equipamento NUMBER PRIMARY KEY,
                             SETOR_id_setor NUMBER NOT NULL,
                             nm_equipamento VARCHAR2(100) NOT NULL,
                             status VARCHAR2(10) NOT NULL,
                             consumo_watts NUMBER NOT NULL,

                             CONSTRAINT EQUIPAMENTO_SETOR_FK
                                 FOREIGN KEY (SETOR_id_setor)
                                     REFERENCES SETOR(id_setor)
);

CREATE SEQUENCE SEQ_EQUIPAMENTO
    START WITH 1
    INCREMENT BY 1;

-- =========================================
-- TABELA CONSUMO_ENERGIA
-- =========================================

CREATE TABLE CONSUMO_ENERGIA (
                                 id_consumo NUMBER PRIMARY KEY,
                                 EQUIPAMENTO_id_equipamento NUMBER NOT NULL,
                                 data_hora DATE NOT NULL,
                                 consumo_kwh NUMBER NOT NULL,

                                 CONSTRAINT CONSUMO_ENERGIA_EQUIPAMENTO_FK
                                     FOREIGN KEY (EQUIPAMENTO_id_equipamento)
                                         REFERENCES EQUIPAMENTO(id_equipamento)
);

CREATE SEQUENCE SEQ_CONSUMO
    START WITH 1
    INCREMENT BY 1;

-- =========================================
-- TABELA ALERTA
-- =========================================

CREATE TABLE ALERTA (
                        id_alerta NUMBER PRIMARY KEY,
                        EQUIPAMENTO_id_equipamento NUMBER NOT NULL,
                        mensagem VARCHAR2(255) NOT NULL,
                        data_hora_alerta DATE NOT NULL,

                        CONSTRAINT ALERTA_EQUIPAMENTO_FK
                            FOREIGN KEY (EQUIPAMENTO_id_equipamento)
                                REFERENCES EQUIPAMENTO(id_equipamento)
);

CREATE SEQUENCE SEQ_ALERTA
    START WITH 1
    INCREMENT BY 1;

-- =========================================
-- INSERTS INICIAIS
-- =========================================

INSERT INTO EMPRESA VALUES (1, 'Empresa Verde');

INSERT INTO SETOR VALUES (1, 1, 'TI');
INSERT INTO SETOR VALUES (2, 1, 'Produção');
INSERT INTO SETOR VALUES (3, 1, 'Administrativo');

INSERT INTO EQUIPAMENTO VALUES (
                                   1,
                                   1,
                                   'Servidor',
                                   'LIGADO',
                                   500
                               );

INSERT INTO EQUIPAMENTO VALUES (
                                   2,
                                   2,
                                   'Máquina A',
                                   'LIGADO',
                                   800
                               );

INSERT INTO CONSUMO_ENERGIA VALUES (
                                       1,
                                       1,
                                       SYSDATE,
                                       120
                                   );

-- =========================================
-- TRIGGER ALERTA DE CONSUMO
-- =========================================

CREATE OR REPLACE TRIGGER TRG_ALERTA_CONSUMO
AFTER INSERT ON CONSUMO_ENERGIA
FOR EACH ROW
DECLARE
LIMITE NUMBER := 100;
BEGIN

    IF :NEW.consumo_kwh > LIMITE THEN

        INSERT INTO ALERTA (
            id_alerta,
            EQUIPAMENTO_id_equipamento,
            mensagem,
            data_hora_alerta
        )
        VALUES (
            SEQ_ALERTA.NEXTVAL,
            :NEW.EQUIPAMENTO_id_equipamento,
            'Consumo elevado detectado',
            SYSDATE
        );

END IF;

END;
/

-- =========================================
-- TRIGGER DESLIGA EQUIPAMENTO
-- =========================================

CREATE OR REPLACE TRIGGER TRG_DESLIGA_EQUIPAMENTO
AFTER INSERT ON CONSUMO_ENERGIA
FOR EACH ROW
BEGIN

    IF :NEW.consumo_kwh = 0 THEN

UPDATE EQUIPAMENTO
SET status = 'DESLIGADO'
WHERE id_equipamento =
      :NEW.EQUIPAMENTO_id_equipamento;

END IF;

END;
/

-- =========================================
-- TRIGGER LIGA EQUIPAMENTO
-- =========================================

CREATE OR REPLACE TRIGGER TRG_LIGA_EQUIPAMENTO
AFTER INSERT ON CONSUMO_ENERGIA
FOR EACH ROW
BEGIN

    IF :NEW.consumo_kwh > 0 THEN

UPDATE EQUIPAMENTO
SET status = 'LIGADO'
WHERE id_equipamento =
      :NEW.EQUIPAMENTO_id_equipamento;

END IF;

END;
/