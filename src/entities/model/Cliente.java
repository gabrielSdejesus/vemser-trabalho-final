package entities.model;

import entities.interfaces.Exibicao;

import java.util.ArrayList;
import java.util.Scanner;

public class Cliente {

    private Integer idCliente;
    private String cpf;
    private String nome;

    public Cliente(){};

    public Cliente(Integer idCliente, String cpf, String nome) {
        this.idCliente = idCliente;
        this.cpf = cpf;
        this.nome = nome;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
