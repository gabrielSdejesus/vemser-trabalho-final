package entities.repository;

import entities.exception.BancoDeDadosException;
import entities.model.Cliente;
import entities.model.Contato;
import entities.model.Endereco;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContatoRepository implements Repository<Integer, Contato>{

    @Override
    public Integer getProximoId(Connection connection) throws BancoDeDadosException {
        try {
            String sql = "SELECT SEQ_CONTATO.NEXTVAL mysequence from DUAL";
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
    public Contato adicionar(Contato contato) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            Integer proximoId = this.getProximoId(con);
            contato.setIdContato(proximoId);

            String sql = """
                    INSERT INTO contato\n
                    (id_contato, id_cliente, telefone, email)\n
                    VALUES(?,?,?,?)
                    """;

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, contato.getIdContato());
            stmt.setInt(2, contato.getCliente().getIdCliente());
            stmt.setString(3, contato.getTelefone());
            stmt.setString(4, contato.getEmail());

            int res = stmt.executeUpdate();
            System.out.println("adicionarContato.res=" + res);
            return contato;
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

            String sql = "DELETE FROM contato WHERE id_contato = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);

            // Executa-se a consulta
            int res = stmt.executeUpdate();
            System.out.println("removerContatoPorId.res=" + res);

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
            Cliente cliente = contato.getCliente();


            if(contato.getTelefone() != null){
                sql.append(" telefone = ?,");
            }

            if(contato.getEmail() != null){
                sql.append(" email = ?,");
            }

            sql.deleteCharAt(sql.length() - 1); //remove o ultimo ','
            sql.append(" WHERE id_cliente = ? ");
            sql.append("AND id_contato = ?");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            int index = 1;

            if (contato.getTelefone() != null) {
                stmt.setString(index++, contato.getTelefone());
            }

            if (contato.getEmail() != null) {
                stmt.setString(index++, contato.getEmail());
            }

            stmt.setInt(index++, id);
            stmt.setInt(index, contato.getIdContato());

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
    public List<Contato> listar() throws BancoDeDadosException {
        List<Contato> contatos = new ArrayList<>();
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = """
                        SELECT *  FROM contato c\n 
                        INNER JOIN cliente c2 ON c.id_cliente = c2.id_cliente\n
                    """;

            // Executa-se a consulta
            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                Contato contato = getContatoFromResultSet(res);
                contatos.add(contato);
            }
            return contatos;
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

    public List<Contato> listarContatosPorPessoa(Integer idCliente) throws BancoDeDadosException {
        List<Contato> contatos = new ArrayList<>();
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            String sql = """
                        SELECT c2.nome, c.* FROM contato c\n 
                        INNER JOIN cliente c2 ON c.id_cliente = c2.id_cliente\n
                        WHERE c.id_cliente = ?
                    """;

            // Executa-se a consulta
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, idCliente);

            ResultSet res = stmt.executeQuery();

            while (res.next()) {
                Contato contato = getContatoFromResultSet(res);
                contatos.add(contato);
            }
            return contatos;
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

    private Contato getContatoFromResultSet(ResultSet res) throws SQLException {
        Contato contato = new Contato();
        contato.setIdContato(res.getInt("id_contato"));
        Cliente cliente = new Cliente();
        cliente.setNome(res.getString("nome"));
        contato.setCliente(cliente);
        contato.setTelefone(res.getString("telefone"));
        contato.setEmail(res.getString("email"));
        return contato;
    }
}
