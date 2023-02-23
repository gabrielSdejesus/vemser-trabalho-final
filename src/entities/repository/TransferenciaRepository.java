package entities.repository;

import entities.exception.BancoDeDadosException;
import entities.model.Conta;
import entities.model.Transferencia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransferenciaRepository implements Repository<Integer, Transferencia> {

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
                    INSERT INTO TRANSFERENCIA\n
                    (id_transferencia, numero_conta_enviou, numero_conta_recebeu, valor)\n
                    VALUES(?,?,?,?)
                    """;

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, transferencia.getIdTransferencia());
            stmt.setInt(2, transferencia.getContaEnviou().getNumeroConta());
            stmt.setInt(3, transferencia.getContaRecebeu().getNumeroConta());
            stmt.setDouble(4, transferencia.getValor());

            int res = stmt.executeUpdate();
            System.out.println("adicionarTransferencia.res=" + res);
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
    public boolean remover(Integer id) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            String sql = "DELETE FROM TRANSFERENCIA WHERE id_transferencia = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);

            int res = stmt.executeUpdate();
            System.out.println("removerTransferenciaPorId.res=" + res);

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
    public boolean editar(Integer id, Transferencia transferencia) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE TRANSFERENCIA SET \n");
            Conta conta = transferencia.getContaEnviou();
            if (conta != null) {
                if (conta.getNumeroConta() > 0) {
                    sql.append(" numero_conta_enviou = ?,");
                }
            }

            if (transferencia.getContaRecebeu() != null){
                sql.append(" numero_conta_recebeu = ?,");
            }

            if (transferencia.getValor() != null) {
                sql.append(" valor = ?,");
            }

            sql.deleteCharAt(sql.length() - 1); //remove o ultimo ','
            sql.append(" WHERE id_transferencia = ? ");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            int index = 1;
            if (conta != null) {
                if (conta.getNumeroConta() > 0) {
                    stmt.setInt(index++, conta.getNumeroConta());
                }
            }

            if (transferencia.getContaRecebeu() != null) {
                stmt.setInt(index++, transferencia.getContaRecebeu().getNumeroConta());
            }

            if (transferencia.getValor() != null) {
                stmt.setDouble(index++, transferencia.getValor());
            }

            stmt.setInt(index, id);

            int res = stmt.executeUpdate();
            System.out.println("editarTransferencia.res=" + res);

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
    public List<Transferencia> listar() throws BancoDeDadosException {
        List<Transferencia> transferencias = new ArrayList<>();
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = """
                        SELECT c.numero_conta, t.*  FROM TRANSFERENCIA t\n 
                        INNER JOIN CONTA c ON t.numero_conta_enviou = c.numero_conta\n
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

    public List<Transferencia> listarTransferenciasPorConta(Integer numeroConta) throws BancoDeDadosException {
        List<Transferencia> transferencias = new ArrayList<>();
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            String sql = """
                        SELECT c.numero_conta, t.*  FROM TRANSFERENCIA t\n 
                        INNER JOIN CONTA c ON t.numero_conta_enviou = c.numero_conta\n
                        WHERE t.numero_conta_enviou = ?
                    """;

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, numeroConta);

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
        Transferencia transferencia = new Transferencia();
        transferencia.setIdTransferencia(res.getInt("id_transferencia"));
        Conta contaEnviou = new Conta();
        contaEnviou.setNumeroConta(res.getInt("numero_conta_enviou"));
        Conta contaRecebeu = new Conta();
        contaRecebeu.setNumeroConta(res.getInt("numero_conta_recebeu"));
        transferencia.setContaEnviou(contaEnviou);
        transferencia.setContaRecebeu(contaRecebeu);
        transferencia.setValor(res.getDouble("valor"));
        return transferencia;
    }
}
