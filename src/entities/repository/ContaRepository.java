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

            String sql = "INSERT INTO CONTA\n" +
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

            String sql = "DELETE FROM cartao WHERE NUMERO_CONTA = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, numeroConta);
            int res = stmt.executeUpdate();

            sql = "DELETE FROM transferencia WHERE NUMERO_CONTA_ENVIOU = ? OR NUMERO_CONTA_RECEBEU = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, numeroConta);
            stmt.setInt(2, numeroConta);
            res = stmt.executeUpdate();

            sql = "DELETE FROM conta WHERE NUMERO_CONTA = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, numeroConta);
            res = stmt.executeUpdate();

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
    public boolean editar(Integer numeroConta, Conta conta) throws BancoDeDadosException {
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

            String sql = "SELECT * FROM CONTA c INNER JOIN CLIENTE c2 ON c.ID_CLIENTE = c2.ID_CLIENTE";

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

    public Conta consultarPorNumeroConta(Integer numeroConta) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();
            String sql = "SELECT numero_conta FROM CONTA WHERE numero_conta = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, numeroConta);

            ResultSet res = stmt.executeQuery(sql);
            return getContaFromResultSet(res);
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
        conta.setNumeroConta(res.getInt("NUMERO_CONTA"));
        conta.setSenha(res.getString("SENHA"));
        conta.setAgencia(res.getInt("AGENCIA"));
        conta.setSaldo(res.getDouble("SALDO"));
        conta.setChequeEspecial(res.getDouble("CHEQUE_ESPECIAL"));

        Cliente cliente = new Cliente();
        cliente.setIdCliente(res.getInt("ID_CLIENTE"));
        cliente.setCpf(res.getString("CPF_CLIENTE"));
        cliente.setNome(res.getString("NOME"));

        conta.setIdCliente(cliente);

        return conta;
    }

}
