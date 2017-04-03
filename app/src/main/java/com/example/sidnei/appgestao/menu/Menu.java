package com.example.sidnei.appgestao.menu;

import java.io.Serializable;

public class Menu implements Serializable {
    public String descricao;

    public Menu(){

    }

    public Menu(String descricao){
        this.descricao = descricao;
    }

}