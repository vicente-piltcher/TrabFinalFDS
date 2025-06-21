package com.bcopstein.sistvendas.dominio.servicos;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.bcopstein.sistvendas.aplicacao.dtos.EstoqueDTO;
import com.bcopstein.sistvendas.dominio.modelos.ItemPedidoModel;
import com.bcopstein.sistvendas.dominio.modelos.OrcamentoModel;
import com.bcopstein.sistvendas.dominio.modelos.ProdutoModel;
import com.bcopstein.sistvendas.dominio.persistencia.IEstoqueRepositorio;
import com.bcopstein.sistvendas.dominio.persistencia.IOrcamentoRepositorio;
import com.bcopstein.sistvendas.dominio.persistencia.IProdutoRepositorio;

@Service
public class ServidoDeGerenciamento {
    private IOrcamentoRepositorio orcamentos;
    private IEstoqueRepositorio estoque;
    private IProdutoRepositorio produtos;

    @Autowired
    public ServidoDeGerenciamento(IProdutoRepositorio produtos,IEstoqueRepositorio estoque, IOrcamentoRepositorio orcamento){
        this.produtos = produtos;
        this.estoque = estoque;
        this.orcamentos = orcamento ;
    }

    public double taxaDeOrcamentosConvertidos() {
        var orcamentosFeitos = this.orcamentos.todos();
        double orcamentoFeitosNum = orcamentosFeitos.size();
        double orcamentosEfetivadosNum = 0.0;


        for(OrcamentoModel orc : orcamentosFeitos) {
            if (orc.isEfetivado()) {
                orcamentosEfetivadosNum++;
            }
        }

        if (orcamentoFeitosNum == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum orçamento encontrado");
        }

        if (orcamentosEfetivadosNum == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum orçamento efetivado foi encontrado");
        }

        return orcamentosEfetivadosNum/orcamentoFeitosNum;
    }

    public Map<ProdutoModel, Integer> quantidadeDeProdutosVendidos(){
        var todosOrcamentos = this.orcamentos.todos();

        Map<ProdutoModel, Integer> vendasPorProduto = new HashMap<>();

        for (OrcamentoModel orc : todosOrcamentos) {
            if(orc.isEfetivado()){
                List<ItemPedidoModel> produtosOrcamento = orc.getItens();
                for(ItemPedidoModel ipm : produtosOrcamento) {
    
                    ProdutoModel produto = ipm.getProduto();
                    var quantidadeDoProduto = ipm.getQuantidade();
    
                    vendasPorProduto.merge(produto, quantidadeDoProduto, Integer::sum);
    
                }
            }
        }

        return vendasPorProduto;
    }

    public double mediaDeImpostoPorOrcamento(){
        var todosOrcamentos = this.orcamentos.todos();
        double sumImpostos = 0.0;

        for(OrcamentoModel orc : todosOrcamentos) {
            sumImpostos += orc.getImposto();
        }

        double mediaImpostos = sumImpostos/todosOrcamentos.size();

        return mediaImpostos;
    }

    public List<EstoqueDTO> estoquePorProduto() {
        var todosProdutos = this.produtos.todos();
        var produtosComEstoque = this.estoque.todosComEstoque();
        List<EstoqueDTO> estoqueDTOList = new ArrayList<>();


        for (ProdutoModel prod : todosProdutos) {
        int quantidade = 0;
        for (ProdutoModel pEstoque : produtosComEstoque) {
            if (pEstoque.getId() == prod.getId()) {
                quantidade = this.estoque.quantidadeEmEstoque(prod.getId());
                break;
            }
        }
        estoqueDTOList.add(new EstoqueDTO(prod.getId(), quantidade));
    }
    
        return estoqueDTOList;
    }

    public Map<ProdutoModel, Integer> estoquePorIdDeProduto(long id) {
        var produto = this.produtos.consultaPorId(id);
        var produtosComEstoque = this.estoque.quantidadeEmEstoque(id);

        if(produto == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado");
        }

        Map<ProdutoModel, Integer> vendasPorProduto = new HashMap<>();
    
        vendasPorProduto.put(produto, produtosComEstoque);
    
        return vendasPorProduto;
    }
    
    
    
    
}
