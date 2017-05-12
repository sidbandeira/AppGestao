package com.example.sidnei.appgestao.estoque;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sidnei.appgestao.R;
import com.squareup.picasso.Picasso;

public class ProdDetalheActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtDescricaoProd;
    private TextView txtCodBarras;
    private TextView txtPrecoCusto;
    private TextView txtPrecoVenda;
    private TextView txtSaldo;
    private ImageView imgImagem;
    private Button btVoltar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prod_detalhe);

        //Recebe o intent da classe ListarMedicos e já joga pros textview
        Intent in = getIntent();
        String descricao = in.getStringExtra("descricao");
        String codBarras = in.getStringExtra("codBarras");
        String precoCusto = in.getStringExtra("precoCusto");
        String precoVenda = in.getStringExtra("precoVenda");
        String saldo = in.getStringExtra("saldo");

        txtDescricaoProd = (TextView) findViewById(R.id.txtDescricaoProd);
        txtDescricaoProd.setText(descricao.toString());
        txtCodBarras = (TextView) findViewById(R.id.txtCodBarras);
        txtCodBarras.setText(codBarras.toString());
        txtPrecoCusto = (TextView) findViewById(R.id.txtPrecoCusto);
        txtPrecoCusto.setText(precoCusto.toString());
        txtPrecoVenda = (TextView) findViewById(R.id.txtPrecoVenda);
        txtPrecoVenda.setText(precoVenda.toString());
        txtSaldo = (TextView) findViewById(R.id.txtSaldo);
        txtSaldo.setText(saldo.toString());

        imgImagem = (ImageView) findViewById(R.id.imgImagem);

        Picasso.with(getApplicationContext()).load("http://sgestao.hol.es/img/" + codBarras +".png").into(imgImagem);

        btVoltar = (Button) findViewById(R.id.btVoltar);
        btVoltar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
