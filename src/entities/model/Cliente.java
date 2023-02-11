package entities.model;

import entities.controller.BancoDeDados;
import entities.interfaces.Exibicao;

import java.util.ArrayList;

public class Cliente implements Exibicao {
    private String nome;
    private String cpf;
    private ArrayList<Endereco> enderecos;
    private ArrayList<Contato> contatos;
    private String login;
    private String senha;
    private Conta conta;

    public Cliente(String nome, String cpf, ArrayList<Endereco> enderecos, ArrayList<Contato> contatos, String login, String senha) {
        this.nome = nome;
        this.cpf = cpf;
        this.enderecos = enderecos;
        this.contatos = contatos;
        this.login = login;
        this.senha = senha;
    }

    public void setConta(Conta conta){
        this.conta = conta;
    }

    public boolean criarConta() {
        return true;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getSenha() {
        return senha;
    }

    @Override
    public void exibir() {

    }

    public boolean verificarSenha(String senhaCliente) {
        return senhaCliente.equals(senha);
    }
}
