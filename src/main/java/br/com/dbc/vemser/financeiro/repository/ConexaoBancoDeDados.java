package br.com.dbc.vemser.financeiro.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class ConexaoBancoDeDados {

    private static final String SERVER = "vemser-dbc.dbccompany.com.br";
    private static final String PORT = "25000"; // Porta TCP padrão do Oracle
    private static final String DATABASE = "xe";

    // autenticação
    private static String USER;
    private static String PASS;
    private static final String SCHEMA = "FINANCEIRO";

    @Value("${bd.usuario}")
    private String usuario;
    @Value("${bd.senha}")
    private String senha;

    @Value("${bd.usuario}")
    public void setUsuarioStatic(String usuario){
        ConexaoBancoDeDados.USER = usuario;
    }

    @Value("${bd.senha}")
    public void setSenhaStatic(String senha){
        ConexaoBancoDeDados.PASS = senha;
    }

    public static Connection getConnection() throws SQLException {
        String url = "jdbc:oracle:thin:@" + SERVER + ":" + PORT + ":" + DATABASE;

        // Abre-se a conexão com o Banco de Dados
        Connection con = DriverManager.getConnection(url, USER, PASS);

        // sempre usar o schema vem_ser
        con.createStatement().execute("alter session set current_schema=" + SCHEMA);

        return con;
    }
}