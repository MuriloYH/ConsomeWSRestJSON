package br.com.bossini.consomewsrestjson;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LivroAdapter extends RecyclerView.Adapter <LivroViewHolder> {
    private Context context;
    private List<Livro> livros;

    //construtor
    public LivroAdapter(Context context, List<Livro> livros) {
        this.context = context;
        this.livros = livros;
    }
    public LivroViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View raiz = inflater.inflate(
                R.layout.list_item,
                parent,
                false
        );
        return new LivroViewHolder(raiz);
    }
    public void onBindViewHolder (@NonNull LivroViewHolder holder, int position) {
        Livro livro = this.livros.get(position);
        holder.autorTextView.setText(livro.getAutor());
        holder.tituloTextView.setText(livro.getTitulo());
        holder.edicaoTextView.setText(livro.getEdicao());
    }
    public int getItemCount() {
        return livros.size();
    }
}

