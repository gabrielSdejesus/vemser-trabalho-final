package entities.repository;

import entities.exception.BancoDeDadosException;
import entities.model.Cliente;
import entities.model.Conta;
import entities.model.Transferencia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransferenciaRepository implements Repository<Transferencia> {

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
            stmt.setInt(2, transferencia.getContaEnviou().getNumeroConta());
            stmt.setInt(3, transferencia.getContaRecebeu().getNumeroConta());
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
                    SELECT t.ID_TRANSFERENCIA, c.NOME AS NOME_RECEBEU, t.NUMERO_CONTA_RECEBEU,
                    c4.NOME AS NOME_ENVIOU, t.NUMERO_CONTA_ENVIOU, t.VALOR FROM CLIENTE c
                    INNER JOIN CONTA c2 ON c.ID_CLIENTE = c2.ID_CLIENTE
                    INNER JOIN TRANSFERENCIA t ON c2.NUMERO_CONTA = t.NUMERO_CONTA_RECEBEU
                    INNER JOIN CONTA c3 ON t.NUMERO_CONTA_ENVIOU = c3.NUMERO_CONTA
                    INNER JOIN CLIENTE c4 ON c3.ID_CLIENTE = c4.ID_CLIENTE
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
                    SELECT t.ID_TRANSFERENCIA, c.NOME AS NOME_RECEBEU, t.NUMERO_CONTA_RECEBEU,
                    c4.NOME AS NOME_ENVIOU, t.NUMERO_CONTA_ENVIOU, t.VALOR
                    FROM CLIENTE c
                    INNER JOIN CONTA c2 ON c.ID_CLIENTE = c2.ID_CLIENTE
                    INNER JOIN TRANSFERENCIA t ON c2.NUMERO_CONTA = t.NUMERO_CONTA_RECEBEU
                    INNER JOIN CONTA c3 ON t.NUMERO_CONTA_ENVIOU = c3.NUMERO_CONTA
                    INNER JOIN CLIENTE c4 ON c3.ID_CLIENTE = c4.ID_CLIENTE
                    WHERE (t.NUMERO_CONTA_ENVIOU = ?
                    OR t.NUMERO_CONTA_RECEBEU = ?)
                    AND ROWNUM <= 10
                    ORDER BY t.ID_TRANSFERENCIA DESC
                    """;

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.setInt(2, id);

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

    private Transferencia getTransferenciaFromResultSet(ResultSet res) throws SQLException {

        Conta contaEnviou = new Conta();
        Cliente clienteEnviou = new Cliente();
        clienteEnviou.setNome(res.getString("NOME_ENVIOU"));
        contaEnviou.setCliente(clienteEnviou);
        contaEnviou.setNumeroConta(res.getInt("NUMERO_CONTA_ENVIOU"));

        Conta contaRecebeu = new Conta();
        Cliente clienteRecebeu = new Cliente();
        clienteRecebeu.setNome(res.getString("NOME_RECEBEU"));
        contaRecebeu.setCliente(clienteRecebeu);
        contaRecebeu.setNumeroConta(res.getInt("NUMERO_CONTA_RECEBEU"));

        Transferencia transferencia = new Transferencia();
        transferencia.setIdTransferencia(res.getInt("ID_TRANSFERENCIA"));
        transferencia.setContaEnviou(contaEnviou);
        transferencia.setContaRecebeu(contaRecebeu);
        transferencia.setValor(res.getDouble("VALOR"));
        return transferencia;
    }
}
