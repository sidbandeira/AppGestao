package com.example.sidnei.appgestao.pedidoCompra;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sidnei.appgestao.R;

import java.util.ArrayList;

public class AdapterFornecedor extends BaseAdapter {
    private LayoutInflater mInflater;
    private ArrayList<Fornecedor> fornecedores;

    public AdapterFornecedor(Context context, ArrayList<Fornecedor> fornecedores)
    {
        //Itens que preencheram o listview
        this.fornecedores = fornecedores;
        //responsavel por pegar o Layout do fornecedor.
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return fornecedores.size();
    }

    @Override
    public Object getItem(int position) {
        return fornecedores.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Pega o item de acordo com a posção.
        Fornecedor fornecedor = fornecedores.get(position);
        //infla o layout para podermos preencher os dados
        convertView = mInflater.inflate(R.layout.fornecedor_listagem, null);

        //atravez do layout pego pelo LayoutInflater, pegamos cada id relacionado
        //ao fornecedor e definimos as informações.
        ((TextView) convertView.findViewById(R.id.txtCodFornecedor)).setText(fornecedor.getId());
        ((TextView) convertView.findViewById(R.id.txtDescricao)).setText(fornecedor.getDescricaofornecedor().toString());

        return convertView;
    }
}

