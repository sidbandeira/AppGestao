package com.example.sidnei.appgestao.Classes;

public class PedidoCompra {
    public long _id;
    public long idFornecedor;
    public String descricaofornecedor;
    public long codEmpresa;
    public long codUnNegocio;
    public String email;
    public String dtPedido;
    public String dtEntrega;
    public String formapgto;
    public Double totalPedido;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public long getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(long idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    public long getCodEmpresa() {
        return codEmpresa;
    }

    public void setCodEmpresa(long codEmpresa) {
        this.codEmpresa = codEmpresa;
    }

    public long getCodUnNegocio() {
        return codUnNegocio;
    }

    public void setCodUnNegocio(long codUnNegocio) {
        this.codUnNegocio = codUnNegocio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDtEntrega() {
        return dtEntrega;
    }

    public void setDtEntrega(String dtEntrega) {
        this.dtEntrega = dtEntrega;
    }

    public String getDtPedido() {
        return dtPedido;
    }

    public void setDtPedido(String dtPedido) {
        this.dtPedido = dtPedido;
    }

    public String getFormapgto() {
        return formapgto;
    }

    public void setFormapgto(String formapgto) {
        this.formapgto = formapgto;
    }

    public Double getTotalPedido() {
        return totalPedido;
    }

    public void setTotalPedido(Double totalPedido) {
        this.totalPedido = totalPedido;
    }

    public String getDescricaofornecedor() {
        return descricaofornecedor;
    }

    public void setDescricaofornecedor(String descricaofornecedor) {
        this.descricaofornecedor = descricaofornecedor;
    }

    public PedidoCompra(long _id, long idFornecedor, String descricaofornecedor, long codEmpresa, long codUnNegocio,String email, String dtPedido,
                        String dtEntrega, Double totalPedido, String formapgto){
        this._id = _id;
        this.idFornecedor = idFornecedor;
        this.descricaofornecedor= descricaofornecedor;
        this.codEmpresa = codEmpresa;
        this.codUnNegocio = codUnNegocio;
        this.email = email;
        this.dtPedido = dtPedido;
        this.dtEntrega = dtEntrega;
        this.formapgto = formapgto;
        this.totalPedido = totalPedido;
    }

    public PedidoCompra(){

    }
}
