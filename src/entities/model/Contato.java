package entities.model;

import entities.interfaces.Alteracao;
import entities.interfaces.Exibicao;

public class Contato implements Alteracao, Exibicao {

    private String telefone;
    private String email;

    public Contato(String telefone, String email) {
        this.telefone = telefone;
        this.email = email;
    }

    @Override
    public boolean alterarDado(String tipo, String novoDado) {
        switch(tipo){
            case "telefone" -> {
                this.telefone = novoDado;
                return true;
            }
            case "email" -> {
                this.email = novoDado;
                return true;
            }
            default -> {
                System.err.println("Tipo inválido, erro no alterarDado do contato");
                return false;
            }
        }
    }

    @Override
    public void exibir() {
        System.out.println("\t\tTelefone: "+this.telefone);
        System.out.println("\t\tEmail: "+this.email);
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Contato{" +
                "telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
