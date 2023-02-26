--Script insert to PROJETO
INSERT INTO cliente VALUES(SEQ_CLIENTE.NEXTVAL, '07800145785', 'Gabriel de Jesus', DEFAULT);
INSERT INTO endereco VALUES(SEQ_ENDERECO.NEXTVAL, SEQ_CLIENTE.CURRVAL, 'Rio de Janeiro', 'Rua Tal', 'RJ', 'Brasil', '60000-000');
INSERT INTO contato VALUES(SEQ_CONTATO.NEXTVAL, SEQ_CLIENTE.CURRVAL, '71987456549','gabriel@exemplo.com');
INSERT INTO conta VALUES(SEQ_CONTA.NEXTVAL, SEQ_CLIENTE.CURRVAL, '123456', 1234, 100.00, 200.00, DEFAULT);
INSERT INTO cartao VALUES(SEQ_CARTAO.NEXTVAL, SEQ_CONTA.CURRVAL, sysdate, 602, 1, TO_DATE('01-02-2025', 'dd-mm-yyyy'), null, DEFAULT);
INSERT INTO cartao VALUES(SEQ_CARTAO.NEXTVAL, SEQ_CONTA.CURRVAL, sysdate, 202, 2, TO_DATE('01-02-2025', 'dd-mm-yyyy'), 1000, DEFAULT);
INSERT INTO compra VALUES(SEQ_COMPRA.NEXTVAL, SEQ_CARTAO.CURRVAL, '111111111-11', sysdate);
INSERT INTO item VALUES(SEQ_ITEM.NEXTVAL, SEQ_COMPRA.CURRVAL, 'Mouse', 100.00, 10);

INSERT INTO cliente VALUES(SEQ_CLIENTE.NEXTVAL, '08214256700', 'Ana Maria', DEFAULT);
INSERT INTO endereco VALUES(SEQ_ENDERECO.NEXTVAL, SEQ_CLIENTE.CURRVAL, 'São Paulo', 'Rua Qualquer', 'SP', 'Brasil', '50000-000');
INSERT INTO contato VALUES(SEQ_CONTATO.NEXTVAL, SEQ_CLIENTE.CURRVAL, '71987456549','ana@exemplo.com');
INSERT INTO conta VALUES(SEQ_CONTA.NEXTVAL, SEQ_CLIENTE.CURRVAL, '123456', 4444, 500.00, 600.00, DEFAULT);
INSERT INTO cartao VALUES(SEQ_CARTAO.NEXTVAL, SEQ_CONTA.CURRVAL, sysdate, 501, 1, TO_DATE('01-02-2025', 'dd-mm-yyyy'), null, DEFAULT);
INSERT INTO cartao VALUES(SEQ_CARTAO.NEXTVAL, SEQ_CONTA.CURRVAL, sysdate, 301, 2, TO_DATE('01-02-2025', 'dd-mm-yyyy'), 1000, DEFAULT);
INSERT INTO compra VALUES(SEQ_COMPRA.NEXTVAL, SEQ_CARTAO.CURRVAL, '222222222-22', sysdate);
INSERT INTO item VALUES(SEQ_ITEM.NEXTVAL, SEQ_COMPRA.CURRVAL, 'Teclado', 200.00, 5);

INSERT INTO cliente VALUES(SEQ_CLIENTE.NEXTVAL, '09123546789', 'João Ferreira', DEFAULT);
INSERT INTO endereco VALUES(SEQ_ENDERECO.NEXTVAL, SEQ_CLIENTE.CURRVAL, 'Belo Horizonte', 'Rua Qualquer Outra', 'MG', 'Brasil', '40000-000');
INSERT INTO contato VALUES(SEQ_CONTATO.NEXTVAL, SEQ_CLIENTE.CURRVAL, '71987456549','joao@exemplo.com');
INSERT INTO conta VALUES(SEQ_CONTA.NEXTVAL, SEQ_CLIENTE.CURRVAL, '123456', 9999, 700.00, 800.00, DEFAULT);
INSERT INTO cartao VALUES(SEQ_CARTAO.NEXTVAL, SEQ_CONTA.CURRVAL, sysdate, 801, 1, TO_DATE('01-02-2025', 'dd-mm-yyyy'), null, DEFAULT);
INSERT INTO cartao VALUES(SEQ_CARTAO.NEXTVAL, SEQ_CONTA.CURRVAL, sysdate, 701, 2, TO_DATE('01-02-2025', 'dd-mm-yyyy'), 1000, DEFAULT);
INSERT INTO compra VALUES(SEQ_COMPRA.NEXTVAL, SEQ_CARTAO.CURRVAL, '333333333-33', sysdate);
INSERT INTO item VALUES(SEQ_ITEM.NEXTVAL, SEQ_COMPRA.CURRVAL, 'Monitor', 300.00, 30);

