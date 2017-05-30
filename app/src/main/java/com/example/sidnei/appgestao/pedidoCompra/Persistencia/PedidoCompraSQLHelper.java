package com.example.sidnei.appgestao.pedidoCompra.Persistencia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PedidoCompraSQLHelper extends SQLiteOpenHelper {
    // VARIAVEIS PARA CRIAÇÃO DO BANCO DE DADOS
    private static final String NOME_BANCO = "dbgestao";
    private static final int VERSAO_BANCO = 1;

    // VARIAVEIS PARA PERSISTENCIA DA TABELA PEDIDO COMPRA
    public static final String TABELA = "pedidocompra";
    public static final String COLUNA_IDPEDIDO = "_id";
    public static final String COLUNA_IDFORNECEDOR = "idfornecedor";
    public static final String COLUNA_DESCRICAOFORNECEDOR = "descricaofornecedor";
    public static final String COLUNA_IDEMPRESA = "codempresa";
    public static final String COLUNA_IDUNIDADE = "codunnegocio";
    public static final String COLUNA_EMAIL = "email";
    public static final String COLUNA_DATAPEDIDO = "dtpedido";
    public static final String COLUNA_DATAENTREGA = "dtentrega";
    public static final String COLUNA_FORMAPGTO = "formapgto";
    public static final String COLUNA_TOTALPEDIDO = "totalpedido";

    // VARIAVEIS PARA PERSISTENCIA DA TABELA PEDIDOCOMPRAITEM
    public static final String TABELAITEM = "pedidocompraitem";
    public static final String COLUNA_IDPEDIDOITEM = "_id";
    public static final String COLUNA_IDCOMPRA = "idcompra";
    public static final String COLUNA_IDPRODUTO = "iditem";
    public static final String COLUNA_DESCRICAOPRODUTO = "descricaoitem";
    public static final String COLUNA_QTDEPRODUTO = "qtdeitem";
    public static final String COLUNA_PRECOCUSTO = "precocusto";
    public static final String COLUNA_TOTALPRODUTO = "totalitem";

    // CONSTRUTOR
    public PedidoCompraSQLHelper(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // CRIA A TABELA PEDIDOCOMPRA
        sqLiteDatabase.execSQL(
                "CREATE TABLE "+ TABELA +" (" +
                        COLUNA_IDPEDIDO +" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                        COLUNA_IDFORNECEDOR +" INTEGER,"+
                        COLUNA_DESCRICAOFORNECEDOR +" TEXT,"+
                        COLUNA_IDEMPRESA +" INTEGER,"+
                        COLUNA_IDUNIDADE +" INTEGER,"+
                        COLUNA_EMAIL +" TEXT,"+
                        COLUNA_FORMAPGTO +" TEXT,"+
                        COLUNA_DATAPEDIDO +" TEXT,"+
                        COLUNA_DATAENTREGA +" TEXT,"+
                        COLUNA_TOTALPEDIDO +" FLOAT)");

        // CRIA A TABELA PEDIDOCOMPRAIEM
        sqLiteDatabase.execSQL(
                "CREATE TABLE "+ TABELAITEM +" (" +
                        COLUNA_IDPEDIDOITEM +" INTEGER PRIMARY KEY AUTOINCREMENT,"+
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
