package br.com.bossini.consomewsrestjson;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class LivroViewHolder extends RecyclerView.ViewHolder {
    public TextView tituloTextView;
    public TextView autorTextView;
    public TextView edicaoTextView;
    public LivroViewHolder (View raiz) {
        super (raiz);
        this.tituloTextView = raiz.findViewById(R.id.tituloTextView);
        this.autorTextView = raiz.findViewById(R.id.autorTextView);
        this.edicaoTextView = raiz.findViewById(R.id.edicaoTextView);
    }
}
