package com.example.sidnei.appgestao.Classes;

import java.io.Serializable;

public class UnidadeNegocio implements Serializable {
    public int id;
    public int codunidade;
    public String razaosocial;

    public UnidadeNegocio(){

    }

    public UnidadeNegocio(int codunidade, String razaosocial){
        this.codunidade = codunidade;
        this.razaosocial = "- " + razaosocial;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCodunidade() {
        return codunidade;
    }

    public void setCodunidade(int codunidade) {
        this.codunidade = codunidade;
    }

    public String getRazaosocial() {
        return razaosocial;
    }

    public void setRazaosocial(String razaosocial) {
        this.razaosocial = razaosocial;
    }

}
