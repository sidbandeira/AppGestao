package com.example.sidnei.appgestao.pedidoCompra;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.sidnei.appgestao.R;
import com.example.sidnei.appgestao.pedidoCompra.Repositorio.PedidoCompraRepositorio;

import java.util.ArrayList;
import java.util.List;

public class ListaPedidoCompraActivity extends AppCompatActivity {
    private ListView listaPedidoCompra;
    ArrayAdapter<String> adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pedido_compra);
        String linha = "";
        listaPedidoCompra = (ListView)findViewById(R.id.listaPedidoCompra);

        PedidoCompraRepositorio rep = new PedidoCompraRepositorio(this);
        Cursor cursor = rep.carregaDados();

        List<String> pedidocompra = new ArrayList<String>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                //"idfornecedor, totalpedido, formapgto, dtpedido"
                linha = cursor.getString(cursor.getColumnIndexOrThrow("_id")) + "- "
                        + cursor.getString(cursor.getColumnIndexOrThrow("descricaofornecedor"));
                pedidocompra.add(linha);
                cursor.moveToNext();
            }
        }
        cursor.close();

        adaptador = new ArrayAdapter<String>(ListaPedidoCompraActivity.this, android.R.layout.simple_list_item_1,
                (List<String>) pedidocompra);

        listaPedidoCompra.setAdapter(adaptador);
    }
}
