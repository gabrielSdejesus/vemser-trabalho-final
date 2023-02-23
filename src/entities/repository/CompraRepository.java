package entities.repository;

import entities.exception.BancoDeDadosException;
import entities.model.*;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CompraRepository implements Repository<Integer, Compra>{

    @Override
    public Integer getProximoId(Connection connection) throws SQLException {
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
                    INSERT INTO compra\n
                    (id_compra, numero_cartao, doc_vendedor, data)\n
                    VALUES(?,?,?,?)
                    """;

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, compra.getIdCompra());
            stmt.setInt(2, Integer.parseInt(compra.getCartao().getNumeroCartao()));
            stmt.setString(3, compra.getDocVendedor());
            stmt.setDate(4, Date.valueOf(compra.getData()));

            int res = stmt.executeUpdate();
            System.out.println("adicionarCompra.res=" + res);
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
    public boolean remover(Integer id) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            String sql = "DELETE FROM compra WHERE id_compra = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);

            // Executa-se a consulta
            int res = stmt.executeUpdate();
            System.out.println("removerCompraPorId.res=" + res);

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
    public boolean editar(Integer id, Compra compra) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE compra SET \n");
            Cartao cartao = compra.getCartao();
            if (cartao != null) {
                if (cartao.getNumeroCartao() != null) {
                    sql.append(" numero_cartao = ?,");
                }
            }

            if(compra.getDocVendedor() != null){
                sql.append(" doc_vendedor = ?,");
            }

            if(compra.getData() != null){
                sql.append(" data = ?,");
            }

            sql.deleteCharAt(sql.length() - 1); //remove o ultimo ','
            sql.append(" WHERE id_compra = ? ");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            int index = 1;
            if (cartao != null) {
                if (cartao.getNumeroCartao() != null) {
                    stmt.setInt(index++, Integer.parseInt(cartao.getNumeroCartao()));
                }
            }

            if(compra.getDocVendedor() != null){
                stmt.setString(index++, compra.getDocVendedor());
            }

            if(compra.getData() != null){
                stmt.setDate(index++, Date.valueOf(compra.getData()));
            }

            stmt.setInt(index++, id);

            // Executa-se a consulta
            int res = stmt.executeUpdate();
            System.out.println("editarCompra.res=" + res);

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
    public List<Compra> listar() throws BancoDeDadosException {
        List<Compra> compras = new ArrayList<>();
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = """
                        SELECT c.*, c4.NOME, c3.NUMERO_CONTA FROM COMPRA c\n 
                        INNER JOIN CARTAO c2 ON c.NUMERO_CARTAO = c2.NUMERO_CARTAO\n
                        INNER JOIN CONTA c3 ON c3.NUMERO_CONTA  = c2.NUMERO_CONTA\n
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

    public List<Compra> listarPorCartao(String numeroCartao) throws BancoDeDadosException {
        List<Compra> compras = new ArrayList<>();
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            String sql = """
                        SELECT c.*, c4.NOME, c3.NUMERO_CONTA FROM COMPRA c\n 
                        INNER JOIN CARTAO c2 ON c.NUMERO_CARTAO = c2.NUMERO_CARTAO\n
                        INNER JOIN CONTA c3 ON c3.NUMERO_CONTA  = c2.NUMERO_CONTA\n
                        INNER JOIN CLIENTE c4 ON c4.ID_CLIENTE  = c3.ID_CLIENTE
                        WHERE c.NUMERO_CARTAO = ?
                    """;

            // Executa-se a consulta
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, numeroCartao);

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
        Cartao cartao = new CartaoDeDebito();
        cartao.setNumeroCartao(res.getString("numero_cartao"));
        Conta conta = new Conta();
        conta.setNumeroConta(res.getInt("numero_conta"));
        Cliente cliente = new Cliente();
        cliente.setNome(res.getString("nome"));
        conta.setIdCliente(cliente);
        cartao.setConta(conta);
        compra.setCartao(cartao);
        return compra;
    }
}
