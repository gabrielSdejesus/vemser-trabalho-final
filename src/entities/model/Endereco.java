package entities.model;

import entities.interfaces.Alteracao;

public class Endereco implements Alteracao {
    private String logradouro, cidade, estado, pais, cep;

    public Endereco(String logradouro, String cidade, String estado, String pais, String cep) {
        this.logradouro = logradouro;
        this.cidade = cidade;
        this.estado = estado;
        this.pais = pais;
        this.cep = cep;
    }

    @Override
    public boolean alterarDado(String tipo, String novoDado) {
        switch(tipo){
            case "logradouro" -> {
                this.logradouro = novoDado;
                return true;
            }
            case "cidade" -> {
                this.cidade = novoDado;
                return true;
            }
            case "estado" -> {
                this.estado = novoDado;
                return true;
            }
            case "pais" -> {
                this.pais = novoDado;
                return true;
            }
            case "cep" -> {
                this.cep = novoDado;
                return true;
            }
            default -> {
                System.err.println("Tipo inválido, erro na linha 40 do Endereço");
                return false;
            }
        }
    }
}
