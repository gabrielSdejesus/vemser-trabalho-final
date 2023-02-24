package entities;

import entities.exception.BancoDeDadosException;
import entities.model.*;
import entities.repository.ClienteRepository;
import entities.repository.ContatoRepository;
import entities.repository.EnderecoRepository;
import entities.repository.ItemRepository;
import entities.service.ClienteService;
import entities.service.ContaService;
import entities.service.TransferenciaService;
import entities.view.TelaPerfil;
import entities.view.TelaPrincipal;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws BancoDeDadosException {
        //Gabriel eu vou lhe pegar, lá ele
        TelaPrincipal.exibirTelaPrincipal();
    }
}
