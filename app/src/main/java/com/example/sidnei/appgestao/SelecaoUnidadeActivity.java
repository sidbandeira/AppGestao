package com.example.sidnei.appgestao;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SelecaoUnidadeActivity extends AppCompatActivity {
    public String menuPosto = "";
    public String menuLoja = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Intent x = getIntent();
//        menuPosto = x.getStringExtra("menuPosto");
//        menuLoja = x.getStringExtra("menuLoja");

        setContentView(R.layout.activity_selecao_unidade);
    }


}
