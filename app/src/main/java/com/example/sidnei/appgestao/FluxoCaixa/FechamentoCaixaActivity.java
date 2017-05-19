package com.example.sidnei.appgestao.FluxoCaixa;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.sidnei.appgestao.R;

public class FechamentoCaixaActivity extends AppCompatActivity {
    public static TextView txtTotal;
    public static TextView txtDif;
    public static TextView txtDiferenca;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fechamento_caixa);
        txtTotal = (TextView) findViewById(R.id.txtTotal);
        txtDif = (TextView) findViewById(R.id.txtDif);
        txtDiferenca = (TextView) findViewById(R.id.txtDiferenca);
    }
}
