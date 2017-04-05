package com.example.sidnei.appgestao.tanque;

public class Tanque {
    public int codTanque;
    public String tipoCombustivel;
    public double capacidadeTanque;
    public double estoqueTanque;
    public int codImagem;

    public Tanque(int codTanque, String tipoCombustivel, double capacidadeTanque, double estoqueTanque,int codImagem){
        this.codTanque = codTanque;
        this.tipoCombustivel = tipoCombustivel;
        this.capacidadeTanque = capacidadeTanque;
        this.estoqueTanque = estoqueTanque;
        this.codImagem = codImagem;

    }
}
