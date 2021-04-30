package com.example.trophyemall.ui.gallery;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.trophyemall.model.Post;
import com.example.trophyemall.repository.PostRepository;

import java.util.List;

public class GalleryViewModel extends AndroidViewModel {

    private PostRepository postRepository;
    private LiveData<List<Post>> pAllPost;
    public GalleryViewModel(@NonNull Application application) {
        super(application);
        postRepository = PostRepository.getInstance(application);
        pAllPost = postRepository.getAllPost();
    }
    public LiveData<List<Post>> getAllPost()
    {
        return pAllPost;
    }
    public void insert(Post post){
        postRepository.insert(post);
    }
    public void delete(Post post){postRepository.delete(post);
    }
}