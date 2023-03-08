package br.com.dbc.vemser.financeiro.repository;

import br.com.dbc.vemser.financeiro.exception.BancoDeDadosException;

import java.sql.Connection;
import java.util.List;

public interface Repositorio<OBJECT> {

    Object getProximoId(Connection connection) throws BancoDeDadosException;

    OBJECT adicionar(OBJECT object) throws BancoDeDadosException;

    List<OBJECT> listar() throws BancoDeDadosException;

}
