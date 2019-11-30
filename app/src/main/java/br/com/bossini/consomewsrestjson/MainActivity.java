package br.com.bossini.consomewsrestjson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView livrosRecyclerView;
    private LivroAdapter adapter;
    private List<Livro> livros;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RequestQueue requestQueue;
    FloatingActionButton btn;
    FloatingActionButton topo_btn;

    public String validador;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        validador = "0";
        //System.out.println("\nvalor inicial\n " +validador);
        btn = findViewById(R.id.fab);
//        topo_btn = findViewById(R.id.topoFAB);
        livrosRecyclerView = findViewById(R.id.livrosRecyclerView);
        livros = new ArrayList<>();
        adapter = new LivroAdapter(this, livros);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        livrosRecyclerView.setAdapter(adapter);
        livrosRecyclerView.setLayoutManager(llm);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        requestQueue = Volley.newRequestQueue(this);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                obterLivros();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        Intent origemIntent = getIntent();
        validador = origemIntent.getStringExtra("validador");
        if("1".equals(validador)) {
            obterLivros();
        }
        validador = "0";
    }

    public String montaURL (String... args) {
        StringBuilder sb = new StringBuilder();
        for (String s : args) {
            sb.append(s);
        }
        return sb.toString();
    }

    public void cadastrar(View view) {
        Intent intent = new Intent(this, SalvarLivroActivity.class);
        startActivity(intent);
    }

    public void obterLivros () {
        String url = montaURL(
                getString(R.string.host_address),
                getString(R.string.host_port),
                getString(R.string.endpoint_base),
                getString(R.string.endpoint_listar)
        );
        requestQueue.add(new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        livros.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject iPos = response.getJSONObject(i);
                                String titulo = iPos.getString("titulo");
                                String autor = iPos.getString("autor");
                                String edicao = iPos.getString("edicao");
                                livros.add(new Livro(titulo, autor, edicao));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        ));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
