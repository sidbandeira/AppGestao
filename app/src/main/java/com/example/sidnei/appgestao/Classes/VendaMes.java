package com.example.sidnei.appgestao.Classes;

public class VendaMes {
    public int _id;
    public String vendames;
    public String vendaano;
    public Double vendamesvalor;
    public int codunidade;

    public VendaMes(){

    }

    public VendaMes(int _id, String vendames, String vendaano, Double vendamesvalor, int codunidade){
        this._id = _id;
        this.vendames = vendames;
        this.vendaano = vendaano;
        this.vendamesvalor = vendamesvalor;
        this.codunidade = codunidade;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getVendames() {
        return vendames;
    }

    public void setVendames(String vendames) {
        this.vendames = vendames;
    }

    public String getVendaano() {
        return vendaano;
    }

    public void setVendaano(String vendaano) {
        this.vendaano = vendaano;
    }

    public Double getVendamesvalor() {
        return vendamesvalor;
    }

    public void setVendamesvalor(Double vendamesvalor) {
        this.vendamesvalor = vendamesvalor;
    }

    public int getCodunidade() {
        return codunidade;
    }

    public void setCodunidade(int codunidade) {
        this.codunidade = codunidade;
    }

}
