package com.example.sidnei.appgestao.pedidoCompra;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.sidnei.appgestao.R;

public class PedidoCompraActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnProdutos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_compra);

        btnProdutos = (Button) findViewById(R.id.btnProdutos);
        btnProdutos.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent it = new Intent(this, PedidoCompraItemActivity.class);
        startActivity(it);

    }
}
