package com.example.sidnei.appgestao.pedidoCompra.Persistencia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Sidnei on 28/04/2017.
 */

public class PedidoCompraItemSQLHelper extends SQLiteOpenHelper {
    private static final String NOME_BANCO = "dbgestao";
    private static final int VERSAO_BANCO = 1;
    public static final String TABELA = "pedidocompraitem";
    public static final String COLUNA_ID = "_id";
    public static final String COLUNA_IDCOMPRA = "idcompra";
    public static final String COLUNA_IDPRODUTO = "iditem";
    public static final String COLUNA_DESCRICAOPRODUTO = "descricaoitem";
    public static final String COLUNA_QTDEPRODUTO = "qtdeitem";
    public static final String COLUNA_PRECOCUSTO = "precocusto";
    public static final String COLUNA_TOTALPRODUTO = "totalitem";

    public PedidoCompraItemSQLHelper(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "CREATE TABLE "+ TABELA +" (" +
                        COLUNA_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                        COLUNA_IDCOMPRA +" INTEGER,"+
                        COLUNA_IDPRODUTO +" INTEGER,"+
                        COLUNA_DESCRICAOPRODUTO +" TEXT,"+
                        COLUNA_QTDEPRODUTO +" FLOAT,"+
                        COLUNA_PRECOCUSTO +" FLOAT,"+
                        COLUNA_TOTALPRODUTO +" FLOAT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
