package com.example.sidnei.appgestao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    // CHAMA A TELA DE RESUMO DA VENDAS DO DIA
    public void Vendas(View view) {
        Intent it = new Intent(this,VendasActivity.class);
        startActivity(it);
    }

    //CHAMADA PARA A TELA DE RESUMO DE FECHAMENTO DE CAIXA
    public void Caixa(View view) {
        Intent it = new Intent(this, FechamentoCaixaActivity.class);
        startActivity(it);
    }

    // CHAMADA DA TELA DE ESTOQUE DE PRODUTOS
    public void Produtos(View view) {
        Intent it = new Intent(this,ProdutosActivity.class);
        startActivity(it);
    }

    // CHAMADA PARA A TELA DE ESTOQUE DOS TANQUES DE COMBUSTIVEIS - HABILITADO SOMENTE PARA POSTOS
    public void Postos(View view) {
        Intent it = new Intent(this,PostosActivity.class);
        startActivity(it);
    }
}
