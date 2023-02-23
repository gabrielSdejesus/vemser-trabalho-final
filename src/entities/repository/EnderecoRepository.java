package entities.repository;

import entities.exception.BancoDeDadosException;
import entities.model.Cliente;
import entities.model.Endereco;

import java.sql.*;
import java.util.ArrayList;
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
            stmt.setInt(2, endereco.getCliente().getIdCliente());
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
    public boolean editar(Integer id, Endereco endereco) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE Endereco SET \n");
            Cliente cliente = endereco.getCliente();
            if (cliente != null) {
                if (cliente.getIdCliente() != null) {
                    sql.append(" id_cliente = ?,");
                }
            }

            if (endereco.getCidade() != null){
                sql.append(" cidade = ?,");
            }

            if (endereco.getLogradouro() != null) {
                sql.append(" logradouro = ?,");
            }

            if (endereco.getEstado() != null) {
                sql.append(" estado = ?,");
            }

            if (endereco.getPais() != null) {
                sql.append(" pais = ?,");
            }

            if (endereco.getCep() != null) {
                sql.append(" cep = ?,");
            }

            sql.deleteCharAt(sql.length() - 1); //remove o ultimo ','
            sql.append(" WHERE id_endereco = ? ");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            int index = 1;
            if (cliente != null) {
                if (cliente.getIdCliente() != null) {
                    stmt.setInt(index++, cliente.getIdCliente());
                }
            }

            if (endereco.getLogradouro() != null) {
                stmt.setString(index++, endereco.getLogradouro());
            }

            if (endereco.getEstado() != null) {
                stmt.setString(index++, endereco.getEstado());
            }

            if (endereco.getPais() != null) {
                stmt.setString(index++, endereco.getPais());
            }

            if (endereco.getCep() != null) {
                stmt.setString(index++, endereco.getCep());
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
        List<Endereco> enderecos = new ArrayList<>();
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

           StringBuilder sql = null;
           sql.append("SELECT c.nome, e.cidade, e.logradouro, e.estado, e.pais, e.cep ");
           sql.append("FROM Endereco e ");
           sql.append("INNER JOIN Cliente c ");
           sql.append("ON e.id_cliente = c.id_cliente");

            // Executa-se a consulta
            ResultSet res = stmt.executeQuery(sql.toString());

            while (res.next()) {
                Endereco endereco = getEnderecoFromResultSet(res);
                enderecos.add(endereco);
            }
            return enderecos;
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

    public List<Endereco> listarEnderecosPorPessoa(Integer idEndereco) throws BancoDeDadosException {
        List<Endereco> enderecos = new ArrayList<>();
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();


            StringBuilder sql = null;
            sql.append("SELECT c.nome, e.cidade, e.logradouro, e.estado, e.pais, e.cep ");
            sql.append("FROM Endereco e ");
            sql.append("INNER JOIN Cliente c ");
            sql.append("ON e.id_cliente = c.id_cliente ");
            sql.append("WHERE e.id_endereco = ?");

            // Executa-se a consulta
            PreparedStatement stmt = con.prepareStatement(sql.toString());
            stmt.setInt(1, idEndereco);

            ResultSet res = stmt.executeQuery();

            while (res.next()) {
                Endereco endereco = getEnderecoFromResultSet(res);
                enderecos.add(endereco);
            }
            return enderecos;
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

    private Endereco getEnderecoFromResultSet(ResultSet res) throws SQLException {
        Endereco endereco = new Endereco();
        endereco.setIdEndereco(res.getInt("id_endereco"));
        Cliente cliente = null;
        endereco.setCliente(cliente);
        endereco.setCep(res.getString("cep"));
        endereco.setCidade(res.getString("cidade"));
        endereco.setEstado(res.getString("estado"));
        endereco.setPais(res.getString("pais"));
        endereco.setLogradouro(res.getString("logradouro"));
        return endereco;
    }
}