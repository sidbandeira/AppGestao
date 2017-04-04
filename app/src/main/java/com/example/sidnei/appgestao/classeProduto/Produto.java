package com.example.sidnei.appgestao.classeProduto;

public class Produto {
    public int _id;
    public String produtoDescricao;
    public Double produtoPrecoVenda;
    public Double produtoPrecoCusto;
    public String nomeSpinner;

    //    public Produto(int _id, String produtoDescricao, Double produtoPrecovenda,Double produtoPrecoCusto){
//        this._id = _id;
//        this.produtoDescricao = produtoDescricao;
//        this.produtoPrecovenda = produtoPrecovenda;
//        this.produtoPrecoCusto = produtoPrecoCusto;
//    }

    public long get_id(){
        return _id;
    }

    public void set_id(int id){
        this._id = id;
    }

    public String get_produtoDescricao(){
        return produtoDescricao;
    }

    public void setProdutoDescricao(String produtoDescricao){
        this.produtoDescricao = produtoDescricao;
    }

    public Double get_produtoPrecoVenda(){
        return produtoPrecoVenda;
    }

    public void setProdutoPrecovenda(Double produtoPrecoVenda){
        this.produtoPrecoVenda = produtoPrecoVenda;
    }

    public Double get_produtoPrecoCusto(){
        return produtoPrecoCusto;
    }

    public void setProdutoPrecoCusto(Double produtoPrecoCusto){
        this.produtoPrecoCusto = produtoPrecoCusto;
    }

    public String toString(){
        String temp;

        return  String.valueOf(this._id);
    }
}
