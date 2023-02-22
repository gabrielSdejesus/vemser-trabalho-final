package entities.repository;

import entities.exception.BancoDeDadosException;
import entities.model.Endereco;

import java.sql.*;
import java.util.List;

public class EnderecoRepository implements Repository<Integer, Endereco> {


    @Override
    public Integer getProximoId(Connection connection) throws BancoDeDadosException {
        try {
            String sql = "SELECT SEQ_ENDERECO.NEXTVAL mysequence from DUAL";
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
    public Endereco adicionar(Endereco endereco) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            Integer proximoId = this.getProximoId(con);
            endereco.setIdEndereco(proximoId);

            StringBuilder sql = null;
            sql.append("INSERT INTO Endereco");
            sql.append("(id_endereco, id_cliente, cidade, logradouro, estado, pais, cep) ");
            sql.append("VALUES(?,?,?,?,?,?,?)");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            stmt.setInt(1, endereco.getIdEndereco());
            stmt.setInt(2, endereco.getIdCliente());
            stmt.setString(3, endereco.getCidade());
            stmt.setString(4, endereco.getLogradouro());
            stmt.setString(5, endereco.getEstado());
            stmt.setString(6, endereco.getPais());
            stmt.setString(7, endereco.getCep());

            int res = stmt.executeUpdate();
            System.out.println("adicionarEndereco.res=" + res);
            return endereco;
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

            String sql = "DELETE FROM Endereco WHERE ID_CONTATO = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);

            // Executa-se a consulta
            int res = stmt.executeUpdate();
            System.out.println("removerEnderecoPorId.res=" + res);

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
    public boolean editar(Integer id, Contato contato) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE contato SET \n");
            Pessoa pessoa = contato.getPessoa();
            if (pessoa != null) {
                if (pessoa.getIdPessoa() != null) {
                    sql.append(" id_pessoa = ?,");
                }
            }
            if (contato.getTipoContato() != null) {
                sql.append(" tipo = ?,");
            }
            if (contato.getNumero() != null) {
                sql.append(" numero = ?,");
            }
            if (contato.getDescricao() != null) {
                sql.append(" descricao = ?,");
            }

            sql.deleteCharAt(sql.length() - 1); //remove o ultimo ','
            sql.append(" WHERE id_contato = ? ");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            int index = 1;
            if (pessoa != null) {
                if (pessoa.getIdPessoa() != null) {
                    stmt.setInt(index++, pessoa.getIdPessoa());
                }
            }
            if (contato.getTipoContato() != null) {
                stmt.setInt(index++, contato.getTipoContato().getTipo());
            }
            if (contato.getNumero() != null) {
                stmt.setString(index++, contato.getNumero());
            }
            if (contato.getDescricao() != null) {
                stmt.setString(index++, contato.getDescricao());
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
    public List<Endereco> listar() throws BancoDeDadosException {
        return null;
    }


}
