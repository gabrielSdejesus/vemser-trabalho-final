package entities.repository;

import entities.exception.BancoDeDadosException;
import entities.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContaRepository implements Repository<Integer, Conta> {
    @Override
    public Integer getProximoId(Connection connection) throws BancoDeDadosException {
        try {
            String sql = "SELECT SEQ_CONTA.NEXTVAL mysequence FROM DUAL";
            Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery(sql);

            if (res.next()) {
                return res.getInt("mysequence");
            }

            return null;
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        }
    }

    @Override
    public Conta adicionar(Conta conta) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            Integer proximoId = this.getProximoId(con);
            conta.setNumeroConta(proximoId);

            String sql = """
                    INSERT INTO conta\n 
                    VALUES(?, ?, ?, ?, ?, ?, ?)
                    """;

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, conta.getNumeroConta());
            stmt.setInt(2, conta.getCliente().getIdCliente());
            stmt.setString(3, conta.getSenha());
            stmt.setInt(4, conta.getAgencia());
            stmt.setDouble(5, conta.getSaldo());
            stmt.setDouble(6, conta.getChequeEspecial());
            stmt.setInt(7, conta.getStatus().getStatus());

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

    public boolean reativarConta(Integer id) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            String sql = "UPDATE conta SET status = 1 WHERE numero_conta = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);

            // Executa-se a consulta
            int res = stmt.executeUpdate();
            System.out.println("removerClientePorId.res=" + res);

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

    public boolean remover(Integer id) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            String sql = "UPDATE conta SET status = 0 WHERE numero_conta = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);

            // Executa-se a consulta
            int res = stmt.executeUpdate();
            System.out.println("removerClientePorId.res=" + res);

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

    public boolean editar(Integer id, Conta conta) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE conta SET \n");

            if(conta.getSenha() != null){
                sql.append(" SENHA = ?,");
            }

            if(conta.getAgencia().toString().matches("\\d{4}")){
                sql.append(" AGENCIA = ?,");
            }

            if(conta.getSaldo() > 0){
                sql.append(" SALDO = ?,");
            }

            if(conta.getChequeEspecial() > 0){
                sql.append(" CHEQUE_ESPECIAL = ?,");
            }

            sql.deleteCharAt(sql.length() - 1); //remove o ultimo ','
            sql.append(" WHERE NUMERO_CONTA = ? ");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            int index = 1;
            if(conta.getSenha() != null){
                stmt.setString(1, conta.getSenha());
            }

            if(conta.getAgencia().toString().matches("\\d{4}")){
                stmt.setInt(2, conta.getAgencia());
            }

            if(conta.getSaldo() > 0){
                stmt.setDouble(3, conta.getSaldo());
            }

            if(conta.getChequeEspecial() > 0){
                stmt.setDouble(4, conta.getChequeEspecial());
            }

            stmt.setInt(index, id);

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

            String sql = "SELECT * FROM CONTA c LEFT JOIN CLIENTE c2 ON c.ID_CLIENTE = c2.ID_CLIENTE";

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

    public Conta consultarPorNumeroConta(Integer id) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();
            Conta conta = new Conta();

            String sql = "SELECT * FROM CONTA c\n " +
                    " LEFT JOIN CLIENTE c2 ON c.ID_CLIENTE = c2.ID_CLIENTE\n" +
                    " WHERE numero_conta = ? AND c.STATUS = 1";

            // Executa-se a consulta
            PreparedStatement stmt = con.prepareStatement(sql);


            stmt.setInt(1, id);


            ResultSet res = stmt.executeQuery();

            while (res.next()) {
                conta = getContaFromResultSet(res);
            }

            return conta;
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
        conta.setStatus(Status.getTipoStatus(res.getInt("STATUS")));
        Cliente cliente = new Cliente();
        cliente.setIdCliente(res.getInt("ID_CLIENTE"));
        cliente.setCpf(res.getString("CPF_CLIENTE"));
        cliente.setNome(res.getString("NOME"));
        conta.setCliente(cliente);
        return conta;
    }

}
