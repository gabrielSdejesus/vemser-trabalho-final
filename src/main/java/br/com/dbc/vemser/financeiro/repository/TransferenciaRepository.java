package br.com.dbc.vemser.financeiro.repository;


import br.com.dbc.vemser.financeiro.exception.BancoDeDadosException;
import br.com.dbc.vemser.financeiro.model.Cliente;
import br.com.dbc.vemser.financeiro.model.Conta;
import br.com.dbc.vemser.financeiro.model.Transferencia;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TransferenciaRepository implements Repositorio<Transferencia> {

    @Override
    public Integer getProximoId(Connection connection) throws BancoDeDadosException {
        try {
            String sql = "SELECT SEQ_TRANSFERENCIA.NEXTVAL mysequence from DUAL";
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
    public Transferencia adicionar(Transferencia transferencia) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            Integer proximoId = this.getProximoId(con);
            transferencia.setIdTransferencia(proximoId);

            String sql = """
                    INSERT INTO TRANSFERENCIA
                    (id_transferencia, numero_conta_enviou, numero_conta_recebeu, valor)
                    VALUES(?,?,?,?)
                    """;

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, transferencia.getIdTransferencia());
            stmt.setLong(2, transferencia.getContaEnviou());
            stmt.setLong(3, transferencia.getContaRecebeu());
            stmt.setDouble(4, transferencia.getValor());

            // Executar consulta
            stmt.executeUpdate();
            return transferencia;

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
    public List<Transferencia> listar() throws BancoDeDadosException {
        List<Transferencia> transferencias = new ArrayList<>();
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = """
                    SELECT t.ID_TRANSFERENCIA, t.NUMERO_CONTA_RECEBEU, t.NUMERO_CONTA_ENVIOU, t.VALOR FROM TRANSFERENCIA t
                                        ORDER BY t.ID_TRANSFERENCIA DESC
                    """;

            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                Transferencia transferencia = getTransferenciaFromResultSet(res);
                transferencias.add(transferencia);
            }
            return transferencias;
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

    public List<Transferencia> listarTransferenciasPorConta(Integer id) throws BancoDeDadosException {
        List<Transferencia> transferencias = new ArrayList<>();
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            String sql = """
                    SELECT t.ID_TRANSFERENCIA, t.NUMERO_CONTA_RECEBEU, t.NUMERO_CONTA_ENVIOU, t.VALOR FROM TRANSFERENCIA t
                                        WHERE t.NUMERO_CONTA_ENVIOU = ?
                                        ORDER BY t.ID_TRANSFERENCIA DESC
                    """;

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);

            ResultSet res = stmt.executeQuery();

            while (res.next()) {
                Transferencia transferencia = getTransferenciaFromResultSet(res);
                transferencias.add(transferencia);
            }
            return transferencias;
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

    public Transferencia retornarTransferencia(Integer id) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            String sql = """
                    SELECT t.* FROM TRANSFERENCIA t
                    WHERE t.id_transferencia = ?
                    """;

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);

            ResultSet res = stmt.executeQuery();
            res.next();
            return getTransferenciaFromResultSet(res);
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

    private Transferencia getTransferenciaFromResultSet(ResultSet res) throws SQLException {
        Transferencia transferencia = new Transferencia();
        transferencia.setIdTransferencia(res.getInt("ID_TRANSFERENCIA"));
        transferencia.setContaEnviou(res.getLong("NUMERO_CONTA_ENVIOU"));
        transferencia.setContaRecebeu(res.getLong("NUMERO_CONTA_RECEBEU"));
        transferencia.setValor(res.getDouble("VALOR"));
        return transferencia;
    }
}
