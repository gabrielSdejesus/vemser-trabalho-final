package entities.model;

public class Endereco {
    private String logradouro, cidade, estado, pais, cep;

    public Endereco(String logradouro, String cidade, String estado, String pais, String cep) {
        this.logradouro = logradouro;
        this.cidade = cidade;
        this.estado = estado;
        this.pais = pais;
        this.cep = cep;
    }
}
