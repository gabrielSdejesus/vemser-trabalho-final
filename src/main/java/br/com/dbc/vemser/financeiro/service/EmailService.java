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

    public void sendEmailCreate(Conta conta, CartaoDTO cartaoDTO) throws RegraDeNegocioException, BancoDeDadosException {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        ContatoDTO contato = getContato(conta.getCliente().getIdCliente());

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(contato.getEmail());
            mimeMessageHelper.setSubject("Olá, cadastro realizado com sucesso!");
            mimeMessageHelper.setText(getTemplateCreate(conta, cartaoDTO), true);

            emailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    public String getTemplateCreate(Conta conta, CartaoDTO cartaoDTO) throws IOException, TemplateException, BancoDeDadosException {
        Map<String, Object> dados = new HashMap<>();

        dados.put("nome", conta.getCliente().getNome());
        dados.put("numero_conta", conta.getNumeroConta().toString());
        dados.put("agencia", conta.getAgencia().toString());
        dados.put("numero_cartao", cartaoDTO.getNumeroCartao().toString());
        dados.put("data_expedicao", cartaoDTO.getDataExpedicao());
        dados.put("codigo_seguranca", cartaoDTO.getCodigoSeguranca());
        dados.put("tipo_cartao", cartaoDTO.getTipo());
        dados.put("data_vencimento", cartaoDTO.getVencimento());
        dados.put("email", from);

        Template template = fmConfiguration.getTemplate("contacreate.ftl");
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
    }

    public void sendEmailDelete(ContaDTO contaDTO) throws RegraDeNegocioException, BancoDeDadosException {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        ContatoDTO contato = getContato(contaDTO.getCliente().getIdCliente());

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(contato.getEmail());
            mimeMessageHelper.setSubject("Adeus!");

            mimeMessageHelper.setText(getTemplateDelete(contato), true);
            emailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    public String getTemplateDelete(ContatoDTO contato) throws IOException, TemplateException, BancoDeDadosException {
        Map<String, Object> dados = new HashMap<>();
        ClienteDTO clienteDTO = clienteService.retornandoCliente(contato.getIdCliente());

        dados.put("nome", clienteDTO.getNome());
        dados.put("email", from);

        Template template = fmConfiguration.getTemplate("contadelete.ftl");
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
    }


    private ContatoDTO getContato(Integer idCliente) throws RegraDeNegocioException, BancoDeDadosException {
        return contatoService.listarContatosDoCliente(idCliente).stream().findFirst().orElseThrow();
    }

    private Conta getConta(List<Object> object) {
        return (Conta) object.stream()
                .filter(o -> o instanceof Conta)
                .findFirst()
                .orElseThrow();
    }
}
