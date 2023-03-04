package br.com.dbc.vemser.financeiro.repository;


import br.com.dbc.vemser.financeiro.exception.BancoDeDadosException;
import br.com.dbc.vemser.financeiro.model.Compra;
import br.com.dbc.vemser.financeiro.model.Item;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ItemRepository implements Repositorio<Item> {

    @Override
    public Integer getProximoId(Connection connection) throws BancoDeDadosException {
        try {
            String sql = "SELECT SEQ_ITEM.NEXTVAL mysequence from DUAL";
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

    public Integer getUltimoIdCompra(Connection connection) throws SQLException {
        try {
            String sql = "SELECT MAX(ID_COMPRA) FROM COMPRA";
            Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery(sql);

            if (res.next()) {
                return res.getInt("MAX(ID_COMPRA)");
            }

            return null;
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        }
    }

    @Override
    public Item adicionar(Item item) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            Integer proximoId = this.getProximoId(con);
            item.setIdItem(proximoId);

            String sql = """
                    INSERT INTO item
                    VALUES(?, ?, ?, ?, ?)
                    """;

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, item.getIdItem());
            stmt.setInt(2, this.getUltimoIdCompra(con));
            stmt.setString(3, item.getNome());
            stmt.setDouble(4, item.getValor());
            stmt.setInt(5, item.getQuantidade());

            // Executar consulta
            stmt.executeUpdate();
            return item;

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
    public List<Item> listar() throws BancoDeDadosException {
        List<Item> itens = new ArrayList<>();
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = """
                    SELECT i.*, c.NUMERO_CARTAO, c.DOC_VENDEDOR, c.DATA FROM ITEM i
                    LEFT JOIN COMPRA c ON i.ID_ITEM = c.ID_COMPRA
                    """;

            // Executa-se a consulta
            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                Item item = getItemFromResultSet(res);
                itens.add(item);
            }
            return itens;
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

    public List<Item> listarItensPorIdCompra(Integer idCompra) throws BancoDeDadosException {
        List<Item> itens = new ArrayList<>();
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            String sql = "SELECT * FROM item WHERE id_compra = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, idCompra);
            ResultSet res = stmt.executeQuery();

            while(res.next()) {
                Item item = getItemFromResultSet(res);
                itens.add(item);
            }
            return itens;

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

    public Item retornarItem(Integer idItem) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            String sql = "SELECT i.* FROM ITEM i WHERE i.id_item = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, idItem);
            ResultSet res = stmt.executeQuery();

            return getItemFromResultSet(res);

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

    private Item getItemFromResultSet(ResultSet res) throws SQLException {
        Item item = new Item();
        item.setIdItem(res.getInt("id_item"));
        item.setNome(res.getString("nome"));
        item.setValor(res.getDouble("valor"));
        item.setQuantidade(res.getInt("quantidade"));
        Compra compra = new Compra();
        compra.setIdCompra(res.getInt("id_compra"));
        item.setCompra(compra);
        return item;
    }
}
