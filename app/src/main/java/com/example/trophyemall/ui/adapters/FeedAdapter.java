package com.example.trophyemall.ui.adapters;

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
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

public class FeedAdapter extends FirestoreRecyclerAdapter<Post, FeedAdapter.FeedHolder> {
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser fbUser = auth.getCurrentUser();
    String usuario = fbUser.getEmail();

    public FeedAdapter(@NonNull FirestoreRecyclerOptions<Post> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull FeedHolder holder, int position, @NonNull Post post) {
        holder.feedUser.setText(post.getUsuario());
        holder.feedType.setText(post.getTipo());
        holder.feedDesc.setText(post.getDescripcion());
        if (!post.getFotoUri().equals(""))
            Glide.with(holder.itemView).load(post.getFotoUri()).into(holder.feedImg);
        holder.post = post;
    }

    @NonNull
    @NotNull
    @Override
    public FeedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FeedHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false));
    }



    public class FeedHolder extends RecyclerView.ViewHolder {
        TextView feedUser;
        TextView feedType;
        TextView feedDesc;
        ImageView feedImg;
        Post post;
        public FeedHolder(@NonNull View itemView) {
            super(itemView);
            feedUser = itemView.findViewById(R.id.tvItemUsername);
            feedType = itemView.findViewById(R.id.tvItemType);
            feedDesc = itemView.findViewById(R.id.tvItemDesc);
            feedImg = itemView.findViewById(R.id.ivItemImage);
        }
        public Post getPost() {
            return post;
        }
    }
}
