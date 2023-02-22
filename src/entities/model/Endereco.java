package entities.model;

import entities.interfaces.Alteracao;
import entities.interfaces.Exibicao;

public class Endereco {

    private Integer idEndereco;
    private Integer idCliente;
    private String logradouro;
    private String cidade;
    private String estado;
    private String pais;
    private String cep;

    public Endereco(Integer idEndereco, Integer idCliente, String logradouro, String cidade, String estado, String pais, String cep) {
        this.idEndereco = idEndereco;
        this.idCliente = idCliente;
        this.logradouro = logradouro;
        this.cidade = cidade;
        this.estado = estado;
        this.pais = pais;
        this.cep = cep;
    }

    public Integer getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(Integer idEndereco) {
        this.idEndereco = idEndereco;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }
}
