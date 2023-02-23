package entities.repository;

import entities.exception.BancoDeDadosException;
import entities.model.Cliente;
import entities.model.Compra;
import entities.model.Item;

import java.sql.*;
import java.util.List;

public class ItemRepository implements Repository<Integer, Item>{

    @Override
    public Integer getProximoId(Connection connection) throws SQLException {
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

    @Override
    public Item adicionar(Item item) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            Integer proximoId = this.getProximoId(con);
            item.setIdItem(proximoId);

            String sql = """
                    INSERT INTO ITEM\n
                    (id_item, nome, valor)\n
                    VALUES(?,?,?)
                    """;

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, item.getIdItem());
            stmt.setString(2, item.getNome());
            stmt.setDouble(3, item.getValor());

            String sqlCompraItem = """
                    INSERT INTO COMPRA_ITEM\n
                    (id_compra, id_item, quantidade)\n
                    VALUES(?,?,?)
                    """;

            String selectLastCompra = """
                    SEQ_CLIENTE.CURRVAL
                    """;

            PreparedStatement stmt1 = con.prepareStatement(sqlCompraItem);
            stmt1.setString(1, selectLastCompra);
            stmt1.setInt(2, item.getIdItem());
            stmt1.setDouble(3, item.getQuantidade());

            int res = stmt.executeUpdate();
            System.out.println("adicionarItem.res=" + res);
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
    public boolean remover(Integer id) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            String sql = "DELETE FROM item WHERE id_item = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);

            // Executa-se a consulta
            int res = stmt.executeUpdate();
            System.out.println("removerItemPorId.res=" + res);

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
    public boolean editar(Integer id, Item item) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE item SET \n");
            Cliente cliente = contato.getCliente();
            if (cliente != null) {
                if (cliente.getIdCliente() > 0) {
                    sql.append(" id_cliente = ?,");
                }
            }

            if(contato.getTelefone() != null){
                sql.append(" telefone = ?,");
            }

            if(contato.getEmail() != null){
                sql.append(" email = ?,");
            }

            sql.deleteCharAt(sql.length() - 1); //remove o ultimo ','
            sql.append(" WHERE id_contato = ? ");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            int index = 1;
            if (cliente != null) {
                if (cliente.getIdCliente() > 0) {
                    stmt.setInt(index++, cliente.getIdCliente());
                }
            }

            if (contato.getTelefone() != null) {
                stmt.setString(index++, contato.getTelefone());
            }

            if (contato.getEmail() != null) {
                stmt.setString(index++, contato.getEmail());
            }

            stmt.setInt(index++, id);

            // Executa-se a consulta
            int res = stmt.executeUpdate();
            System.out.println("editarContato.res=" + res);

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
    public List<Item> listar() throws BancoDeDadosException {
        return null;
    }
}
