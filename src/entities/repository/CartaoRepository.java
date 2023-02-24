package entities.repository;

import entities.exception.BancoDeDadosException;
import entities.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartaoRepository implements Repository<String, Cartao> {
    
    @Override
    public String getProximoId(Connection connection) throws BancoDeDadosException {
        try {
            String sql = "SELECT SEQ_CARTAO.NEXTVAL mysequence from DUAL";
            Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery(sql);

            if (res.next()) {
                return res.getString("mysequence");
            }

            return null;
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        }
    }

    @Override
    public Cartao adicionar(Cartao cartao) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            String proximoId = this.getProximoId(con);
            cartao.setNumeroCartao(String.valueOf(proximoId));

            String sql = """
                    INSERT INTO cartao\n 
                    VALUES(?, ?, ?, ?, ?, ?, ?)
                    """;

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, cartao.getNumeroCartao());
            stmt.setInt(2, cartao.getConta().getNumeroConta());
            stmt.setDate(3, Date.valueOf(cartao.getDataExpedicao()));
            stmt.setInt(4, cartao.getCodigoSeguranca());
            stmt.setInt(5, cartao.getTipo().getTipo());
            stmt.setDate(6, Date.valueOf(cartao.getVencimento()));

            if(cartao.getClass().equals(CartaoDeCredito.class)){
                stmt.setDouble(7, ((CartaoDeCredito) cartao).getLimite());
            } else {
                stmt.setString(7, null);
            }

            int res = stmt.executeUpdate();
            System.out.println("adicionarCartao.res=" + res);
            return cartao;
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
    public boolean remover(String id) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            String sql = "DELETE FROM cartao WHERE NUMERO_CARTAO = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, id);

            // Executa-se a consulta
            int res = stmt.executeUpdate();
            System.out.println("removerCartaoPorId.res=" + res);

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
    public boolean editar(String id, Cartao cartao) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();


            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE cartao SET \n");

            if(cartao.getCodigoSeguranca() > 99){
                sql.append(" CODIGO_SEGURANCA = ?,");
            }

            if(cartao.getTipo() != null){
                sql.append(" TIPO = ?,");
            }

            if(cartao.getVencimento() != null){
                sql.append(" VENCIMENTO = ?,");
            }

            if(cartao.getClass().equals(CartaoDeCredito.class)
                    && ((CartaoDeCredito) cartao).getLimite() > 0){
                sql.append(" LIMITE = ?,");
            }

            sql.deleteCharAt(sql.length() - 1); //remove o ultimo ','
            sql.append(" WHERE numero_cartao = ? ");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            int index = 1;
            if(cartao.getCodigoSeguranca() > 99){
                stmt.setInt(index++, cartao.getCodigoSeguranca());
            }

            if(cartao.getTipo() != null){
                stmt.setInt(index++, cartao.getTipo().getTipo());
            }

            if(cartao.getVencimento() != null){
                stmt.setDate(index++, Date.valueOf(cartao.getVencimento()));
            }

            if(cartao.getClass().equals(CartaoDeCredito.class)
                    && ((CartaoDeCredito) cartao).getLimite() > 0){
                stmt.setDouble(index++, ((CartaoDeCredito) cartao).getLimite());
            }

            stmt.setString(index, id);

            // Executa-se a consulta
            int res = stmt.executeUpdate();
            System.out.println("editarCartao.res=" + res);

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
    public List<Cartao> listar() throws BancoDeDadosException {
        List<Cartao> cartoes = new ArrayList<>();
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = """
                    SELECT * FROM CARTAO c
                    """;

            // Executa-se a consulta
            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                Cartao cartao = getCartaoFromResultSet(res);
                cartoes.add(cartao);
            }
            return cartoes;
        } catch (SQLException e) {
            e.printStackTrace();
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

    public List<Cartao> listarCartoesPorNumeroConta(Conta conta) throws BancoDeDadosException{
        List<Cartao> cartoes = new ArrayList<>();
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            String sql = """
                    SELECT *  FROM CARTAO c\n
                    WHERE c.NUMERO_CONTA = ?
                    """;

            // Executa-se a consulta
            PreparedStatement stmt = con.prepareStatement(sql);

            if(conta != null){
                stmt.setInt(1, conta.getNumeroConta());
            }

            ResultSet res = stmt.executeQuery();

            while (res.next()) {
                Cartao cartao = getCartaoFromResultSet(res);
                cartoes.add(cartao);
            }
            return cartoes;
        } catch (SQLException e) {
            e.printStackTrace();
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

    public Cartao getCartaoFromResultSet(ResultSet res) throws SQLException {

        if (res.getInt("TIPO") == 1) {
            CartaoDeDebito cartaoDeDebito = new CartaoDeDebito();
            cartaoDeDebito.setNumeroCartao(res.getString("NUMERO_CARTAO"));
            Conta conta = new Conta();
            conta.setNumeroConta(res.getInt("NUMERO_CONTA"));
            cartaoDeDebito.setConta(conta);
            cartaoDeDebito.setDataExpedicao(res.getDate("DATA_EXPEDICAO").toLocalDate());
            cartaoDeDebito.setCodigoSeguranca(res.getInt("CODIGO_SEGURANCA"));
            cartaoDeDebito.setTipo(TipoCartao.getTipoCartao(res.getInt("TIPO")));
            cartaoDeDebito.setVencimento(res.getDate("VENCIMENTO").toLocalDate());
            return cartaoDeDebito;
        } else{
            CartaoDeCredito cartaoDeCredito = new CartaoDeCredito();
            cartaoDeCredito.setNumeroCartao(res.getString("NUMERO_CARTAO"));
            Conta conta = new Conta();
            conta.setNumeroConta(res.getInt("NUMERO_CONTA"));
            cartaoDeCredito.setConta(conta);
            cartaoDeCredito.setDataExpedicao(res.getDate("DATA_EXPEDICAO").toLocalDate());
            cartaoDeCredito.setCodigoSeguranca(res.getInt("CODIGO_SEGURANCA"));
            cartaoDeCredito.setTipo(TipoCartao.getTipoCartao(res.getInt("TIPO")));
            cartaoDeCredito.setVencimento(res.getDate("VENCIMENTO").toLocalDate());
            cartaoDeCredito.setLimite(res.getDouble("LIMITE"));
            return cartaoDeCredito;
        }
    }

}
