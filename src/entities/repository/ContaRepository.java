package entities.repository;

import entities.exception.BancoDeDadosException;
import entities.model.Cliente;
import entities.model.Conta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContaRepository implements Repository<Integer, Conta> {
    @Override
    public Integer getProximoId(Connection connection) throws BancoDeDadosException {
        try {
            String sql = "SELECT seq_conta mysequence FROM DUAL";
            Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery(sql);
            if(res.next()) {
                return res.getInt("mysequence");
            }
            return null;

        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new BancoDeDadosException(e.getCause());
            }
        }
    }

    @Override
    public Conta adicionar(Conta conta) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            Integer nextID = getProximoId(con);

            String sql = "INSERT INTO CONTATO\n" +
                    "(NUMERO_CONTA, ID_CLIENTE, AGENCIA, SALDO, CHEQUE_ESPECIAL)\n" +
                    "VALUES(?, ?, ?, ?, ?)\n";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, conta.getNumeroConta());
            stmt.setInt(2, conta.getIdCliente().getIdCliente());
            stmt.setString(3, conta.getSenha());
            stmt.setInt(4, conta.getAgencia());
            stmt.setDouble(4, conta.getSaldo());
            stmt.setDouble(4, conta.getChequeEspecial());

            int res = stmt.executeUpdate();
            System.out.println("adicionarConta.res=" + res);
            return conta;

        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                throw new BancoDeDadosException(e.getCause());
            }
        }
    }

    @Override
    public boolean remover(Integer numeroConta) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            String sql = "DELETE FROM CONTA WHERE NUMERO_CONTA = ?";
            PreparedStatement stmt = con.prepareStatement(sql);

            int res = stmt.executeUpdate();
            System.out.println("removerContaPorNumeroConta.res=" + res);
            return res > 0;
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                throw new BancoDeDadosException(e.getCause());
            }
        }
    }

    @Override
    public boolean editar(Integer id, Conta conta) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE conta SET \n");

            if (conta.getSenha() != null) {
                sql.append(" SENHA = ?,");
            }
            if (conta.getChequeEspecial() != 0.0) {
                sql.append(" CHEQUE_ESPECIAL = ?,");
            }

            sql.deleteCharAt(sql.length() - 1); //remove o ultimo ','
            sql.append(" WHERE NUMERO_CONTA = ? ");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            stmt.setString(1, conta.getSenha());
            stmt.setDouble(2, conta.getChequeEspecial());
            stmt.setInt(3, conta.getNumeroConta());

            // Executa-se a consulta
            int res = stmt.executeUpdate();
            System.out.println("editarConta.res=" + res);

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

    @Override
    public List<Conta> listar() throws BancoDeDadosException {
        List<Conta> contas = new ArrayList<>();
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT c.\"numero_conta\", c.\"agencia\", c2.\"nome\"  FROM FINANCEIRO.\"Conta\" c " +
                    "INNER JOIN \"Cliente\" c2 ON c2.\"id_cliente\" = c.\"id_cliente\"";

            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                Conta conta = getContaFromResultSet(res);
                contas.add(conta);
            }
            return contas;
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

    private Conta getContaFromResultSet(ResultSet res) throws SQLException {
        Conta conta = new Conta();
        conta.setNumeroConta(res.getInt("numero_conta"));
        conta.setAgencia(res.getInt("agencia"));
        Cliente cliente = new Cliente();
        cliente.setNome(res.getString("nome"));
        conta.setIdCliente(cliente);
        return conta;
    }

}
