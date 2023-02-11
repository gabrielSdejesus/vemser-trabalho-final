package entities.model;

import entities.interfaces.Alteracao;

public class Contato implements Alteracao {

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
                System.err.println("Tipo inválido, erro na linha 25 do Contato");
                return false;
            }
        }
    }
}
