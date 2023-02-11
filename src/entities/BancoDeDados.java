package entities;

import java.util.ArrayList;
import java.util.List;

public class BancoDeDados {

    private List<Cliente> clientes = new ArrayList<>();
    private List<Conta> contas = new ArrayList<>();

    public void adicionarCliente(Cliente cliente){
        clientes.add(cliente);
    }

    public void adicionarConta(Conta conta){
        contas.add(conta);
    }

}
