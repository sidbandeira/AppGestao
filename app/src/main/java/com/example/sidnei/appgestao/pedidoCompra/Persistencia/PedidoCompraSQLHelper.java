package com.example.sidnei.appgestao.pedidoCompra.Persistencia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PedidoCompraSQLHelper extends SQLiteOpenHelper {
    private static final String NOME_BANCO = "dbgestao";
    private static final int VERSAO_BANCO = 1;
    public static final String TABELA = "pedidocompra";
    public static final String COLUNA_ID = "_id";
    public static final String COLUNA_IDFORNECEDOR = "idfornecedor";
    public static final String COLUNA_IDEMPRESA = "codempresa";
    public static final String COLUNA_IDUNIDADE = "codunnegocio";
    public static final String COLUNA_DATAPEDIDO = "dtpedido";
    public static final String COLUNA_FORMAPGTO = "formapgto";
    public static final String COLUNA_TOTALPEDIDO = "totalpedido";

    public PedidoCompraSQLHelper(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "CREATE TABLE "+ TABELA +" (" +
                        COLUNA_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                        COLUNA_IDFORNECEDOR +" INTEGER,"+
                        COLUNA_IDEMPRESA +" INTEGER,"+
                        COLUNA_IDUNIDADE +" INTEGER,"+
                        COLUNA_FORMAPGTO +" TEXT,"+
                        COLUNA_DATAPEDIDO +" TEXT,"+
                        COLUNA_TOTALPEDIDO +" FLOAT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
