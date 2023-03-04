package br.com.dbc.vemser.financeiro.service;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class Servico {
    protected final ObjectMapper objectMapper;

    public Servico(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }
}
