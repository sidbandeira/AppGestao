package com.example.sidnei.appgestao.pedidoCompra.PedidoCompraMenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.sidnei.appgestao.R;
import com.example.sidnei.appgestao.pedidoCompra.ListaPedidoCompraActivity;
import com.example.sidnei.appgestao.pedidoCompra.PedidoCompraActivity;

public class MenuPedidoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_pedido);
    }

    // cHAMA A TELA DE LANÇAMENTO DE PEDIDOS.
    public void btnNovo(View view) {
        Intent it = new Intent(this, PedidoCompraActivity.class);
        startActivity(it);
    }

    // LISTA OS PEDIDOS GERADOS
    public void btnListar(View view) {
        Intent it2 = new Intent(this, ListaPedidoCompraActivity.class);
        it2.putExtra("tela", "listagem");
        startActivityForResult(it2, 2);
    }

}
