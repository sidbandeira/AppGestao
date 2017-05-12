package com.example.sidnei.appgestao.Classes;

public class Produto {
    public int _id;
    public String produtoCodBarras;
    public String produtoDescricao;
    public Double produtoPrecoVenda;
    public Double produtoPrecoCusto;
    public String produtodtultimacompra;
    public Double produtoSaldo;

    //CONSTRUTOR COM PARAMETROS
    public Produto(int _id, String produtoCodBarras, String produtoDescricao, Double produtoPrecovenda,Double produtoPrecoCusto,
                   String produtodtultimacompra, Double produtoSaldo){
        this._id = _id;
        this.produtoCodBarras = produtoCodBarras;
        this.produtoDescricao = produtoDescricao;
        this.produtoPrecoVenda = produtoPrecovenda;
        this.produtoPrecoCusto = produtoPrecoCusto;
        this.produtodtultimacompra = produtodtultimacompra;
        this.produtoSaldo = produtoSaldo;
    }

    //CONSTRUTOR VAZIO
    public Produto(){

    }

    public long get_id(){
        return _id;
    }

    public void set_id(int id){
        this._id = id;
    }

    public String getProdutoCodBarras() {
        return produtoCodBarras;
    }

    public void setProdutoCodBarras(String produtoCodBarras) {
        this.produtoCodBarras = produtoCodBarras;
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

    public String getProdutodtultimacompra() {
        return produtodtultimacompra;
    }

    public void setProdutodtultimacompra(String produtodtultimacompra) {
        this.produtodtultimacompra = produtodtultimacompra;
    }

    public Double getProdutoSaldo() {
        return produtoSaldo;
    }

    public void setProdutoSaldo(Double produtoSaldo) {
        this.produtoSaldo = produtoSaldo;
    }

    public String toString(){
        String temp;

        return  String.valueOf(this._id);
    }
}
