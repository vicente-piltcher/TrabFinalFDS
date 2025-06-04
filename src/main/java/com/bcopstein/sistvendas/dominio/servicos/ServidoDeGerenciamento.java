package com.bcopstein.sistvendas.dominio.servicos;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;

import com.bcopstein.sistvendas.dominio.modelos.ItemPedidoModel;
import com.bcopstein.sistvendas.dominio.modelos.OrcamentoModel;
import com.bcopstein.sistvendas.dominio.modelos.ProdutoModel;
import com.bcopstein.sistvendas.dominio.persistencia.IEstoqueRepositorio;
import com.bcopstein.sistvendas.dominio.persistencia.IOrcamentoRepositorio;
import com.bcopstein.sistvendas.dominio.persistencia.IProdutoRepositorio;

public class ServidoDeGerenciamento {
    private IOrcamentoRepositorio orcamentos;
    private IEstoqueRepositorio estoque;
    private IProdutoRepositorio produtos;

    @Autowired
    public ServidoDeGerenciamento(IProdutoRepositorio produtos,IEstoqueRepositorio estoque, IOrcamentoRepositorio orcamento){
        this.produtos = produtos;
        this.estoque = estoque;
        this.orcamentos = orcamento;
    }

    public double taxaDeOrcamentosConvertidos() {
        var orcamentosFeitos = this.orcamentos.todos();
        var orcamentoFeitosNum = orcamentosFeitos.size();
        var orcamentosEfetivadosNum = 0;

        for(OrcamentoModel orc : orcamentosFeitos) {
            if (orc.isEfetivado()) {
                orcamentosEfetivadosNum++;
            }
        }

        return orcamentoFeitosNum/orcamentosEfetivadosNum;
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

    public long[][] estoquePorProduto() {
        var todosProdutos = this.produtos.todos();
        var produtosComEstoque = this.estoque.todosComEstoque();
        int totalProdutos = todosProdutos.size();
    
        long[][] estoquePorId = new long[totalProdutos][2];
    
        for (int i = 0; i < totalProdutos; i++) {
            ProdutoModel prod = todosProdutos.get(i);
            estoquePorId[i][0] = prod.getId();
    
            int quantidade = 0;
            for (ProdutoModel pEstoque : produtosComEstoque) {
                if (pEstoque.getId() == prod.getId()) {
                    quantidade = this.estoque.todosComEstoque().size();
                    break;
                }
            }
            estoquePorId[i][1] = quantidade;
        }
    
        return estoquePorId;
    }

    public Map<ProdutoModel, Integer> estoquePorIdDeProduto(long id) {
        var produto = this.produtos.consultaPorId(id);
        var produtosComEstoque = this.estoque.quantidadeEmEstoque(id);

        Map<ProdutoModel, Integer> vendasPorProduto = new HashMap<>();
    
        vendasPorProduto.put(produto, produtosComEstoque);
    
        return vendasPorProduto;
    }
    
    
    
    
}
