package com.musichak.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.musichak.Activity.PlayMusicActivity;
import com.musichak.Adapter.PlayMusicAdapter;
import com.musichak.R;

public class Fragment_Play_Danhsachbaihat extends Fragment {
    View view;
    RecyclerView recyclerViewPlayMusic;
    PlayMusicAdapter playMusicAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_play_danhsachbaihat,container,false);
        recyclerViewPlayMusic = view.findViewById(R.id.recyclerviewPlayMusic);
        if (PlayMusicActivity.listBaiHat.size() >0){
            playMusicAdapter = new PlayMusicAdapter(getActivity(), PlayMusicActivity.listBaiHat);
            recyclerViewPlayMusic.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerViewPlayMusic.setAdapter(playMusicAdapter);
        }

        return view;
    }
}
