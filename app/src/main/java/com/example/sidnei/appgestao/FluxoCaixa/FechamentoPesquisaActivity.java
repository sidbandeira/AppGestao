package com.example.sidnei.appgestao.FluxoCaixa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.sidnei.appgestao.R;
import com.example.sidnei.appgestao.utilitario.Mascara;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FechamentoPesquisaActivity extends AppCompatActivity {
    private EditText edtData;
    public static String dataVenda;
    private String dataFormatadaTela;
    public static String dataFormatadaBanco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fechamento_pesquisa);

        //FORMATA E SETA A DATA DO DIA
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        dataFormatadaTela = sdf.format(date);

        edtData = (EditText) findViewById(R.id.edtData);
        edtData.addTextChangedListener(Mascara.insert(Mascara.MaskType.DATA,  edtData));
        edtData.setText(dataFormatadaTela);
    }

    public void ibtPesquisar(View view) {
        //SimpleDateFormat sdfBD = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = sdf2.parse(edtData.getText().toString());
            String dataParaBanco = new SimpleDateFormat("yyyy/MM/dd").format(date);
            dataParaBanco = dataParaBanco.replace("/","-");
            dataFormatadaBanco = dataParaBanco;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Intent it = new Intent(this, FechamentoCaixaActivity.class);
        startActivity(it);

    }
}
