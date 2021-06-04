package com.example.trophyemall.ui.gallery;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trophyemall.R;
import com.example.trophyemall.model.Post;
import com.example.trophyemall.ui.adapters.PostAdapter;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private RecyclerView rvGallery;
    private PostAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        rvGallery = root.findViewById(R.id.rvGallery);
        adapter = new PostAdapter();

        return root;
    }

    /**
     * Aquí se crea el viewmodel y el adaptador, se asocia este ultimo con el recyclerview y se llama
     * al método de definir el evento de deslizar
     * @param view
     * @param savedInstanceState
     */

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        galleryViewModel = new ViewModelProvider(this).get(GalleryViewModel.class);
        rvGallery.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rvGallery.setAdapter(adapter);
        galleryViewModel.getAllPost().observe(
                getViewLifecycleOwner(), posts -> {
                    adapter.setListaPost(posts);
                }
        );
        definirEventoSwiper();
    }

    /**
     * En este método se define que, al deslizar un post, ese post es borrado de la base de datos local
     * pero no de la base de datos de Firebase
     */

    private void definirEventoSwiper() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT |
                        ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }
                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int
                            swipeDir) {
                        PostAdapter.PostViewHolder
                                vhPost=(PostAdapter.PostViewHolder) viewHolder;
                        Post post = vhPost.getPost();
                        borrarPost(post, vhPost.getAdapterPosition());
                    }
                };
        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(rvGallery);
    }

    /**
     * En este método se muestra un diálogo que avisa de que se va a borrar el post y da la opción
     * de aceptar o cancelar dicha acción. Al pulsar aceptar, borra el post solo de forma local, ya
     * que el post en Firebase se mantiene intacto
     * @param post
     * @param posicion
     */

    private void borrarPost(Post post,int posicion) {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(getContext());
        dialogo.setTitle("Aviso");
        dialogo.setMessage("¿Quieres borrar este post de tus elementos guardados?");
        dialogo.setNegativeButton(android.R.string.cancel, new
                DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        adapter.notifyItemChanged(posicion);//recuperamos la posición
                    }
                });
        dialogo.setPositiveButton(android.R.string.ok, new
                DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Borramos
                        galleryViewModel.delete(post);
                    }
                });
        dialogo.show();
    }
}