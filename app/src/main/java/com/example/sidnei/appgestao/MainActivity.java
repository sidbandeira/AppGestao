package com.example.sidnei.appgestao;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

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
        if(edtUsuario.getText().toString().equals("")){
            Toast.makeText(this,"Informe seu e-mail!", Toast.LENGTH_SHORT).show();
            edtUsuario.requestFocus();
        }else{
            if (edtSenha.getText().toString().equals("")){
                Toast.makeText(this,"Informe a senha!",Toast.LENGTH_SHORT).show();
                edtSenha.requestFocus();
            }else{
                String email = edtUsuario.getText().toString();
                String senha = edtSenha.getText().toString();

                enviaDados task = new enviaDados();

                task.execute("http://sgestao.hol.es/LoginWs.php?email=sidnei@gmail.com&senha=123456", email, senha);

            }
        }
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
                String dados =
                        "?email="+email+"&senha="+senha;
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
                int cod = jsonObject.getInt("wsCodempresa");
                if (cod > 0){
                    Toast.makeText(MainActivity.this,"Logou",Toast.LENGTH_LONG).show();
                    Intent it = new Intent(MainActivity.this, MenuActivity.class);
                    //it.putExtra("cod", cod);
                    startActivity(it);
                }else {
                    String mensa = jsonObject.getString("wsMsg");
                    Toast.makeText(MainActivity.this, "Mensagem: " + mensa,
                            Toast.LENGTH_LONG).show();
                    edtUsuario.requestFocus();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

