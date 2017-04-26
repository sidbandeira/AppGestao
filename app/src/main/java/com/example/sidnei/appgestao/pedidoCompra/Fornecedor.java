package com.example.sidnei.appgestao.pedidoCompra;

public class Fornecedor {
    public int id;
    public String descricaofornecedor;
    public int codempresa;

    public Fornecedor(){
    }

    public Fornecedor(int id, String descricaofornecedor, int codempresa){
        this.id = id;
        this.descricaofornecedor = descricaofornecedor;
        this.codempresa = codempresa;
    }

    public Fornecedor(int id, String descricaofornecedor){
        this.id = id;
        this.descricaofornecedor = descricaofornecedor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricaofornecedor() {
        return descricaofornecedor;
    }

    public void setDescricaofornecedor(String descricaofornecedor) {
        this.descricaofornecedor = descricaofornecedor;
    }

    public int getCodempresa() {
        return codempresa;
    }

    public void setCodempresa(int codempresa) {
        this.codempresa = codempresa;
    }

}
