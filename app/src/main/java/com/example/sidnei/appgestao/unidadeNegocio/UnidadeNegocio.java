package com.example.sidnei.appgestao.unidadeNegocio;

import java.io.Serializable;

/**
 * Created by Sidnei on 15/03/2017.
 */
public class UnidadeNegocio implements Serializable {
    public int id;
    public String razaosocial;

    public UnidadeNegocio(){

    }

    public UnidadeNegocio(int id, String razaosocial){
        this.id = id;
        this.razaosocial = "- " + razaosocial;
    }


}
