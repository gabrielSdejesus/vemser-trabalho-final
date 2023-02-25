package entities.repository;

import entities.exception.BancoDeDadosException;

import java.sql.Connection;
import java.util.List;

public interface Repository<OBJECT> {

    Object getProximoId(Connection connection) throws BancoDeDadosException;

    OBJECT adicionar(OBJECT object) throws BancoDeDadosException;

    List<OBJECT> listar() throws BancoDeDadosException;

}
