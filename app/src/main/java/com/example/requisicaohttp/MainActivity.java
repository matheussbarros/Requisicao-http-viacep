package com.example.requisicaohttp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText cep = findViewById(R.id.cepEditText);
        final TextView resposta = findViewById(R.id.enderecoTextView);


        Button buscaCepBtn = findViewById(R.id.buscaCepBtn);
        buscaCepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    JSONObject retorno = new HttpService(cep.getText().toString()).execute().get();

                    if(retorno!= null){
                        Cep cep = new Cep(
                                retorno.getString("cep"),
                                retorno.getString("logradouro"),
                                retorno.getString("complemento"),
                                retorno.getString("bairro"),
                                retorno.getString("cidade"),
                                retorno.getString("estado")
                        );
                        resposta.setText(cep.toString());
                    }else{
                        Toast.makeText(MainActivity.this, "CEP não encontrado", Toast.LENGTH_SHORT).show();
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });



    }
}
