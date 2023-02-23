package entities.model;

public class Contato {

    private Integer idContato;
    private Cliente cliente;
    private String telefone;
    private String email;

    public Contato() {
    }

    public Contato(Integer idContato, Cliente cliente, String telefone, String email) {
        this.idContato = idContato;
        this.cliente = cliente;
        this.telefone = telefone;
        this.email = email;
    }

    public Integer getIdContato() {
        return idContato;
    }

    public void setIdContato(Integer idContato) {
        this.idContato = idContato;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Contato{" +
                "idContato=" + idContato +
                ", cliente=" + cliente +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
