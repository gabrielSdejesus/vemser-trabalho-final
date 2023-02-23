package entities.repository;

import entities.exception.BancoDeDadosException;
import entities.model.Cartao;

import java.sql.*;
import java.util.List;

public class CartaoRepository implements Repository<Integer, Cartao> {
    @Override
    public Integer getProximoId(Connection connection) throws SQLException {
        try {
            String sql = "SELECT SEQ_CARTAO.NEXTVAL mysequence from DUAL";
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
    public Cartao adicionar(Cartao cartao) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            Integer proximoId = this.getProximoId(con);
            cartao.setNumeroCartao(String.valueOf(proximoId));

            String sql = """
                    INSERT INTO cartao\n 
                    VALUES(?, ?, ?, ?, ?, ?)
                    """;

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, cartao.);


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
    public boolean remover(Integer id) throws BancoDeDadosException {
        return false;
    }

    @Override
    public boolean editar(Integer id, Cartao objeto) throws BancoDeDadosException {
        return false;
    }

    @Override
    public List<Cartao> listar() throws BancoDeDadosException {
        return null;
    }
}
