package com.example.sidnei.appgestao.pedidocompra;

public class PedidoCompra {
    public long _id;
    public long idFornecedor;
    public long codEmpresa;
    public long codUnNegocio;
    public String dtPedido;
    public String formapgto;
    public Double totalPedido;

    public PedidoCompra(long _id, long idFornecedor, long codEmpresa, long codUnNegocio,String dtPedido,
                        Double totalPedido, String formapgto){
        this._id = _id;
        this.idFornecedor = idFornecedor;
        this.codEmpresa = codEmpresa;
        this.codUnNegocio = codUnNegocio;
        this.dtPedido = dtPedido;
        this.formapgto = formapgto;
        this.totalPedido = totalPedido;
    }
}
