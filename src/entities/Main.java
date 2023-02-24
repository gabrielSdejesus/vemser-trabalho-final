package entities;

import entities.exception.BancoDeDadosException;
import entities.model.*;
import entities.repository.ContatoRepository;
import entities.repository.EnderecoRepository;
import entities.repository.ItemRepository;
import entities.service.TransferenciaService;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws BancoDeDadosException {
        //Gabriel eu vou lhe pegar, lá ele

        TransferenciaService transferenciaService = new TransferenciaService();
        transferenciaService.listarTransferenciasPorConta(444444);

    }
}
