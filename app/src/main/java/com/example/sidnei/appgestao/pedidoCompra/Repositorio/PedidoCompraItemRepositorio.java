package com.example.sidnei.appgestao.pedidoCompra.Repositorio;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.sidnei.appgestao.Classes.PedidoCompraItem;
import com.example.sidnei.appgestao.pedidoCompra.Persistencia.PedidoCompraSQLHelper;

public class PedidoCompraItemRepositorio {
    private PedidoCompraSQLHelper helper;

    //CONSTRUTOR
    public PedidoCompraItemRepositorio(Context ctx) {
        helper = new PedidoCompraSQLHelper(ctx);
    }

    //INSERIR ITENS DO PEDIDO DE COMPRA
    private long inserir(PedidoCompraItem compraItem) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(PedidoCompraSQLHelper.COLUNA_IDCOMPRA, compraItem.idCompra);
        cv.put(PedidoCompraSQLHelper.COLUNA_IDPRODUTO, compraItem.idItem);
        cv.put(PedidoCompraSQLHelper.COLUNA_DESCRICAOPRODUTO, compraItem.descricaoItem);
        cv.put(PedidoCompraSQLHelper.COLUNA_PRECOCUSTO, compraItem.precoCusto);
        cv.put(PedidoCompraSQLHelper.COLUNA_QTDEPRODUTO, compraItem.qtdeItem);
        cv.put(PedidoCompraSQLHelper.COLUNA_TOTALPRODUTO, compraItem.totalItem);


        long id = db.insert(PedidoCompraSQLHelper.TABELAITEM, null, cv);
        if (id != -1) {
            compraItem._id = id;
        }
        db.close();
        return id;
    }

    //EDITAR ITENS DO PEDIDO DE COMPRA
    private int atualizar(PedidoCompraItem compraItem) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(PedidoCompraSQLHelper.COLUNA_IDPEDIDOITEM, compraItem._id);
        cv.put(PedidoCompraSQLHelper.COLUNA_IDCOMPRA, compraItem.idCompra);
        cv.put(PedidoCompraSQLHelper.COLUNA_IDPRODUTO, compraItem.idItem);
        cv.put(PedidoCompraSQLHelper.COLUNA_DESCRICAOPRODUTO, compraItem.descricaoItem);
        cv.put(PedidoCompraSQLHelper.COLUNA_PRECOCUSTO, compraItem.precoCusto);
        cv.put(PedidoCompraSQLHelper.COLUNA_QTDEPRODUTO, compraItem.qtdeItem);
        cv.put(PedidoCompraSQLHelper.COLUNA_TOTALPRODUTO, compraItem.totalItem);

        int linhasAfetadas = db.update(
                PedidoCompraSQLHelper.TABELAITEM,
                cv,
                PedidoCompraSQLHelper.COLUNA_IDPEDIDOITEM +" = ?",
                new String[]{ String.valueOf(compraItem._id)});
        db.close();
        return linhasAfetadas;
    }

    //VERIFICAR O TIPO DE GRAVAÇÃO SE EDIÇÃO OU INSERSÃO
    public void salvar(PedidoCompraItem compraItem) {
        if (compraItem._id == 0) {
            inserir(compraItem);
        } else {
            atualizar(compraItem);
        }
    }

    //EXCLUIR ITENS DE UM PEDIDO DE COMPRA
    public int excluir(PedidoCompraItem compraItem) {
        SQLiteDatabase db = helper.getWritableDatabase();
        int linhasAfetadas = db.delete(
                PedidoCompraSQLHelper.TABELA,
                PedidoCompraSQLHelper.COLUNA_IDPEDIDOITEM +" = ?",
                new String[]{ String.valueOf(compraItem._id)});
        db.close();
        return linhasAfetadas;
    }

}
