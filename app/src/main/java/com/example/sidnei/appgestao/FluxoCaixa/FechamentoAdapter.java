package com.example.sidnei.appgestao.FluxoCaixa;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sidnei.appgestao.Classes.Venda;
import com.example.sidnei.appgestao.R;

import java.util.List;

public class FechamentoAdapter extends ArrayAdapter<Venda> {

    public FechamentoAdapter(Context context, List<Venda> objects) {
        super(context, 0, objects);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Venda venda = getItem(position);
        FechamentoAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_fechacaixa_list, null);
            holder = new FechamentoAdapter.ViewHolder();
            holder.txtNomeVendedor = (TextView)convertView.findViewById(R.id.txtNomeVendedor);
            holder.txtTotVenda =  (TextView)convertView.findViewById(R.id.txtTotVenda);
            holder.txtTotDeclarado =  (TextView)convertView.findViewById(R.id.txtTotDeclarado);
            holder.txtTotDiferenca =  (TextView)convertView.findViewById(R.id.txtTotDiferenca);
            holder.txtDataFechamento =  (TextView)convertView.findViewById(R.id.txtDataFechamento);
            holder.txtHoraAtualiza =  (TextView)convertView.findViewById(R.id.txtHoraAtualiza);

            convertView.setTag(holder);
        } else {
            holder = (FechamentoAdapter.ViewHolder)convertView.getTag();
        }
        holder.txtNomeVendedor.setText(venda.nomevendedor);
        holder.txtTotVenda.setText(String.valueOf(String.format("%.2f", venda.totalvenda)));
        holder.txtTotDeclarado.setText(String.valueOf(String.format("%.2f", venda.declaradovenda)));
        holder.txtTotDiferenca.setText(String.format("%.2f", (venda.declaradovenda - venda.totalvenda)));

        // SE VALOR DA DIFERENÇA FOR NEGATIVO PASSA A COR DA FONTE PARA VERMELHO
        if((venda.declaradovenda - venda.totalvenda) < 0 ){
            holder.txtTotDiferenca.setTextColor(Color.parseColor("#ff0000"));
        }
        holder.txtDataFechamento.setText(venda.datavenda);
        holder.txtHoraAtualiza.setText(venda.horaatualizacao);
        return convertView;
    }

    static class ViewHolder {
        TextView txtNomeVendedor;
        TextView txtTotVenda;
        TextView txtTotDeclarado;
        TextView txtTotDiferenca;
        TextView txtDataFechamento;
        TextView txtHoraAtualiza;
    }
}
