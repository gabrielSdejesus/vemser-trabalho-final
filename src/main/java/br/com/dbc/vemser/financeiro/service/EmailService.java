package br.com.dbc.vemser.financeiro.service;

import br.com.dbc.vemser.financeiro.dto.*;
import br.com.dbc.vemser.financeiro.exception.BancoDeDadosException;
import br.com.dbc.vemser.financeiro.exception.RegraDeNegocioException;
import br.com.dbc.vemser.financeiro.model.Cartao;
import br.com.dbc.vemser.financeiro.model.Cliente;
import br.com.dbc.vemser.financeiro.model.Conta;
import br.com.dbc.vemser.financeiro.model.Contato;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;
    private final Configuration fmConfiguration;
    private final ContatoService contatoService;
    private final ClienteService clienteService;
    private final CartaoService cartaoService;
    @Value("${spring.mail.username}")
    private String from;
    private static String to;

    public void sendEmailCliente(List<Object> object) throws RegraDeNegocioException, BancoDeDadosException {
        MimeMessage mimeMessagege = emailSender.createMimeMessage();

        //Cliente
        ClienteDTO cliente = (ClienteDTO) object.stream().filter(o -> o.getClass().equals(ClienteDTO.class)).findFirst().orElseThrow();
        //Contato
        List<ContatoDTO> contatoDTO = contatoService.listarContatosDoCliente(cliente.getIdCliente());
        ContatoDTO contatoDTO1 = contatoDTO.get(0);
        try {

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessagege, true);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(contatoDTO1.getEmail());
            mimeMessageHelper.setSubject("Olá, cadastro realizado com sucesso!");

            mimeMessageHelper.setText(getContentFromTemplateClienteCreate(object), true);
            emailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    public void sendEmailClienteDelete(Integer idCliente, List<ContatoDTO> contatoDTOS) throws RegraDeNegocioException, BancoDeDadosException {
        MimeMessage mimeMessagege = emailSender.createMimeMessage();
        //Buscando cliente
        Cliente cliente = clienteService.retornandoCliente(idCliente);
        //Buscando email do contato
        ContatoDTO contatoDTO = contatoDTOS.stream().findFirst().get();

        try {

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessagege, true);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(contatoDTO.getEmail());
            mimeMessageHelper.setSubject("Adeus!");

            mimeMessageHelper.setText(getContentFromTemplateClienteDelete(cliente), true);
            emailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    public String getContentFromTemplateClienteCreate(List<Object> object) throws IOException, TemplateException, BancoDeDadosException {
        Map<String, Object> dados = new HashMap<>();
        Template template = null;
        String html;

        //CLIENTE
        ClienteDTO cliente = (ClienteDTO) object.stream().filter(o -> o.getClass().equals(ClienteDTO.class)).findFirst().get();
        Cliente clienteEmail = clienteService.retornandoCliente(cliente.getIdCliente());

        //CONTA
        Conta conta = (Conta) object.stream().filter(o -> o.getClass().equals(Conta.class)).findFirst().get();

        //CARTAO
        CartaoCreateDTO cartaoCreateDTO = (CartaoCreateDTO) object.stream().filter(o -> o.getClass().equals(CartaoCreateDTO.class)).findFirst().get();
        List<CartaoDTO> listCartao = cartaoService.listarPorNumeroConta(conta.getNumeroConta());
        CartaoDTO cartaoDTO = listCartao.stream()
                .max(Comparator.comparingLong(CartaoDTO::getNumeroCartao))
                .orElse(null);

        dados.put("nome", clienteEmail.getNome());
        dados.put("numero_conta", conta.getNumeroConta().toString());
        dados.put("agencia", conta.getAgencia().toString());
        dados.put("numero_cartao", cartaoDTO.getNumeroCartao().toString());
        dados.put("data_expedicao", cartaoCreateDTO.getDataExpedicao());
        dados.put("codigo_seguranca", cartaoCreateDTO.getCodigoSeguranca());
        dados.put("tipo_cartao", cartaoCreateDTO.getTipo());
        dados.put("data_vencimento", cartaoCreateDTO.getVencimento());
        dados.put("email", from);

        template = fmConfiguration.getTemplate("contacreate.ftl");

        html = FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
        return html;
    }

    public String getContentFromTemplateClienteDelete(Cliente cliente) throws IOException, TemplateException, BancoDeDadosException {
        Map<String, Object> dados = new HashMap<>();
        Template template = null;
        String html;

        dados.put("nome", cliente.getNome());
        dados.put("email", from);
        template = fmConfiguration.getTemplate("contadelete.ftl");

        html = FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
        return html;
    }
}
