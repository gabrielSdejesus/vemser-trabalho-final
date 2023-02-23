package entities;

import entities.exception.BancoDeDadosException;
import entities.model.*;
import entities.repository.CompraRepository;
import entities.repository.ContatoRepository;
import entities.repository.EnderecoRepository;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws BancoDeDadosException {

        CompraRepository cp = new CompraRepository();

        List<Compra> listCompra = cp.listar();

        listCompra.forEach(System.out::println);
    }
}
