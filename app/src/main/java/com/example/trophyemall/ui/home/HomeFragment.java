package com.example.trophyemall.ui.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.trophyemall.ui.CreateActivity;
import com.example.trophyemall.ui.adapters.FeedAdapter;
import com.example.trophyemall.ui.adapters.PostAdapter;
import com.firebase.ui.common.ChangeEventType;
import com.firebase.ui.firestore.ChangeEventListener;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

public class HomeFragment extends Fragment {
    public static final int RESULT_OK = 0;
    public static final int RESULT_CANCELED = -1;
    public static int  OPTION_REQUEST_NUEVO = 1;
    private HomeViewModel homeViewModel;
    private RecyclerView rvHome;
    private FeedAdapter adapter;
    private FloatingActionButton fab;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        rvHome = root.findViewById(R.id.rvHome);
        fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(view -> startActivityForResult(new Intent(getActivity(), CreateActivity.class), OPTION_REQUEST_NUEVO));

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        rvHome.setAdapter(adapter);
        rvHome.setLayoutManager(new LinearLayoutManager(view.getContext()));
        defineAdaptador();
        definirEventoSwiper();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Post post = data.getParcelableExtra(CreateActivity.EXTRA_POST);
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("Feed").add(post);
        }
    }

    private void defineAdaptador() {
        Query query = FirebaseFirestore.getInstance()
                .collection("Feed")
                .orderBy("id", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Post> options = new
                FirestoreRecyclerOptions.Builder<Post>()
                .setQuery(query, Post.class)
                .setLifecycleOwner(this)
                .build();
        if (adapter != null) {
            adapter.stopListening();
        }
        adapter = new FeedAdapter(options);
        rvHome.setAdapter(adapter);
        adapter.startListening();
        adapter.getSnapshots().addChangeEventListener(new ChangeEventListener() {
            @Override
            public void onChildChanged(@NonNull ChangeEventType type, @NonNull
                    DocumentSnapshot snapshot, int newIndex, int oldIndex) {
                rvHome.smoothScrollToPosition(0);
            }
            @Override
            public void onDataChanged() {
            }
            @Override
            public void onError(@NonNull FirebaseFirestoreException e) {
            }
        });
    }

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
                        FeedAdapter.FeedHolder
                                vhPost = (FeedAdapter.FeedHolder) viewHolder;
                        Post post = vhPost.getPost();
                        insertaPost(post, vhPost.getAdapterPosition());
                    }
                };
        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(rvHome);
    }

    private void insertaPost(Post post, int posicion) {

        AlertDialog.Builder dialogo = new AlertDialog.Builder(getContext());
        dialogo.setTitle("Aviso");
        dialogo.setMessage("¿Quieres guardar este post?");
        dialogo.setNegativeButton(android.R.string.cancel, new
                DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        adapter.notifyItemChanged(posicion);
                    }
                });
        dialogo.setPositiveButton(android.R.string.ok, new
                DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        homeViewModel.insert(post);
                        adapter.notifyItemChanged(posicion);
                    }
                });
        dialogo.show();
    }
}