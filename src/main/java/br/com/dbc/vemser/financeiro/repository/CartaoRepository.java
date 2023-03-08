package br.com.dbc.vemser.financeiro.repository;


import br.com.dbc.vemser.financeiro.exception.BancoDeDadosException;
import br.com.dbc.vemser.financeiro.exception.RegraDeNegocioException;
import br.com.dbc.vemser.financeiro.model.*;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
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
            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE cartao SET \n");

            if(cartao.getCodigoSeguranca() != null){
                sql.append(" CODIGO_SEGURANCA = ?,");
            }

            if(cartao instanceof CartaoDeCredito){
                if(((CartaoDeCredito) cartao).getLimite() != 1000){
                    sql.append(" LIMITE = ?,");
                }
            }

            sql.deleteCharAt(sql.length() -1);
            sql.append(" WHERE NUMERO_CARTAO = ?");
            PreparedStatement stmt = con.prepareStatement(sql.toString());

            int index = 1;
            if(cartao.getCodigoSeguranca() != null){
                stmt.setInt(index++, cartao.getCodigoSeguranca());
            }

            if(cartao instanceof CartaoDeCredito){
                if(((CartaoDeCredito) cartao).getLimite() != 1000){
                    stmt.setDouble(index++, ((CartaoDeCredito) cartao).getLimite());;
                }
            }

            stmt.setLong(index, numeroCartao);

            //Executar consulta
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

            return cartoes.stream()
                    .findFirst()
                    .orElseThrow(()-> new RegraDeNegocioException("Cartão não encontrado!"));

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
        Cartao cartao = new Cartao() {
            @Override
            public void setNumeroCartao(Long numeroCartao) {
                super.setNumeroCartao(numeroCartao);
            }

            @Override
            public void setNumeroConta(Integer numeroConta) {
                super.setNumeroConta(numeroConta);
            }

            @Override
            public void setDataExpedicao(LocalDate dataExpedicao) {
                super.setDataExpedicao(dataExpedicao);
            }

            @Override
            public void setCodigoSeguranca(Integer codigoSeguranca) {
                super.setCodigoSeguranca(codigoSeguranca);
            }

            @Override
            public void setTipo(TipoCartao tipo) {
                super.setTipo(tipo);
            }

            @Override
            public void setVencimento(LocalDate vencimento) {
                super.setVencimento(vencimento);
            }

            @Override
            public void setStatus(Status status) {
                super.setStatus(status);
            }
        };
        cartao.setNumeroCartao(res.getLong("NUMERO_CARTAO"));
        cartao.setNumeroConta(res.getInt("NUMERO_CONTA"));
        cartao.setDataExpedicao(res.getDate("DATA_EXPEDICAO").toLocalDate());
        cartao.setCodigoSeguranca(res.getInt("CODIGO_SEGURANCA"));
        cartao.setTipo(TipoCartao.getTipoCartao(res.getInt("TIPO")));
        cartao.setVencimento(res.getDate("VENCIMENTO").toLocalDate());
        cartao.setStatus(Status.getTipoStatus(res.getInt("STATUS")));

        if (cartao.getTipo().equals(TipoCartao.DEBITO)) {
            return cartao;
        } else{
            CartaoDeCredito cartaoDeCredito = (CartaoDeCredito) cartao;
            cartaoDeCredito.setLimite(res.getDouble("LIMITE"));
            return cartaoDeCredito;
        }
    }

}
