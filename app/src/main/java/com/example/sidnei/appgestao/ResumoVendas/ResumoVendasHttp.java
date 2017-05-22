package com.example.sidnei.appgestao.ResumoVendas;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.sidnei.appgestao.Classes.VendaMes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ResumoVendasHttp {
    public static final String RESUMOVENDA_URL_JSON = "http://sgestao.hol.es/ws/ResumoVendasWs.php?codunidade=" ;

    private static HttpURLConnection connectar(String urlArquivo) throws IOException {
        final int SEGUNDOS = 1000;
        URL url = new URL(urlArquivo);
        HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
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

    public static List<VendaMes> carregarResumoJson(Integer codunidade, String ano) {
        try {

            HttpURLConnection conexao = connectar(RESUMOVENDA_URL_JSON + codunidade + "&ano=" + ano);
            int resposta = conexao.getResponseCode();
            if (resposta == HttpURLConnection.HTTP_OK) {
                InputStream is = conexao.getInputStream();
                JSONObject json = new JSONObject(bytesParaString(is));
                return lerJsonResumoVenda(json);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<VendaMes> lerJsonResumoVenda(JSONObject json) throws JSONException {
        List<VendaMes> listadeVenda = new ArrayList<VendaMes>();
        //OBJETO PAI DO JSON
        JSONArray jsonVend = json.getJSONArray("vendas");
        for (int i = 0; i < jsonVend.length(); i++) {
            JSONObject jsonVenda = jsonVend.getJSONObject(i);
            VendaMes vend = new VendaMes();
            vend.codunidade = jsonVenda.getInt("codunidade");
            vend.vendames = jsonVenda.getString("vendames");
            vend.vendaano = jsonVenda.getString("vendamesano");
            vend.vendamesvalor = jsonVenda.getDouble("vendamesvalor");

//            vend.datavenda =  jsonVenda.getString("datavenda");
//            vend.totalvenda =  jsonVenda.getDouble("totalvenda");
//            vend.declaradovenda = jsonVenda.getDouble("declaradovenda");
//            vend.horaatualizacao = jsonVenda.getString("horaatualizacao");
//            vend.nomevendedor = jsonVenda.getString("nomevendedor");
            listadeVenda.add(vend);
        }
        return listadeVenda;
    }

    private static String bytesParaString(InputStream is) throws IOException {
        byte[] buffer = new byte[1024];
        // O bufferzao vai armazenar todos os bytes lidos
        ByteArrayOutputStream bufferzao = new ByteArrayOutputStream();
        // precisamos saber quantos bytes foram lidos
        int bytesLidos;
        // Vamos lendo de 1KB por vez...
        while ((bytesLidos = is.read(buffer)) != -1) {
            // copiando a quantidade de bytes lidos do buffer para o bufferzão
            bufferzao.write(buffer, 0, bytesLidos);
        }
        return new String(bufferzao.toByteArray(), "UTF-8");
    }
}
