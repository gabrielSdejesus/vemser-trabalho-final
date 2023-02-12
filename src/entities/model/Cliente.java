package entities.model;

import entities.interfaces.Exibicao;

import java.util.ArrayList;
import java.util.Scanner;

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
        System.out.println("\nExibindo informações do cliente");
        System.out.println("\tNome: "+this.nome);
        System.out.println("\tCpf: "+this.cpf);
        System.out.println("Exibindo todos os contatos:");
        for(int i=0;i< contatos.size();i++){
            System.out.printf("\tContato [%d]:\n", (i+1));
            contatos.get(i).exibir();
        }
        System.out.println("Exibindo todos os endereços:");
        for(int i=0;i< enderecos.size();i++){
            System.out.printf("\tEndereço [%d]:\n", (i+1));
            enderecos.get(i).exibir();
        }
        System.out.print("Pressione ENTER para continuar...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    public boolean alterarSenha(String senhaAntiga, String novaSenha){
        if(verificarSenha(senhaAntiga)){
            this.senha = novaSenha;
            return true;
        }
        return false;
    }

    public boolean verificarSenha(String senhaCliente) {
        return senhaCliente.equals(senha);
    }

    public boolean verificarLogin(String loginCliente) {
        return loginCliente.equals(login);
    }

    public ArrayList<Contato> getContatos(){
        return contatos;
    }

    public ArrayList<Endereco> getEnderecos(){
        return enderecos;
    }

    public void addContatos(ArrayList<Contato> contatos){
        for(Contato contato: contatos){
            if(contato != null){
                this.contatos.add(contato);
            }
        }
    }

    public void addEnderecos(ArrayList<Endereco> enderecos){
        for(Endereco endereco: enderecos){
            if(endereco != null){
                this.enderecos.add(endereco);
            }
        }
    }

}
