package com.bcopstein.sistvendas.dominio.servicos;

import java.util.Date;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.bcopstein.sistvendas.dominio.modelos.ItemPedidoModel;
import com.bcopstein.sistvendas.dominio.modelos.OrcamentoModel;
import com.bcopstein.sistvendas.dominio.modelos.PedidoModel;
import com.bcopstein.sistvendas.dominio.modelos.ProdutoModel;
import com.bcopstein.sistvendas.dominio.persistencia.IEstoqueRepositorio;
import com.bcopstein.sistvendas.dominio.persistencia.IOrcamentoRepositorio;

@Service
public class ServicoDeVendas {
    private IOrcamentoRepositorio orcamentos;
    private IEstoqueRepositorio estoque;

    @Autowired
    public ServicoDeVendas(IOrcamentoRepositorio orcamentos,IEstoqueRepositorio estoque){
        this.orcamentos = orcamentos;
        this.estoque = estoque;
    }
     
    public List<ProdutoModel> produtosDisponiveis() {
        return estoque.todosComEstoque();
    }

    public OrcamentoModel recuperaOrcamentoPorId(long id) {
        
        OrcamentoModel orc = this.orcamentos.recuperaPorId(id);

        if(orc == null) {
            throw new IllegalArgumentException("O orçamento nao existerrrr");
        }

        return orc;
    }

    public OrcamentoModel criaOrcamento(PedidoModel pedido) {
        var novoOrcamento = new OrcamentoModel();
        novoOrcamento.addItensPedido(pedido);
        double custoItens = novoOrcamento.getItens().stream()
            .mapToDouble(it->it.getProduto().getPrecoUnitario()*it.getQuantidade())
            .sum();
        novoOrcamento.setCustoItens(custoItens);
        novoOrcamento.setImposto(custoItens * 0.1);
        if (novoOrcamento.getItens().size() > 10){
            novoOrcamento.setDesconto(custoItens * 0.10);
        }else if (novoOrcamento.getItens().size() > 3){
            novoOrcamento.setDesconto(custoItens * 0.05);
        } else {
            novoOrcamento.setDesconto(0.0);
        }
        novoOrcamento.setCustoConsumidor(custoItens + novoOrcamento.getImposto() - novoOrcamento.getDesconto());
        return this.orcamentos.cadastra(novoOrcamento);
    }

    public List<OrcamentoModel> listaPorData(String d1, String d2){
        Date d1date = null;
        Date d2date = null;
        LocalDate localDate1 = null;
        LocalDate localDate2 = null;
        if (d1 == null || d2 == null) {
            throw new IllegalArgumentException("Datas não podem ser nulas");
        }
        if (d1.isEmpty() || d2.isEmpty()) {
            throw new IllegalArgumentException("Datas não podem ser vazias");
        }
        if (d1.length() != 10 || d2.length() != 10) {
            throw new IllegalArgumentException("Formato de data deve ser yyyy-MM-dd");
        }
        if (!d1.matches("\\d{4}-\\d{2}-\\d{2}") || !d2.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new IllegalArgumentException("Formato de data deve ser yyyy-MM-dd");
        }
        if (d1.compareTo(d2) > 0) {
            throw new IllegalArgumentException("Data inicial não pode ser maior que a data final");
        }
        if (d1.compareTo(d2) == 0) {
            throw new IllegalArgumentException("Datas não podem ser iguais");
        }
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            d1date = dateFormat.parse(d1);
            d2date = dateFormat.parse(d2);
            localDate1 = d1date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            localDate2 = d2date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        } catch(ParseException e){
            throw new IllegalArgumentException("Formato de data incorreto");
        }

        List<OrcamentoModel> orcamentoList = this.orcamentos.recuperaListaDataOrcamento(localDate1, localDate2);

        return orcamentoList;


    }
 
    public OrcamentoModel efetivaOrcamento(long id) {
        // Recupera o orçamento
        var orcamento = this.orcamentos.recuperaPorId(id);
        if (orcamento == null || orcamento.isEfetivado()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Orçamento inexistente ou já efetivado");
        }

        var orcamentoDate = orcamento.getDate();
        if (orcamentoDate == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Data do orçamento não definida");
        }
        var today = LocalDate.now();

        if (ChronoUnit.DAYS.between(orcamentoDate, today) > 21) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Orçamento é superior a 21 dias");
        }
        
        var ok = true;
        // Verifica se tem quantidade em estoque para todos os itens
        for (ItemPedidoModel itemPedido:orcamento.getItens()) {
            int qtdade = estoque.quantidadeEmEstoque(itemPedido.getProduto().getId());
            if (qtdade < itemPedido.getQuantidade()) {
                ok = false;
                System.out.println("\n\n- semEstoque: " + itemPedido.getProduto());
                System.out.println("\n idProd:"+itemPedido.getProduto().getId());
                System.out.println("\n solicitado: "+itemPedido.getQuantidade());
                System.out.println("\n estoque: "+qtdade);
                break;
            }
        }
        // Se tem quantidade para todos os itens, da baixa no estoque para todos
        if (ok) {
            for (ItemPedidoModel itemPedido:orcamento.getItens()) {
                int qtdade = estoque.quantidadeEmEstoque(itemPedido.getProduto().getId());
                if (qtdade >= itemPedido.getQuantidade()) {
                    estoque.baixaEstoque(itemPedido.getProduto().getId(), itemPedido.getQuantidade());
                }
            }
            System.out.println("\n\n- marcar efetivado: " + id);
            // Marca o orcamento como efetivado
            orcamentos.marcaComoEfetivado(id);
        }
        // Retorna o orçamento marcado como efetivado ou não conforme disponibilidade do estoque
        return orcamentos.recuperaPorId(id);
    }

    public OrcamentoModel buscaOrcamento(long idOrcamento) {
        OrcamentoModel orc = this.orcamentos.recuperaPorId(idOrcamento);
        
        if(orc == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Orçamento não encontrado");
        }

        return orc;
    }

    
}
