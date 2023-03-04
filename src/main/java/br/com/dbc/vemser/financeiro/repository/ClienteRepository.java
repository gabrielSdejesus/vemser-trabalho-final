package br.com.dbc.vemser.financeiro.repository;

import br.com.dbc.vemser.financeiro.exception.BancoDeDadosException;
import br.com.dbc.vemser.financeiro.model.Cliente;
import br.com.dbc.vemser.financeiro.model.Status;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ClienteRepository implements Repositorio<Cliente> {

    @Override
    public Integer getProximoId(Connection connection) throws BancoDeDadosException {
        try {
            String sql = "SELECT SEQ_CLIENTE.NEXTVAL mysequence FROM DUAL";
            Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery(sql);
            if(res.next()) {
                return res.getInt("mysequence");
            }
            return null;

        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        }
    }

    @Override
    public Cliente adicionar(Cliente cliente) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            Integer proximoId = this.getProximoId(con);
            cliente.setIdCliente(proximoId);

            String sql = """
                    INSERT INTO cliente
                    (id_cliente, cpf_cliente, nome)
                    VALUES(?, ?, ?)
                    """;

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, cliente.getIdCliente());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getNome());

            // Executar consulta
            stmt.executeUpdate();
            return cliente;
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean remover(Integer id) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            String sql = "UPDATE CLIENTE SET STATUS = 0 WHERE ID_CLIENTE = ? ";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);

            // Executar consulta
            int res = stmt.executeUpdate();
            return res > 0;
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Cliente editar(Integer id, Cliente cliente) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE cliente SET \n");

            if (cliente.getCpf() != null) {
                sql.append(" CPF_CLIENTE = ?,");
            }
            if (cliente.getNome() != null) {
                sql.append(" NOME = ?,");
            }

            sql.deleteCharAt(sql.length() - 1);
            sql.append(" WHERE ID_CLIENTE = ? ");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            int index = 1;
            if(cliente.getNome() != null){
                stmt.setString(index++, cliente.getCpf());
            }

            if(cliente.getCpf() != null){
                stmt.setString(index++, cliente.getNome());
            }

            stmt.setInt(index, id);

            // Executar consulta
            stmt.executeUpdate();

            return consultarPorIdCliente(id);
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Cliente> listar() throws BancoDeDadosException {
        List<Cliente> clientes = new ArrayList<>();
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT * FROM cliente";

            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                Cliente cliente = getClienteFromResultSet(res);
                clientes.add(cliente);
            }
            return clientes;
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Cliente consultarPorIdCliente(Integer id) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();
            Cliente cliente = new Cliente();

            String sql = "SELECT * FROM CLIENTE WHERE ID_CLIENTE = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);


            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                cliente = getClienteFromResultSet(res);
            }
            return cliente;
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private Cliente getClienteFromResultSet(ResultSet res) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setIdCliente(res.getInt("ID_CLIENTE"));
        cliente.setCpf(res.getString("CPF_CLIENTE"));
        cliente.setNome(res.getString("NOME"));
        cliente.setStatus(Status.getTipoStatus(res.getInt("STATUS")));
        return cliente;
    }
}
