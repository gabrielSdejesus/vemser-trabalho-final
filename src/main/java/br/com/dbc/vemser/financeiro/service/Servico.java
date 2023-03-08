package br.com.dbc.vemser.financeiro.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class Servico {
    protected final ObjectMapper objectMapper;
}
