package entities.repository;

import entities.exception.BancoDeDadosException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface Repository<CHAVE, OBJECT> {

    Integer getProximoId(Connection connection) throws SQLException;

    Object adicionar(Object object) throws BancoDeDadosException;

    boolean remover(CHAVE id) throws BancoDeDadosException;

    boolean editar(CHAVE id, Object objeto) throws BancoDeDadosException;

    List<Object> listar() throws BancoDeDadosException;

}
