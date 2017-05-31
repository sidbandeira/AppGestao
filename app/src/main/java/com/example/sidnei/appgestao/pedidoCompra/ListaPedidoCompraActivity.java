package com.example.sidnei.appgestao.pedidoCompra;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.sidnei.appgestao.Classes.PedidoCompra;
import com.example.sidnei.appgestao.R;
import com.example.sidnei.appgestao.pedidoCompra.Adaptadores.AdapterListaPedido;
import com.example.sidnei.appgestao.pedidoCompra.Repositorio.PedidoCompraRepositorio;

import java.util.ArrayList;
import java.util.List;

public class ListaPedidoCompraActivity extends AppCompatActivity {
    private ListView listaPedidoCompra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pedido_compra);
        listaPedidoCompra = (ListView)findViewById(R.id.listaPedidoCompra);

        PedidoCompraRepositorio rep = new PedidoCompraRepositorio(this);
        Cursor cursor = rep.carregaDados();
        List<PedidoCompra> pedido = new ArrayList<PedidoCompra>();
        if (cursor.getCount()> 0){
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    PedidoCompra ped = new PedidoCompra();
                    ped._id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                    ped.descricaofornecedor = cursor.getString(cursor.getColumnIndexOrThrow("descricaofornecedor"));
                    ped.dtEntrega = cursor.getString(cursor.getColumnIndexOrThrow("dtentrega"));
                    ped.totalPedido = cursor.getDouble(cursor.getColumnIndexOrThrow("totalpedido"));
                    pedido.add(ped);
                    cursor.moveToNext();
                }
            }
        }
        cursor.close();
        listaPedidoCompra.setAdapter(new AdapterListaPedido(this, (ArrayList<PedidoCompra>) pedido));
    }
}
