package com.example.trophyemall.ui.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.trophyemall.R;
import com.example.trophyemall.model.Post;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder>{
    private List<Post> listaPost;

    /**
     * Este método crea una instancia del viewholder y marca el layout "item_post" como forma en la
     * que se muestran los post
     * @param parent
     * @param viewType
     * @return
     */

    @NonNull
    @Override
    public PostAdapter.PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post,
                        parent, false);
        return new PostViewHolder(itemView);
    }

    /**
     * Este método obtiene el post requerido, asocia los textos que muestran y la imagen
     * @param holder
     * @param position
     */

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        if (listaPost != null) {
            final Post post = listaPost.get(position);
            holder.tvUsuario.setText(post.getUsuario());
            holder.tvTipo.setText(post.getTipo());
            holder.tvDescripcion.setText(post.getDescripcion());
            if (!post.getFotoUri().equals(""))
                Glide.with(holder.itemView).load(post.getFotoUri()).into(holder.ivFoto);
            holder.post = post;
        }
    }

    /**
     * Este método devuelve el total de los post que hay creados
     * @return
     */

    @Override
    public int getItemCount() {
        if (listaPost != null)
            return listaPost.size();
        else return 0;
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        private TextView tvUsuario;
        private TextView tvTipo;
        private TextView tvDescripcion;
        private ImageView ivFoto;
        private Post post;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsuario = itemView.findViewById(R.id.tvItemUsername);
            tvTipo = itemView.findViewById(R.id.tvItemType);
            tvDescripcion = itemView.findViewById(R.id.tvItemDesc);
            ivFoto = itemView.findViewById(R.id.ivItemImage);
        }

        public Post getPost() {
            return post;
        }
    }

    public void setListaPost(List<Post> posts) {
        listaPost = posts;
        notifyDataSetChanged();
    }
}
