package com.example.sidnei.appgestao.pedidoCompra.Repositorio;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.sidnei.appgestao.pedidoCompra.Classes.PedidoCompraItem;
import com.example.sidnei.appgestao.pedidoCompra.Persistencia.PedidoCompraItemSQLHelper;
import com.example.sidnei.appgestao.pedidoCompra.Persistencia.PedidoCompraSQLHelper;

public class PedidoCompraItemRepositorio {
    private PedidoCompraItemSQLHelper helper;

    public PedidoCompraItemRepositorio(Context ctx) {
        helper = new PedidoCompraItemSQLHelper(ctx);
    }

    private long inserir(PedidoCompraItem compraItem) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(PedidoCompraItemSQLHelper.COLUNA_IDCOMPRA, compraItem.idCompra);
        cv.put(PedidoCompraItemSQLHelper.COLUNA_IDPRODUTO, compraItem.idItem);
        cv.put(PedidoCompraItemSQLHelper.COLUNA_DESCRICAOPRODUTO, compraItem.descricaoItem);
        cv.put(PedidoCompraItemSQLHelper.COLUNA_PRECOCUSTO, compraItem.precoCusto);
        cv.put(PedidoCompraItemSQLHelper.COLUNA_QTDEPRODUTO, compraItem.qtdeItem);
        cv.put(PedidoCompraItemSQLHelper.COLUNA_TOTALPRODUTO, compraItem.totalItem);

        long id = db.insert(PedidoCompraItemSQLHelper.TABELA, null, cv);
        if (id != -1) {
            compraItem._id = id;
        }
        db.close();
        return id;
    }

    private int atualizar(PedidoCompraItem compraItem) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(PedidoCompraSQLHelper.COLUNA_ID, compraItem._id);
        cv.put(PedidoCompraItemSQLHelper.COLUNA_IDCOMPRA, compraItem.idCompra);
        cv.put(PedidoCompraItemSQLHelper.COLUNA_IDPRODUTO, compraItem.idItem);
        cv.put(PedidoCompraItemSQLHelper.COLUNA_DESCRICAOPRODUTO, compraItem.descricaoItem);
        cv.put(PedidoCompraItemSQLHelper.COLUNA_PRECOCUSTO, compraItem.precoCusto);
        cv.put(PedidoCompraItemSQLHelper.COLUNA_QTDEPRODUTO, compraItem.qtdeItem);
        cv.put(PedidoCompraItemSQLHelper.COLUNA_TOTALPRODUTO, compraItem.totalItem);

        int linhasAfetadas = db.update(
                PedidoCompraItemSQLHelper.TABELA,
                cv,
                PedidoCompraItemSQLHelper.COLUNA_ID +" = ?",
                new String[]{ String.valueOf(compraItem._id)});
        db.close();
        return linhasAfetadas;
    }

    public void salvar(PedidoCompraItem compraItem) {
        if (compraItem._id == 0) {
            inserir(compraItem);
        } else {
            atualizar(compraItem);
        }
    }

    public int excluir(PedidoCompraItem compraItem) {
        SQLiteDatabase db = helper.getWritableDatabase();
        int linhasAfetadas = db.delete(
                PedidoCompraSQLHelper.TABELA,
                PedidoCompraSQLHelper.COLUNA_ID +" = ?",
                new String[]{ String.valueOf(compraItem._id)});
        db.close();
        return linhasAfetadas;
    }

}
