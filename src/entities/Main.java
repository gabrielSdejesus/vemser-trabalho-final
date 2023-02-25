package entities;

import entities.exception.BancoDeDadosException;
import entities.model.Cartao;
import entities.model.Conta;
import entities.repository.CartaoRepository;
import entities.repository.ContaRepository;
import entities.view.TelaPrincipal;

import java.util.List;

public class Main {
    public static void main(String[] args) throws BancoDeDadosException {
        //Gabriel eu vou lhe pegar, lá ele
        //TelaPrincipal.exibirTelaPrincipal();
        ContaRepository contaRepository = new ContaRepository();
        Conta conta = contaRepository.consultarPorNumeroConta(100000);
        System.out.println(conta.toString());
        /*CartaoRepository cartaoRepository = new CartaoRepository();
        Conta conta = new Conta();
        conta.setNumeroConta(100000);
        List<Cartao> cartaoList = cartaoRepository.listarCartoesPorNumeroConta(conta);
        cartaoList.forEach(System.out::println);*/
    }
}
