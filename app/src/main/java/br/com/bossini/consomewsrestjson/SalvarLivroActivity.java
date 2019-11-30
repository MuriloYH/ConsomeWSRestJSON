package br.com.bossini.consomewsrestjson;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class SalvarLivroActivity extends AppCompatActivity {

    private EditText tituloET;
    private EditText autorET;
    private EditText nPaginasET;
    private EditText edicaoET;
    FloatingActionButton sendFAB;

    private RequestQueue requestQueue;

    MainActivity main = new MainActivity();

    private String v = "0";

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent origemIntent = getIntent();
        setContentView(R.layout.activity_salvar_livro);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tituloET = findViewById(R.id.tituloEditText);
        autorET = findViewById(R.id.autorEditText);
        nPaginasET = findViewById(R.id.nPaginasEditText);
        edicaoET = findViewById(R.id.edicaoEditText);
        requestQueue = Volley.newRequestQueue(this);
        sendFAB = findViewById(R.id.fab);

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    private String montaURL (String... args) {
        StringBuilder sb = new StringBuilder();
        for (String s : args) {
            sb.append(s);
        }
        return sb.toString();
    }

    private JSONObject geraLivroJSON (Livro livro) {
        JSONObject jo = new JSONObject();

        try {
            jo.put("titulo", livro.getTitulo());
            jo.put("autor", livro.getAutor());
            jo.put("numeroPaginas", livro.getNumeroPaginas());
            jo.put("edicao", livro.getEdicao());
            Toast.makeText(this, "Livro cadastrado", Toast.LENGTH_SHORT).show();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return jo;
    }

    private void cadastrarLivros (JSONObject body) {
        String url = montaURL(
                getString(R.string.host_address),
                getString(R.string.host_port),
                getString(R.string.endpoint_base),
                getString(R.string.endpoint_salvar)
        );
        requestQueue.add(new JsonObjectRequest(
                Request.Method.POST,
                url,
                body,
                null,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        ));
    }

    public void sendLivro(View view) {
        String titulo = tituloET.getText().toString();
        String autor = autorET.getText().toString();
        int numeroPaginas = Integer.parseInt(nPaginasET.getText().toString());
        String edicao = edicaoET.getText().toString();

        Livro livro = new Livro(titulo, autor, numeroPaginas, edicao);
        livro.setTitulo(titulo);
        livro.setAutor(autor);
        livro.setNumeroPaginas(numeroPaginas);
        livro.setEdicao(edicao);

        JSONObject body = geraLivroJSON(livro);
        cadastrarLivros(body);
        setV("1");
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("validador", getV());
        startActivity(intent);
    }

}
