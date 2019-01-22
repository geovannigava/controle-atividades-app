
CREATE SEQUENCE atividade.tb_atividade_id_atividade_seq;

CREATE TABLE atividade.TB_ATIVIDADE (
                ID_ATIVIDADE BIGINT NOT NULL DEFAULT nextval('atividade.tb_atividade_id_atividade_seq'),
                TITULO VARCHAR(60) NOT NULL,
                DESCRICAO VARCHAR(500) NOT NULL,
                TIPO_ATIVIDADE INTEGER NOT NULL,
                STATUS INTEGER NOT NULL,
                CONSTRAINT id_atividade PRIMARY KEY (ID_ATIVIDADE)
);
COMMENT ON TABLE atividade.TB_ATIVIDADE IS 'Armazena atividades.';
COMMENT ON COLUMN atividade.TB_ATIVIDADE.ID_ATIVIDADE IS 'Chave Primária';
COMMENT ON COLUMN atividade.TB_ATIVIDADE.TITULO IS 'Titulo da Atividade';
COMMENT ON COLUMN atividade.TB_ATIVIDADE.DESCRICAO IS 'Descricao da Atividade.';
COMMENT ON COLUMN atividade.TB_ATIVIDADE.TIPO_ATIVIDADE IS 'Tipo de Atividade.';
COMMENT ON COLUMN atividade.TB_ATIVIDADE.STATUS IS 'Status da Atividade.';


ALTER SEQUENCE atividade.tb_atividade_id_atividade_seq OWNED BY atividade.TB_ATIVIDADE.ID_ATIVIDADE;
