package entities.model;

import entities.interfaces.Alteracao;
import entities.interfaces.Exibicao;

public class Endereco implements Alteracao, Exibicao {
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
                System.err.println("Tipo inválido, erro no alterarDado do Endereço");
                return false;
            }
        }
    }

    @Override
    public void exibir() {
        System.out.println("\t\tLogradouro: "+this.logradouro);
        System.out.println("\t\tCidade: "+this.cidade);
        System.out.println("\t\tEstado: "+this.estado);
        System.out.println("\t\tPaís: "+this.pais);
        System.out.println("\t\tCEP: "+this.cep);
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getCidade() {
        return cidade;
    }

    public String getEstado() {
        return estado;
    }

    public String getPais() {
        return pais;
    }

    public String getCep() {
        return cep;
    }
}
