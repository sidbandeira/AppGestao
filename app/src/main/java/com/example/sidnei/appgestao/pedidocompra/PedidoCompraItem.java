package com.example.sidnei.appgestao.pedidoCompra;

public class PedidoCompraItem {
    public long _id;
    public long idCompra;
    public long idItem;
    public String descricaoItem;
    public Double qtdeItem;
    public Double precoCusto;
    public Double totalItem;


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
}
