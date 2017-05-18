package com.example.sidnei.appgestao.Classes;

public class Venda {
    public int _id;
    public String datavenda;
    public Double totalvenda;
    public Double declaradovenda;
    public String horaatualizacao;
    public String nomevendedor;
    public long codUnNegocio;

    public Venda(int _id, String datavenda, Double totalvenda, Double declaradovenda, String horaatualizacao, String nomevendedor, long codUnNegocio) {
        this._id = _id;
        this.datavenda = datavenda;
        this.totalvenda = totalvenda;
        this.declaradovenda = declaradovenda;
        this.horaatualizacao = horaatualizacao;
        this.nomevendedor = nomevendedor;
        this.codUnNegocio = codUnNegocio;
    }

    public Venda(){
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getDatavenda() {
        return datavenda;
    }

    public void setDatavenda(String datavenda) {
        this.datavenda = datavenda;
    }

    public Double getTotalvenda() {
        return totalvenda;
    }

    public void setTotalvenda(Double totalvenda) {
        this.totalvenda = totalvenda;
    }

    public Double getDeclaradovenda() {
        return declaradovenda;
    }

    public void setDeclaradovenda(Double declaradovenda) {
        this.declaradovenda = declaradovenda;
    }

    public String getHoraatualizacao() {
        return horaatualizacao;
    }

    public void setHoraatualizacao(String horaatualizacao) {
        this.horaatualizacao = horaatualizacao;
    }

    public String getNomevendedor() {
        return nomevendedor;
    }

    public void setNomevendedor(String nomevendedor) {
        this.nomevendedor = nomevendedor;
    }

    public long getCodUnNegocio() {
        return codUnNegocio;
    }

    public void setCodUnNegocio(long codUnNegocio) {
        this.codUnNegocio = codUnNegocio;
    }

}
