package com.example.sidnei.appgestao.pedidoCompra.PedidoCompraMenu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.sidnei.appgestao.Postos.PostosActivity;
import com.example.sidnei.appgestao.R;
import com.example.sidnei.appgestao.pedidoCompra.PedidoCompraActivity;

public class MenuPedidoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_pedido);
    }

    public void btnNovo(View view) {
        Intent it = new Intent(this, PedidoCompraActivity.class);
        startActivity(it);
    }

    public void btnListar(View view) {
        Intent it = new Intent(this, PostosActivity.class);
        startActivity(it);
    }

}