INSERT INTO cliente VALUES(SEQ_CLIENTE.NEXTVAL, '09998765432', 'Lucas Almeida', DEFAULT);
INSERT INTO endereco VALUES(SEQ_ENDERECO.NEXTVAL, SEQ_CLIENTE.CURRVAL, 'Salvador', 'Rua Outra', 'BA', 'Brasil', '30000-000');
INSERT INTO contato VALUES(SEQ_CONTATO.NEXTVAL, SEQ_CLIENTE.CURRVAL, '71987456549','lucas@exemplo.com');
INSERT INTO conta VALUES(SEQ_CONTA.NEXTVAL, SEQ_CLIENTE.CURRVAL, '123456', 5555, 900.00, 1000.00, DEFAULT);
INSERT INTO cartao VALUES(SEQ_CARTAO.NEXTVAL, SEQ_CONTA.CURRVAL, sysdate, 902, 1, TO_DATE('01-02-2025', 'dd-mm-yyyy'), null, DEFAULT);
INSERT INTO cartao VALUES(SEQ_CARTAO.NEXTVAL, SEQ_CONTA.CURRVAL, sysdate, 802, 2, TO_DATE('01-02-2025', 'dd-mm-yyyy'), 1000, DEFAULT);
INSERT INTO compra VALUES(SEQ_COMPRA.NEXTVAL, SEQ_CARTAO.CURRVAL, '444444444-44', sysdate);
INSERT INTO item VALUES(SEQ_ITEM.NEXTVAL, SEQ_COMPRA.CURRVAL, 'Headset', 400.00, 8);

INSERT INTO cliente VALUES(SEQ_CLIENTE.NEXTVAL, '05678912345', 'Fernanda Santos', DEFAULT);
INSERT INTO endereco VALUES(SEQ_ENDERECO.NEXTVAL, SEQ_CLIENTE.CURRVAL, 'Fortaleza', 'Rua Mais Uma', 'CE', 'Brasil', '20000-000');
INSERT INTO contato VALUES(SEQ_CONTATO.NEXTVAL, SEQ_CLIENTE.CURRVAL, '71987456549','fernanda@exemplo.com');
INSERT INTO conta VALUES(SEQ_CONTA.NEXTVAL, SEQ_CLIENTE.CURRVAL, '123456', 7777, 1100.00, 1200.00, DEFAULT);
INSERT INTO cartao VALUES(SEQ_CARTAO.NEXTVAL, SEQ_CONTA.CURRVAL, sysdate, 906, 1, TO_DATE('01-02-2025', 'dd-mm-yyyy'), null, DEFAULT);
INSERT INTO cartao VALUES(SEQ_CARTAO.NEXTVAL, SEQ_CONTA.CURRVAL, sysdate, 903, 2, TO_DATE('01-02-2025', 'dd-mm-yyyy'), 1000, DEFAULT);
INSERT INTO compra VALUES(SEQ_COMPRA.NEXTVAL, SEQ_CARTAO.CURRVAL, '555555555-55', sysdate);
INSERT INTO item VALUES(SEQ_ITEM.NEXTVAL, SEQ_COMPRA.CURRVAL, 'Cadeira', 500.00, 3);

INSERT INTO transferencia VALUES(SEQ_TRANSFERENCIA.NEXTVAL, 100000, 100003, 100);
INSERT INTO transferencia VALUES(SEQ_TRANSFERENCIA.NEXTVAL, 100002, 100001, 100);
INSERT INTO transferencia VALUES(SEQ_TRANSFERENCIA.NEXTVAL, 100003, 100004, 100);
INSERT INTO transferencia VALUES(SEQ_TRANSFERENCIA.NEXTVAL, 100000, 100004, 100);