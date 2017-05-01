package com.example.sidnei.appgestao.pedidoCompra.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sidnei.appgestao.R;
import com.example.sidnei.appgestao.pedidoCompra.Classes.PedidoCompraItem;

import java.util.ArrayList;

public class AdapterItemCompra extends BaseAdapter{
    private LayoutInflater mInflater;
    private ArrayList<PedidoCompraItem> itens;

    public AdapterItemCompra(Context context, ArrayList<PedidoCompraItem> itens)
    {
        //Itens que preencheram o listview
        this.itens = itens;
        //responsavel por pegar o Layout do item.
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return itens.size();
    }

    @Override
    public PedidoCompraItem getItem(int position)
    {
        return itens.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Pega o item de acordo com a posção.
        PedidoCompraItem item = itens.get(position);
        //infla o layout para podermos preencher os dados
        convertView = mInflater.inflate(R.layout.item_listagem_pedido, null);

        //atravez do layout pego pelo LayoutInflater, pegamos cada id relacionado
        //ao item e definimos as informações.
        ((TextView) convertView.findViewById(R.id.txtDescricao)).setText(item.getDescricaoItem());
        ((TextView) convertView.findViewById(R.id.txtUnit)).setText(item.getPrecoCusto().toString());
        ((TextView) convertView.findViewById(R.id.txtQtde)).setText(item.getQtdeItem().toString());
        ((TextView) convertView.findViewById(R.id.txtTotal)).setText(item.getTotalItem().toString());

        return convertView;
    }
}
