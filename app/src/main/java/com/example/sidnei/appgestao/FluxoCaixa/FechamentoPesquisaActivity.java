package com.example.sidnei.appgestao.FluxoCaixa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.sidnei.appgestao.R;

public class FechamentoPesquisaActivity extends AppCompatActivity {
    private EditText edtData;
    public static String dataVenda;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fechamento_pesquisa);

        edtData = (EditText) findViewById(R.id.edtData);
        dataVenda = edtData.getText().toString();
    }

    public void ibtPesquisar(View view) {
        Intent it = new Intent(this, FechamentoCaixaActivity.class);
        startActivity(it);

    }
}
