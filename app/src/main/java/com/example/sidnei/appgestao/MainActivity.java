package com.example.sidnei.appgestao;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtUsuario;
    private EditText edtSenha;
    private Button btnLogar;
    private CheckBox chkLembrar;
    public static Integer codEmpresa = 0;
    public static Integer menuPosto = 0;
    public static Integer menuLoja = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtUsuario = (EditText) findViewById(R.id.edtUsuario);
        edtSenha = (EditText) findViewById(R.id.edtSenha);

        btnLogar = (Button) findViewById(R.id.btnLogar);
        chkLembrar = (CheckBox) findViewById(R.id.chkLembrar);

        chkLembrar.setChecked(true);
        edtUsuario.setText("sidnei@gmail.com");
        edtSenha.setText("123456");

        btnLogar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (isOnline()) {
            if (edtUsuario.getText().toString().equals("")) {
                Toast.makeText(this, "Informe seu e-mail!", Toast.LENGTH_SHORT).show();
                edtUsuario.requestFocus();
            } else {
                if (edtSenha.getText().toString().equals("")) {
                    Toast.makeText(this, "Informe a senha!", Toast.LENGTH_SHORT).show();
                    edtSenha.requestFocus();
                } else {
                    String email = edtUsuario.getText().toString();
                    String senha = edtSenha.getText().toString();

                    enviaDados task = new enviaDados();

                    task.execute("http://sgestao.hol.es/ws/LoginWs.php?email=" + email + "&senha=" + senha, email, senha);
                    //task.execute("http://10.0.2.2:81/ws_sgestao/ws/LoginWs.php?email="+email+"&senha="+senha, email, senha);
                }
            }
        }else{
            Toast.makeText(this,"Não foi possível conectar a internet! Verifique sua conexão!",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 20){
            codEmpresa = 0;
            menuLoja = 0;
            menuPosto = 0;
        }
    }

    public boolean isOnline() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return manager.getActiveNetworkInfo() != null &&
                manager.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    private class enviaDados extends AsyncTask<String, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(String... strings) {
            String path = strings[0];
            String email = strings[1];
            String senha = strings[2];

            // cria um objeto HttpURLConnection
            HttpURLConnection conn = null;
            try {
                URL url = new URL(path);
                // abre a conexão
                conn = (HttpURLConnection) url.openConnection();
                // define o método de envio dos dados
                conn.setRequestMethod("POST");
                // define os dados a serem enviados
                String dados = "email="+email+"&senha="+senha;
                conn.setDoOutput(true);
                // envia os dados
                try (DataOutputStream out = new DataOutputStream(conn.getOutputStream())) {
                    out.write(dados.getBytes(StandardCharsets.UTF_8));
                }
                // define uma StringBuilder para obter o retorno (JSON)
                StringBuilder resposta1 = new StringBuilder();
                // cria um BufferReader para ler os dados de retorno do WebService
                try (BufferedReader in = new BufferedReader(new
                        InputStreamReader(conn.getInputStream()))) {
                    String linha;
                    while ((linha = in.readLine()) != null) {
                        resposta1.append(linha);
                    }
                }
                return new JSONObject(resposta1.toString());
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                return null;
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
        }
        @Override
        protected void onPostExecute(JSONObject jsonObject) {

            try {
                JSONArray jsonMenus = jsonObject.getJSONArray("menu");
                for (int i = 0; i < jsonMenus.length(); i++) {
                    JSONObject jsonUn = jsonMenus.getJSONObject(i);

                    codEmpresa = jsonUn.getInt("codempresa");
                    String tipoMenu= jsonUn.getString("menu");

                    if (tipoMenu.equals("Posto")){
                        menuPosto = jsonUn.getInt("liberado");
                    }else if(tipoMenu.equals("Loja")){
                        menuLoja= jsonUn.getInt("liberado");
                    }
                }
                if (codEmpresa > 0){
                    Intent it2 = new Intent(MainActivity.this, SelecaoUnidadeActivity.class);
                    //startActivity(it2);
                    startActivityForResult(it2,20);

                }else {
                    //String mensa = jsonObject.getString("wsMsg");
                    Toast.makeText(MainActivity.this, "Usuário não localizado",Toast.LENGTH_LONG).show();
                    edtUsuario.requestFocus();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}

