package com.example.sidnei.appgestao.Classes;

public class Tanque {
    public int codTanque;
    public String tipoCombustivel;
    public double capacidadeTanque;
    public double estoqueTanque;
    public int codImagem;

    public Tanque(){

    }

    public Tanque(int codTanque, String tipoCombustivel, double capacidadeTanque, double estoqueTanque,int codImagem){
        this.codTanque = codTanque;
        this.tipoCombustivel = tipoCombustivel;
        this.capacidadeTanque = capacidadeTanque;
        this.estoqueTanque = estoqueTanque;
        this.codImagem = codImagem;

    }

    public int getCodTanque() {
        return codTanque;
    }

    public void setCodTanque(int codTanque) {
        this.codTanque = codTanque;
    }

    public String getTipoCombustivel() {
        return tipoCombustivel;
    }

    public void setTipoCombustivel(String tipoCombustivel) {
        this.tipoCombustivel = tipoCombustivel;
    }

    public double getCapacidadeTanque() {
        return capacidadeTanque;
    }

    public void setCapacidadeTanque(double capacidadeTanque) {
        this.capacidadeTanque = capacidadeTanque;
    }

    public double getEstoqueTanque() {
        return estoqueTanque;
    }

    public void setEstoqueTanque(double estoqueTanque) {
        this.estoqueTanque = estoqueTanque;
    }

    public int getCodImagem() {
        return codImagem;
    }

    public void setCodImagem(int codImagem) {
        this.codImagem = codImagem;
    }


}
