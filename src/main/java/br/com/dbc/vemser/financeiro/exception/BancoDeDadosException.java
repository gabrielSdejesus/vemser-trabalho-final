package br.com.dbc.vemser.financeiro.exception;

import java.sql.SQLException;

public class BancoDeDadosException extends SQLException{

    public BancoDeDadosException(Throwable cause) {
            super(cause);
        }
}
