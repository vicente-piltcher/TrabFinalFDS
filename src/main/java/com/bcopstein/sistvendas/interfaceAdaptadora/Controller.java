package com.bcopstein.sistvendas.interfaceAdaptadora;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bcopstein.sistvendas.aplicacao.casosDeUso.AdicionaEstoqueUC;
import com.bcopstein.sistvendas.aplicacao.casosDeUso.BuscaOrcamentoUC;
import com.bcopstein.sistvendas.aplicacao.casosDeUso.ConsultaEstoquePorIdUC;
import com.bcopstein.sistvendas.aplicacao.casosDeUso.CriaOrcamentoUC;
import com.bcopstein.sistvendas.aplicacao.casosDeUso.ConsultaEstoqueTotalUC;
import com.bcopstein.sistvendas.aplicacao.casosDeUso.ConsultaMediaImpostosUC;
import com.bcopstein.sistvendas.aplicacao.casosDeUso.ConsultaProdutosVendidosUC;
import com.bcopstein.sistvendas.aplicacao.casosDeUso.EfetivaOrcamentoUC;
import com.bcopstein.sistvendas.aplicacao.casosDeUso.ProdutosDisponiveisUC;
import com.bcopstein.sistvendas.aplicacao.casosDeUso.TaxaDeOrcamentosConvertidosUC;
import com.bcopstein.sistvendas.aplicacao.dtos.ItemPedidoDTO;
import com.bcopstein.sistvendas.aplicacao.dtos.OrcamentoDTO;
import com.bcopstein.sistvendas.aplicacao.dtos.ProdutoDTO;
import com.bcopstein.sistvendas.dominio.modelos.OrcamentoModel;
import com.bcopstein.sistvendas.dominio.modelos.ProdutoModel;



@RestController
public class Controller {
    private ProdutosDisponiveisUC produtosDisponiveis;
    private CriaOrcamentoUC criaOrcamento;
    private EfetivaOrcamentoUC efetivaOrcamento;
    private BuscaOrcamentoUC buscaOrcamento;
    private AdicionaEstoqueUC adicionaEstoque;
    private ConsultaEstoqueTotalUC consultaEstoque;
    private ConsultaEstoquePorIdUC consultaEstoquePorId;
    private TaxaDeOrcamentosConvertidosUC taxaOrcamentoConvertido;
    private ConsultaProdutosVendidosUC consultaProdutos;
    private ConsultaMediaImpostosUC consultaMediaImpostos;

    @Autowired
    public Controller(ProdutosDisponiveisUC produtosDisponiveis,
                      CriaOrcamentoUC criaOrcamento,
                      EfetivaOrcamentoUC efetivaOrcamento,
                      BuscaOrcamentoUC buscaOrcamento){
        this.produtosDisponiveis = produtosDisponiveis;
        this.criaOrcamento = criaOrcamento;
        this.efetivaOrcamento = efetivaOrcamento;
        this.buscaOrcamento = buscaOrcamento;
    }

    @GetMapping("")
    @CrossOrigin(origins = "*")
    public String welcomeMessage(){
        return("Bem vindo as lojas ACME");
    }

    @GetMapping("produtosDisponiveis")
    @CrossOrigin(origins = "*")
    public List<ProdutoDTO> produtosDisponiveis(){
        return produtosDisponiveis.run();
    }    

    @PostMapping("novoOrcamento")
    @CrossOrigin(origins = "*")
    public OrcamentoDTO novoOrcamento(@RequestBody List<ItemPedidoDTO> itens){
        return criaOrcamento.run(itens);
    }

    @GetMapping("efetivaOrcamento/{id}")
    @CrossOrigin(origins = "*")
    public OrcamentoDTO efetivaOrcamento(@PathVariable(value="id") long idOrcamento){
        return efetivaOrcamento.run(idOrcamento);
    }

    @GetMapping("buscaOrcamento/{id}")
    @CrossOrigin(origins = "*")
    public OrcamentoModel buscaOrcamento(@PathVariable(value="id") long idOrcamento){
        return buscaOrcamento.run(idOrcamento);
    }

    @PostMapping("adicionaEstoque/{id}/{qtd}")
    @CrossOrigin(origins = "*")
    public void adicionaEstoque(@PathVariable(value="id") long id, @PathVariable(value="qtd") int qtd){
        adicionaEstoque.run(id, qtd);
    }

    @GetMapping("consultaEstoque")
    @CrossOrigin(origins = "*")
    public long[][] consultaEstoqueTotal() {
        return consultaEstoque.run();
    }

    @GetMapping("consultaEstoque/{id}")
    @CrossOrigin(origins = "*")
    public Map<ProdutoModel, Integer> consultaEstoquePorId(@PathVariable(value="id") long id) {
        return consultaEstoquePorId.run(id);
    }

    @GetMapping("consultaTaxaOrcamento")
    @CrossOrigin(origins = "*")
    public double consultaTaxaOrcamento(){
        return taxaOrcamentoConvertido.run();
    }

    @GetMapping("consultaProdutosVendidos")
    @CrossOrigin(origins = "*")
    public Map<ProdutoModel, Integer> consultaProdutosVendidos(){
        return consultaProdutos.run();
    }

    @GetMapping("consultaMediaImpostos")
    @CrossOrigin(origins = "*")
    public double consultaMediaImpostos(){
        return consultaMediaImpostos.run();
    }
}