package br.com.dbc.vemser.financeiro.repository;


import br.com.dbc.vemser.financeiro.exception.BancoDeDadosException;
import br.com.dbc.vemser.financeiro.model.Cliente;
import br.com.dbc.vemser.financeiro.model.Conta;
import br.com.dbc.vemser.financeiro.model.Status;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ContaRepository implements Repositorio<Conta> {

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

    public Conta consultarConta(Integer numeroConta, Integer idCliente) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();
            Conta conta = new Conta();

            String sql = """
                    SELECT * FROM CONTA c
                    LEFT JOIN CLIENTE c2 ON c.ID_CLIENTE = c2.ID_CLIENTE
                    WHERE c.numero_conta = ? AND c.id_cliente = ?
                     """;

            // Executa-se a consulta
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, numeroConta);
            stmt.setInt(2,idCliente);
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

    public Conta consultarNumeroConta(Integer numeroConta) throws BancoDeDadosException{
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();
            Conta conta = null;

            String sql = """
                    SELECT * FROM CONTA c
                    LEFT JOIN CLIENTE c2 ON c.ID_CLIENTE = c2.ID_CLIENTE
                    WHERE c.numero_conta = ?
                     """;

            // Executa-se a consulta
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, numeroConta);
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

    @Override
    public Conta adicionar(Conta conta) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            Integer proximoId = this.getProximoId(con);
            conta.setNumeroConta(proximoId);

            String sql = """
                    INSERT INTO conta
                    (numero_conta, id_cliente, senha, 
                        agencia, saldo, cheque_especial)
                    VALUES(?, ?, ?, ?, ?, ?)
                    """;

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, conta.getNumeroConta());
            stmt.setInt(2, conta.getCliente().getIdCliente());
            stmt.setString(3, conta.getSenha());
            stmt.setInt(4, conta.getAgencia());
            stmt.setDouble(5, conta.getSaldo());
            stmt.setDouble(6, conta.getChequeEspecial());

            // Executar consulta
            stmt.executeUpdate();

            return consultarConta(conta.getNumeroConta(), conta.getCliente().getIdCliente());
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

    public boolean reativarConta(String cpf) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            String sql1 = "UPDATE CLIENTE C SET c.STATUS = 1 WHERE c.CPF_CLIENTE = ?";
            String sql = "UPDATE CONTA C SET C.STATUS = 1 WHERE c.ID_CLIENTE =\n" +
                    " (SELECT ID_CLIENTE FROM CLIENTE c2 WHERE c2.CPF_CLIENTE = ?)";
            String sql2 = "UPDATE CARTAO C SET C.STATUS = 1 WHERE c.NUMERO_CONTA =\n" +
                    " (SELECT NUMERO_CONTA FROM CONTA c2 WHERE c2.ID_CLIENTE =(SELECT ID_CLIENTE FROM CLIENTE c2 WHERE C2.CPF_CLIENTE = ?))";

            //reativar cliente
            PreparedStatement stmt = con.prepareStatement(sql1);
            stmt.setString(1, cpf);
            int res = stmt.executeUpdate();

            //reativar conta
            stmt = con.prepareStatement(sql);
            stmt.setString(1, cpf);
            res += stmt.executeUpdate();

            //reativar cartões
            stmt = con.prepareStatement(sql2);
            stmt.setString(1, cpf);
            res += stmt.executeUpdate();

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

    public boolean remover(Integer numeroConta) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            String sql = "UPDATE conta SET status = 0 WHERE numero_conta = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, numeroConta);

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

    public Conta editar(Integer id, Conta conta) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE conta SET \n");

            if(conta.getSenha() != null){
                sql.append(" SENHA = ?,");
            }

            if(conta.getAgencia() != null && conta.getAgencia().toString().matches("\\d{4}")){
                sql.append(" AGENCIA = ?,");
            }

            if(conta.getSaldo() != null && conta.getSaldo() >= 0){
                sql.append(" SALDO = ?,");
            }

            if(conta.getChequeEspecial() != null && conta.getChequeEspecial() > 0){
                sql.append(" CHEQUE_ESPECIAL = ?,");
            }

            sql.deleteCharAt(sql.length() - 1); //remove o ultimo ','
            sql.append(" WHERE NUMERO_CONTA = ? ");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            int index = 1;
            if(conta.getSenha() != null){
                stmt.setString(index++, conta.getSenha());
            }

            if(conta.getAgencia().toString().matches("\\d{4}")){
                stmt.setInt(index++, conta.getAgencia());
            }

            if(conta.getSaldo() >= 0){
                stmt.setDouble(index++, conta.getSaldo());
            }

            if(conta.getChequeEspecial() > 0){
                stmt.setDouble(index++, conta.getChequeEspecial());
            }

            stmt.setInt(index, id);
            // Executar consulta
            stmt.executeUpdate();

            return consultarConta(conta.getNumeroConta(), conta.getCliente().getIdCliente());
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
        cliente.setStatus(Status.getTipoStatus(res.getInt("STATUS")));
        conta.setCliente(cliente);
        return conta;
    }

}
