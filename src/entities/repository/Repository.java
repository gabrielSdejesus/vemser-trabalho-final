package entities.repository;

import entities.exception.BancoDeDadosException;
import entities.model.Endereco;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface Repository<CHAVE, OBJECT> {

    Integer getProximoId(Connection connection) throws SQLException;

    OBJECT adicionar(OBJECT object) throws BancoDeDadosException;

    boolean remover(CHAVE id) throws BancoDeDadosException;

    boolean editar(CHAVE id, OBJECT objeto) throws BancoDeDadosException;

    List<OBJECT> listar() throws BancoDeDadosException;

}
