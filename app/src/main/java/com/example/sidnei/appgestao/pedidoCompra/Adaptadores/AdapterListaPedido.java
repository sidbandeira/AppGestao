package com.example.sidnei.appgestao.pedidoCompra.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sidnei.appgestao.Classes.PedidoCompra;
import com.example.sidnei.appgestao.R;

import java.util.ArrayList;

public class AdapterListaPedido extends BaseAdapter {
    private LayoutInflater mInflater;
    private ArrayList<PedidoCompra> pedidos;

    public AdapterListaPedido(Context context, ArrayList<PedidoCompra> pedidos)
    {
        //Itens que preencheram o listview
        this.pedidos = pedidos;
        //responsavel por pegar o Layout do item.
        mInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return pedidos.size();
    }

    @Override
    public Object getItem(int position) {
        return pedidos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Pega o item de acordo com a posção.
        PedidoCompra ped = pedidos.get(position);
        //infla o layout para podermos preencher os dados
        convertView = mInflater.inflate(R.layout.item_pedido_lista, null);

        //atravez do layout pego pelo LayoutInflater, pegamos cada id relacionado
        //ao item e definimos as informações.
        ((TextView) convertView.findViewById(R.id.txtCodPedido)).setText(String.valueOf(ped._id));
        ((TextView) convertView.findViewById(R.id.txtFornecedorPedido)).setText(ped.descricaofornecedor);
        ((TextView) convertView.findViewById(R.id.txtValorPedido)).setText(ped.totalPedido.toString());
        ((TextView) convertView.findViewById(R.id.txtDataEntrega)).setText(ped.dtEntrega);

        return convertView;
    }
}
