package br.com.dbc.vemser.financeiro.repository;


import br.com.dbc.vemser.financeiro.dto.CartaoCreateDTO;
import br.com.dbc.vemser.financeiro.dto.CartaoDTO;
import br.com.dbc.vemser.financeiro.exception.BancoDeDadosException;
import br.com.dbc.vemser.financeiro.exception.RegraDeNegocioException;
import br.com.dbc.vemser.financeiro.model.*;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CartaoRepository implements Repositorio<Cartao> {
    
    @Override
    public Long getProximoId(Connection connection) throws BancoDeDadosException {
        try {
            String sql = "SELECT SEQ_CARTAO.NEXTVAL mysequence from DUAL";
            Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery(sql);

            if (res.next()) {
                return res.getLong("mysequence");
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

            cartao.setNumeroCartao(getProximoId(con));
            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO");
            sql.append(" cartao (NUMERO_CARTAO, NUMERO_CONTA, DATA_EXPEDICAO, CODIGO_SEGURANCA, TIPO, VENCIMENTO");

            if(cartao.getClass() == CartaoDeDebito.class){
                sql.append(")");
                sql.append(" VALUES(?, ?, ?, ?, ?, ?)");
            }

            if(cartao.getClass() == CartaoDeCredito.class){
                sql.append(", LIMITE)");
                sql.append(" VALUES(?, ?, ?, ?, ?, ?, ?)");
            }

            PreparedStatement stmt = con.prepareStatement(sql.toString());
            stmt.setLong(1, cartao.getNumeroCartao());
            stmt.setInt(2, cartao.getNumeroConta());
            stmt.setDate(3, Date.valueOf(cartao.getDataExpedicao()));
            stmt.setInt(4, cartao.getCodigoSeguranca());
            stmt.setInt(5, cartao.getTipo().getTipo());
            stmt.setDate(6, Date.valueOf(cartao.getVencimento()));

            if(cartao.getClass().equals(CartaoDeCredito.class)){
                stmt.setDouble(7, ((CartaoDeCredito) cartao).getLimite());
            }

            // Executar consulta
            stmt.executeUpdate();
            return cartao;
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

    public boolean remover(Long numeroCartao) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            String sql = "UPDATE CARTAO SET STATUS = 0 WHERE NUMERO_CARTAO = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setLong(1, numeroCartao);

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

    public Cartao editar(Long numeroCartao, Cartao cartao) throws BancoDeDadosException, RegraDeNegocioException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            String sql = """
                    UPDATE cartao 
                    SET codigo_seguranca = ?
                    WHERE numero_cartao = ?
                    """;

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, cartao.getCodigoSeguranca());
            stmt.setLong(2, numeroCartao);

            stmt.executeUpdate();

            return getPorNumeroCartao(numeroCartao);


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

    public List<Cartao> listarPorNumeroConta(Integer numeroConta) throws BancoDeDadosException{
        List<Cartao> cartoes = new ArrayList<>();
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            String sql = """
                    SELECT *  FROM CARTAO c
                    WHERE c.NUMERO_CONTA = ?
                    AND STATUS = 1
                    """;

            // Executa-se a consulta
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, numeroConta);
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

    public Cartao getPorNumeroCartao(Long numeroCartao) throws BancoDeDadosException, RegraDeNegocioException{
        List<Cartao> cartoes = new ArrayList<>();
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            String sql = """
                    SELECT *  FROM CARTAO c
                    WHERE c.NUMERO_CARTAO = ?
                    AND STATUS = 1
                    """;

            // Executa-se a consulta
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setLong(1, numeroCartao);
            ResultSet res = stmt.executeQuery();

            while (res.next()) {
                Cartao cartao = getCartaoFromResultSet(res);
                cartoes.add(cartao);
            }

            Cartao result = cartoes.stream()
                    .findFirst()
                    .orElseThrow(()-> new RegraDeNegocioException("Cartão não encontrado!"));

            return result;

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
            cartaoDeDebito.setNumeroCartao(res.getLong("NUMERO_CARTAO"));
            cartaoDeDebito.setNumeroConta(res.getInt("NUMERO_CONTA"));
            cartaoDeDebito.setDataExpedicao(res.getDate("DATA_EXPEDICAO").toLocalDate());
            cartaoDeDebito.setCodigoSeguranca(res.getInt("CODIGO_SEGURANCA"));
            cartaoDeDebito.setTipo(TipoCartao.getTipoCartao(res.getInt("TIPO")));
            cartaoDeDebito.setVencimento(res.getDate("VENCIMENTO").toLocalDate());
            cartaoDeDebito.setStatus(Status.getTipoStatus(res.getInt("STATUS")));
            return cartaoDeDebito;
        } else{
            CartaoDeCredito cartaoDeCredito = new CartaoDeCredito();
            cartaoDeCredito.setNumeroCartao(res.getLong("NUMERO_CARTAO"));
            cartaoDeCredito.setNumeroConta(res.getInt("NUMERO_CONTA"));
            cartaoDeCredito.setDataExpedicao(res.getDate("DATA_EXPEDICAO").toLocalDate());
            cartaoDeCredito.setCodigoSeguranca(res.getInt("CODIGO_SEGURANCA"));
            cartaoDeCredito.setTipo(TipoCartao.getTipoCartao(res.getInt("TIPO")));
            cartaoDeCredito.setVencimento(res.getDate("VENCIMENTO").toLocalDate());
            cartaoDeCredito.setLimite(res.getDouble("LIMITE"));
            cartaoDeCredito.setStatus(Status.getTipoStatus(res.getInt("STATUS")));
            return cartaoDeCredito;
        }
    }

}
