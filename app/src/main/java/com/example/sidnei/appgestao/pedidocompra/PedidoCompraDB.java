package com.example.sidnei.appgestao.pedidoCompra;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PedidoCompraDB extends SQLiteOpenHelper{

    // CRIA O BANCO DE DADOS
    public PedidoCompraDB(Context context){
        super  (context, "dados.sqlite",null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //CRIA A TABELA PEDIDOCOMPRA
        db.execSQL("create table if not exists pedidocompra (_id integer primary key autoincrement," +
                " idFornecedor long, dtPedido text, totalPedido DOUBLE, formapgto text, codempresa long, codunnegocio long);");
        //CRIA A TABELA PEDIDOCOMPRAITEM
        db.execSQL("create table if not exists pedidocompraitem (_id integer primary key autoincrement," +
                " idCompra long ,iditem long, descricaoitem text, qtdeitem DOUBLE,precocusto DOUBLE, totalitem DOUBLE);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long gravaPedidoCompra(PedidoCompra pedido){
        SQLiteDatabase db = getWritableDatabase();
        try{
            ContentValues values = new ContentValues();
            values.put("idfornecedor", pedido.idFornecedor);
            values.put("codempresa", pedido.codEmpresa);
            values.put("codunnegocio", pedido.codUnNegocio);
            values.put("totalpedido", pedido.totalPedido);
            values.put("formapgto",pedido.formapgto);
            values.put("dtpedido",pedido.dtPedido);

            long id = db.insert("pedidocompra", null, values);
            return id;
        }finally{
            db.close();
        }
    }

    public long gravaPedidoCompraItem(PedidoCompraItem item){
        SQLiteDatabase db = getWritableDatabase();
        try{
            ContentValues values = new ContentValues();
            values.put("idcompra", item.idCompra);
            values.put("iditem", item.idItem);
            values.put("descricaoitem",item.descricaoItem);
            values.put("qtdeItem",item.qtdeItem);
            values.put("precocusto",item.precoCusto);
            values.put("totalitem",item.totalItem);

            long id = db.insert("pedidocompraitem", null, values);

            // fazer a rotina de gravação dos itens do pedido
            return id;

        }finally{
            db.close();
        }
    }
}
