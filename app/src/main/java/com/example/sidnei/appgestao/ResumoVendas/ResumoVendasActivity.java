package com.example.sidnei.appgestao.ResumoVendas;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;

import com.example.sidnei.appgestao.Classes.VendaMes;
import com.example.sidnei.appgestao.R;
import com.example.sidnei.appgestao.utilitario.JSON;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ResumoVendasActivity extends AppCompatActivity {
    WebView wvGrafico;
    String strURL;
    JSONObject jsonobject;
    JSONArray jsonarray;
    ArrayList<String> vendaResumolist;
    ArrayList<VendaMes> vendaResumo;

    private String valorColuna = "";
    private String rotuloColuna = "";
    private String legenda = "";
    private String parametros = "";
    private String escala = "";
    private String cabecalhoGrafico = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumo_vendas);

        new DownloadJSON().execute();
    }

    // Download JSON file AsyncTask
    private class DownloadJSON extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            // Localiza a classe Fornecedor
            vendaResumo = new ArrayList<VendaMes>();
            // Cria o Array para popular o  spinner
            vendaResumolist = new ArrayList<String>();

            // CHAMA A CLASSE JSON E PASSA A URL PARA BAIXAR O ARQUIVO COM OS FORNECEDORES NO FORMATO JSON
            jsonobject = JSON.getJSONfromURL("http://sgestao.hol.es/ws/ResumoVendasWs.php?codunidade=3&ano=2017");
            try {

                // PEGA O NÓ DOS FORNECEDORES
                jsonarray = jsonobject.getJSONArray("venda");
                for (int i = 0; i < jsonarray.length(); i++) {
                    jsonobject = jsonarray.getJSONObject(i);

                    VendaMes ven = new VendaMes();
                    ven.setCodunidade(jsonobject.optInt("codunidade"));
                    ven.setVendames(jsonobject.optString("vendames"));
                    ven.setVendaano(jsonobject.optString("vendamesano"));
                    ven.setVendamesvalor(jsonobject.optDouble("vendamesvalor"));

                    vendaResumo.add(ven);
                }

                Double escalaMenor = 0.00;
                Double escalaMaior = 0.00;
                for(int i = 0; i < vendaResumo.size();i++){
                    valorColuna += ("," + vendaResumo.get(i).getVendamesvalor());

                    rotuloColuna += ("|" + RetornaMes(vendaResumo.get(i).getVendames()));

                    if(escalaMenor > vendaResumo.get(i).getVendamesvalor()){
                        escalaMenor = vendaResumo.get(i).getVendamesvalor();
                    }

                    if(escalaMaior < vendaResumo.get(i).getVendamesvalor()){
                        escalaMaior = vendaResumo.get(i).getVendamesvalor();
                    }

                }
                valorColuna = valorColuna.substring(1);
                rotuloColuna = rotuloColuna.substring(1);
                parametros =  "0," + escalaMaior.toString();
                escala = escalaMenor.toString() + "," + escalaMaior.toString();
                legenda = "Vendas";
                cabecalhoGrafico = "Total Venda/Mês";

            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            strURL = "https://chart.googleapis.com/chart?" +
                    "cht=lc&" + //define o tipo do gráfico "linha"
                    "chxt=x,y&" + //imprime os valores dos eixos X, Y
                    "chs=570x280&" + //define o tamanho da imagem
                    "chd=t:"+ valorColuna+"&" + //valor de cada coluna do gráfico
                    "chl="+rotuloColuna+"&" + //rótulo para cada coluna
                    "chdl="+legenda+"&" + //legenda do gráfico
                    "chxr=1,"+parametros+"&" + //define o valor de início e fim do eixo
                    "chds="+escala+"&" + //define o valor de escala dos dados
                    "chg=0,5,0,0&" + //desenha linha horizontal na grade
                    "chco=3D7930&" + //cor da linha do gráfico
                    "chtt=" + cabecalhoGrafico + "&" + //cabeçalho do gráfico
                    "chm=B,C5D4B5BB,0,0,0"; //fundo verde

            wvGrafico = (WebView)findViewById(R.id.wvGrafico);
            wvGrafico.loadUrl(strURL);

        }

    }
    private static String RetornaMes(String mes){
        switch (mes) {

            case "1": mes = "Jan"; break;
            case "2": mes = "Fev"; break;
            case "3": mes = "Mar"; break;
            case "4": mes = "Abr"; break;
            case "5": mes = "Mai"; break;
            case "6": mes = "Jun"; break;
            case "7": mes = "Jul"; break;
            case "8": mes = "Ago"; break;
            case "9": mes = "Set"; break;
            case "10": mes = "Out"; break;
            case "11": mes = "Nov"; break;
            case "12": mes = "Dez"; break;
            default: mes = "Inválido"; break;
        }
        return mes;
    }
}
