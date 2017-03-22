package com.example.sidnei.appgestao;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    private Button btnVendas;
    private Button btnCaixa;
    private Button btnProdutos;
    private Button btnPostos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        btnCaixa = (Button) findViewById(R.id.btnCaixa);
        btnVendas = (Button) findViewById(R.id.btnVendas);
        btnProdutos = (Button) findViewById(R.id.btnProdutos);
        btnPostos = (Button) findViewById(R.id.btnPostos);

        if(MainActivity.menuLoja ==  0){
            btnVendas.setVisibility(View.INVISIBLE);
            btnCaixa.setVisibility(View.INVISIBLE);
            btnProdutos.setVisibility(View.INVISIBLE);
        }

        if(MainActivity.menuPosto == 0){
            btnPostos.setVisibility(View.INVISIBLE);
        }
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
