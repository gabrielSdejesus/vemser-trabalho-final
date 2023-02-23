package entities.repository;

import entities.exception.BancoDeDadosException;
import entities.model.Cartao;
import entities.model.Conta;

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
            stmt.setInt(1, Integer.parseInt(cartao.getNumeroCartao()));
            stmt.setInt(2, cartao.getConta().getNumeroConta());
            stmt.setDate(3, Date.valueOf(cartao.getDataExpedicao()));
            stmt.setInt(4, cartao.getCodigoSeguranca());
            stmt.setInt(5, cartao.getTipo().getTipo());
            stmt.setDate(6, Date.valueOf(cartao.getVencimento()));

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
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            String sql = "DELETE FROM cartao WHERE id_cartao = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);

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
    public boolean editar(Integer id, Cartao cartao) throws BancoDeDadosException {
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

            stmt.setInt(index++, id);

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
        return null;
    }
}
