package com.example.sidnei.appgestao.pedidoCompra.Repositorio;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.sidnei.appgestao.pedidoCompra.Classes.PedidoCompra;
import com.example.sidnei.appgestao.pedidoCompra.Persistencia.PedidoCompraSQLHelper;

public class PedidoCompraRepositorio {
    private PedidoCompraSQLHelper helper;

    public PedidoCompraRepositorio(Context ctx) {
        helper = new PedidoCompraSQLHelper(ctx);
    }

    private long inserir(PedidoCompra compra) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(PedidoCompraSQLHelper.COLUNA_IDEMPRESA, compra.codEmpresa);
        cv.put(PedidoCompraSQLHelper.COLUNA_IDUNIDADE, compra.codUnNegocio);
        cv.put(PedidoCompraSQLHelper.COLUNA_IDFORNECEDOR, compra.idFornecedor);
        cv.put(PedidoCompraSQLHelper.COLUNA_DATAPEDIDO, compra.dtPedido);
        cv.put(PedidoCompraSQLHelper.COLUNA_FORMAPGTO, compra.formapgto);
        cv.put(PedidoCompraSQLHelper.COLUNA_TOTALPEDIDO, compra.totalPedido);

        long id = db.insert(PedidoCompraSQLHelper.TABELA, null, cv);
        if (id != -1) {
            compra._id = id;
        }
        db.close();
        return id;
    }

    private int atualizar(PedidoCompra compra) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(PedidoCompraSQLHelper.COLUNA_ID, compra._id);
        cv.put(PedidoCompraSQLHelper.COLUNA_IDEMPRESA, compra.codEmpresa);
        cv.put(PedidoCompraSQLHelper.COLUNA_IDUNIDADE, compra.codUnNegocio);
        cv.put(PedidoCompraSQLHelper.COLUNA_IDFORNECEDOR, compra.idFornecedor);
        cv.put(PedidoCompraSQLHelper.COLUNA_DATAPEDIDO, compra.dtPedido);
        cv.put(PedidoCompraSQLHelper.COLUNA_FORMAPGTO, compra.formapgto);
        cv.put(PedidoCompraSQLHelper.COLUNA_TOTALPEDIDO, compra.totalPedido);

        int linhasAfetadas = db.update(
                PedidoCompraSQLHelper.TABELA,
                cv,
                PedidoCompraSQLHelper.COLUNA_ID +" = ?",
                new String[]{ String.valueOf(compra._id)});
        db.close();
        return linhasAfetadas;
    }

    public void salvar(PedidoCompra compra) {
        if (compra._id == 0) {
            inserir(compra);
        } else {
            atualizar(compra);
        }
    }

    public int excluir(PedidoCompra compra) {
        SQLiteDatabase db = helper.getWritableDatabase();
        int linhasAfetadas = db.delete(
                PedidoCompraSQLHelper.TABELA,
                PedidoCompraSQLHelper.COLUNA_ID +" = ?",
                new String[]{ String.valueOf(compra._id)});
        db.close();
        return linhasAfetadas;
    }

    }
