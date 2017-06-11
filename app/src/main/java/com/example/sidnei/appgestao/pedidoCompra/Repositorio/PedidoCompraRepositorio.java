package com.example.sidnei.appgestao.pedidoCompra.Repositorio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sidnei.appgestao.Classes.PedidoCompra;
import com.example.sidnei.appgestao.pedidoCompra.Persistencia.PedidoCompraSQLHelper;
import com.example.sidnei.appgestao.unidadeNegocio.UnidadeNegocioListFragment;

public class PedidoCompraRepositorio {
    private PedidoCompraSQLHelper helper;

    //CONSTRUTOR
    public PedidoCompraRepositorio(Context ctx) {
        helper = new PedidoCompraSQLHelper(ctx);
    }

    //INSERIR UM PEDIDO DE COMPRA
    private long inserir(PedidoCompra compra) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(PedidoCompraSQLHelper.COLUNA_IDEMPRESA, compra.codEmpresa);
        cv.put(PedidoCompraSQLHelper.COLUNA_IDUNIDADE, compra.codUnNegocio);
        cv.put(PedidoCompraSQLHelper.COLUNA_IDFORNECEDOR, compra.idFornecedor);
        cv.put(PedidoCompraSQLHelper.COLUNA_DESCRICAOFORNECEDOR, compra.descricaofornecedor);
        cv.put(PedidoCompraSQLHelper.COLUNA_EMAIL, compra.email);
        cv.put(PedidoCompraSQLHelper.COLUNA_DATAPEDIDO, compra.dtPedido);
        cv.put(PedidoCompraSQLHelper.COLUNA_DATAENTREGA, compra.dtEntrega);
        cv.put(PedidoCompraSQLHelper.COLUNA_FORMAPGTO, compra.formapgto);
        cv.put(PedidoCompraSQLHelper.COLUNA_TOTALPEDIDO, compra.totalPedido);

        long id = db.insert(PedidoCompraSQLHelper.TABELA, null, cv);
        if (id != -1) {
            compra._id = id;
        }
        db.close();
        return id;
    }

    //EDITAR UM PEDIDO DE COMPRA
    private int atualizar(PedidoCompra compra) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(PedidoCompraSQLHelper.COLUNA_IDPEDIDO, compra._id);
        cv.put(PedidoCompraSQLHelper.COLUNA_IDEMPRESA, compra.codEmpresa);
        cv.put(PedidoCompraSQLHelper.COLUNA_IDUNIDADE, compra.codUnNegocio);
        cv.put(PedidoCompraSQLHelper.COLUNA_IDFORNECEDOR, compra.idFornecedor);
        cv.put(PedidoCompraSQLHelper.COLUNA_DESCRICAOFORNECEDOR, compra.descricaofornecedor);
        cv.put(PedidoCompraSQLHelper.COLUNA_EMAIL, compra.email);
        cv.put(PedidoCompraSQLHelper.COLUNA_DATAPEDIDO, compra.dtPedido);
        cv.put(PedidoCompraSQLHelper.COLUNA_DATAENTREGA, compra.dtEntrega);
        cv.put(PedidoCompraSQLHelper.COLUNA_FORMAPGTO, compra.formapgto);
        cv.put(PedidoCompraSQLHelper.COLUNA_TOTALPEDIDO, compra.totalPedido);

        int linhasAfetadas = db.update(
                PedidoCompraSQLHelper.TABELA,
                cv,
                PedidoCompraSQLHelper.COLUNA_IDPEDIDO +" = ?",
                new String[]{ String.valueOf(compra._id)});
        db.close();
        return linhasAfetadas;
    }

    //VERIFICAR O TIPO DE GRAVAÇÃO SE EDIÇÃO OU INSERSÃO
    public void salvar(PedidoCompra compra) {
        if (compra._id == 0) {
            inserir(compra);
        } else {
            atualizar(compra);
        }
    }

    //EXCLUIR UM PEDIDO DE COMPRA
    public int excluir(Integer cod) {
        SQLiteDatabase db = helper.getWritableDatabase();
        int linhasAfetadas = db.delete(
                PedidoCompraSQLHelper.TABELA,
                PedidoCompraSQLHelper.COLUNA_IDPEDIDO    +" = ?",
                new String[]{ String.valueOf(cod)});
        db.close();
        return linhasAfetadas;
    }

    public Cursor carregaDados(){
        Cursor cursor;
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] campos =  {"_id, idfornecedor, descricaofornecedor, totalpedido, formapgto, dtpedido, dtentrega"};
        String condicao = "codunnegocio = " + String.valueOf(UnidadeNegocioListFragment.codUnidade.toString());
        cursor = db.query("pedidocompra", campos, condicao, null , null, null, null, null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

}
