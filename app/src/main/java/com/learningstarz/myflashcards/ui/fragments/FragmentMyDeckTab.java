package com.learningstarz.myflashcards.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.learningstarz.myflashcards.R;
import com.learningstarz.myflashcards.ui.activities.EditDeckActivity;
import com.learningstarz.myflashcards.ui.components.adapters.MyDeckCardAdapter;

import java.util.ArrayList;

/**
 * Created by ZahARin on 20.01.2016.
 */
public class FragmentMyDeckTab extends Fragment {
    public static final String PAGE = "PAGE";
    public static final String TOKEN = "TOKEN";

    private int mPage;
    private String mToken;

    public static FragmentMyDeckTab newInstance(int page, String token) {
        Bundle args = new Bundle();
        args.putInt(PAGE, page);
        args.putString(TOKEN, token);
        FragmentMyDeckTab fragment = new FragmentMyDeckTab();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(PAGE);
        mToken = getArguments().getString(TOKEN);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_deck_tab, container, false);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.MyDeckFragmentTab_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EditDeckActivity.class);
                startActivity(intent);
            }
        });
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.MyDeckActivity_recyclerView);
        rv.hasFixedSize();
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);
        rv.setAdapter(new MyDeckCardAdapter(getContext(), mPage, mToken));
        return view;
    }
}
