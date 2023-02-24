package entities.repository;

import entities.exception.BancoDeDadosException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface Repository<CHAVE, OBJECT> {

    Object getProximoId(Connection connection) throws BancoDeDadosException;

    OBJECT adicionar(OBJECT object) throws BancoDeDadosException;

    List<OBJECT> listar() throws BancoDeDadosException;

}
