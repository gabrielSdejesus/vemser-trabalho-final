package br.com.dbc.vemser.financeiro.repository;

import br.com.dbc.vemser.financeiro.exception.BancoDeDadosException;
import br.com.dbc.vemser.financeiro.model.*;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CompraRepository implements Repositorio<Compra> {

    @Override
    public Integer getProximoId(Connection connection) throws BancoDeDadosException {
        try {
            String sql = "SELECT SEQ_COMPRA.NEXTVAL mysequence from DUAL";
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
    public Compra adicionar(Compra compra) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            Integer proximoId = this.getProximoId(con);
            compra.setIdCompra(proximoId);

            String sql = """
                    INSERT INTO compra
                    (id_compra, numero_cartao, doc_vendedor, data)
                    VALUES(?,?,?,?)
                    """;

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, compra.getIdCompra());
            stmt.setLong(2, compra.getNumeroCartao());
            stmt.setString(3, compra.getDocVendedor());
            stmt.setDate(4, Date.valueOf(compra.getData()));

            // Executar consulta
            stmt.executeUpdate();
            return compra;
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
    public List<Compra> listar() throws BancoDeDadosException {
        List<Compra> compras = new ArrayList<>();
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = """
                        SELECT c.*, c4.NOME, c3.NUMERO_CONTA FROM COMPRA c
                        INNER JOIN CARTAO c2 ON c.NUMERO_CARTAO = c2.NUMERO_CARTAO
                        INNER JOIN CONTA c3 ON c3.NUMERO_CONTA  = c2.NUMERO_CONTA
                        INNER JOIN CLIENTE c4 ON c4.ID_CLIENTE  = c3.ID_CLIENTE
                    """;

            // Executa-se a consulta
            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                Compra compra = getCompraFromResultSet(res);
                compras.add(compra);
            }
            return compras;
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

    public List<Compra> listarPorCartao(Long numeroCartao) throws BancoDeDadosException {
        List<Compra> compras = new ArrayList<>();
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            String sql = """
                        SELECT c.*, c4.NOME, c3.NUMERO_CONTA FROM COMPRA c
                        INNER JOIN CARTAO c2 ON c.NUMERO_CARTAO = c2.NUMERO_CARTAO
                        INNER JOIN CONTA c3 ON c3.NUMERO_CONTA  = c2.NUMERO_CONTA
                        INNER JOIN CLIENTE c4 ON c4.ID_CLIENTE  = c3.ID_CLIENTE
                        WHERE c.NUMERO_CARTAO = ?
                    """;

            // Executa-se a consulta
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setLong(1, numeroCartao);

            ResultSet res = stmt.executeQuery();

            while (res.next()) {
                Compra compra = getCompraFromResultSet(res);
                compras.add(compra);
            }
            return compras;
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

    private Compra getCompraFromResultSet(ResultSet res) throws SQLException {
        Compra compra = new Compra();
        compra.setIdCompra(res.getInt("id_compra"));
        compra.setData(LocalDate.parse(res.getDate("data").toString()));
        compra.setDocVendedor(res.getString("doc_vendedor"));
        compra.setNumeroCartao(res.getLong("numero_cartao"));
        return compra;
    }
}
