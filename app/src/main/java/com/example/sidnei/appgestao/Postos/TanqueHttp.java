package com.example.sidnei.appgestao.Postos;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class TanqueHttp {
    //public static final String urlArquivo = "http://sgestao.hol.es/suporte.json";
    public static final String TANQUES_URL_JSON = "http://sgestao.hol.es/ws/TanqueWs.php?codunidade=";

    public static HttpURLConnection connectar(Integer codunidade) throws IOException {
        final int SEGUNDOS = 1000;
        URL url = new URL(TANQUES_URL_JSON + codunidade);
        HttpURLConnection conexao = (HttpURLConnection)url.openConnection();
        conexao.setReadTimeout(10 * SEGUNDOS);
        conexao.setConnectTimeout(15 * SEGUNDOS);
        conexao.setRequestMethod("GET");
        conexao.setDoInput(true);
        conexao.setDoOutput(false);
        conexao.connect();
        return conexao;
    }

    public static boolean temConexao(Context ctx) {
        ConnectivityManager cm = (ConnectivityManager)
                ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }
}
