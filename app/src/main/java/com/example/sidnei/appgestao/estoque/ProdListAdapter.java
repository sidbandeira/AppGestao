package com.example.sidnei.appgestao.estoque;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sidnei.appgestao.Classes.Produto;
import com.example.sidnei.appgestao.R;

import java.util.List;

public class ProdListAdapter extends ArrayAdapter<Produto> {
    public ProdListAdapter(Context context, List<Produto> objects) {
        super(context, 0, objects);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Produto produto = getItem(position);
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_prod_list, null);
            holder = new ViewHolder();
            holder.txtId = (TextView)convertView.findViewById(R.id.txtId);
            holder.txtDescricaoProd =  (TextView)convertView.findViewById(R.id.txtDescricaoProd);
            holder.txtVendaProduto =  (TextView)convertView.findViewById(R.id.txtVendaProduto);
            holder.txtCustoProd =  (TextView)convertView.findViewById(R.id.txtCustoProd);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.txtId.setText(String.valueOf(produto._id));
        holder.txtDescricaoProd.setText(produto.produtoDescricao);
        holder.txtVendaProduto.setText(produto.produtoPrecoVenda.toString());
        holder.txtCustoProd.setText(produto.produtoPrecoCusto.toString());

        return convertView;
    }

    static class ViewHolder {
        TextView txtId;
        TextView txtDescricaoProd;
        TextView txtVendaProduto;
        TextView txtCustoProd;

    }
}
