package com.example.sidnei.appgestao.pedidoCompra;

public class PedidoCompraItem {

    public long _id;
    public long idCompra;
    public long idItem;
    public String descricaoItem;
    public Double qtdeItem;
    public Double precoCusto;
    public Double totalItem;

    public PedidoCompraItem(){

    }

    public PedidoCompraItem(long _id, long idCompra, long idItem, String descricaoItem,
                            Double qtdeItem, Double precoCusto, Double totalItem){
        this._id = _id;
        this.idCompra = idCompra;
        this.idItem = idItem;
        this.descricaoItem = descricaoItem;
        this.qtdeItem = qtdeItem;
        this.precoCusto = precoCusto;
        this.totalItem = totalItem;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public long getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(long idCompra) {
        this.idCompra = idCompra;
    }

    public long getIdItem() {
        return idItem;
    }

    public void setIdItem(long idItem) {
        this.idItem = idItem;
    }

    public String getDescricaoItem() {
        return descricaoItem;
    }

    public void setDescricaoItem(String descricaoItem) {
        this.descricaoItem = descricaoItem;
    }

    public Double getQtdeItem() {
        return qtdeItem;
    }

    public void setQtdeItem(Double qtdeItem) {
        this.qtdeItem = qtdeItem;
    }

    public Double getPrecoCusto() {
        return precoCusto;
    }

    public void setPrecoCusto(Double precoCusto) {
        this.precoCusto = precoCusto;
    }

    public Double getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(Double totalItem) {
        this.totalItem = totalItem;
    }


}
