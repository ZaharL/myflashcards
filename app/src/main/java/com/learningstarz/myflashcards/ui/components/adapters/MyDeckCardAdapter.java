package com.learningstarz.myflashcards.ui.components.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.learningstarz.myflashcards.R;
import com.learningstarz.myflashcards.Types.Deck;

import java.util.ArrayList;

/**
 * Created by ZahARin on 01.02.2016.
 */
public class MyDeckCardAdapter extends RecyclerSwipeAdapter<MyDeckCardAdapter.SimpleViewHolder> {
    Context mContext;
    ArrayList<Deck> decks;

    public MyDeckCardAdapter(Context context, ArrayList<Deck> decks) {
        this.decks = decks;
        this.mContext = context;
    }

    public class SimpleViewHolder extends RecyclerView.ViewHolder {
        SwipeLayout swipeLayout;
        TextView tvDeckTitle;
        TextView tvDeckAuthor;
        TextView tvCardsCount;
        TextView tvProgress;
        ProgressBar pbDeckProgress;
        ImageButton btnEdit;
        ImageButton btnDelete;
        CardView cv;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.CardDeck_swipeLayout);
            tvDeckTitle = (TextView) itemView.findViewById(R.id.CardDeck_tvDeckTitle);
            tvDeckAuthor = (TextView) itemView.findViewById(R.id.CardDeck_tvAuthor);
            tvCardsCount = (TextView) itemView.findViewById(R.id.CardDeck_tvCardsCount);
            tvProgress = (TextView) itemView.findViewById(R.id.CardDeck_tvProgress);
            pbDeckProgress = (ProgressBar) itemView.findViewById(R.id.CardDeck_cvProgressBar);
            cv = (CardView) itemView.findViewById(R.id.CardDeck_cardView);
            btnEdit = (ImageButton) itemView.findViewById(R.id.CardDeck_btnEdit);
            btnDelete = (ImageButton) itemView.findViewById(R.id.CardDeck_btnDelete);
        }
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_deck, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder viewHolder, final int position) {
        Deck deck = decks.get(position);

        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);

        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemManger.removeShownLayouts(viewHolder.swipeLayout);
                decks.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, decks.size());
                mItemManger.closeAllItems();
                //TODO Create delete card from server mechanism
            }
        });

        if (deck.getOwner() != 1) {
            viewHolder.btnEdit.setVisibility(View.GONE);
        } else {
            viewHolder.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO You need to create EditCardsActivity, and the transition to it
                }
            });
        }

        viewHolder.tvDeckTitle.setText(deck.getTitle());
        viewHolder.tvDeckAuthor.setText(deck.getAuthor());
        viewHolder.tvCardsCount.setText(deck.getCardsCount()+"");
        viewHolder.tvProgress.setText(deck.getProgress()+"");
        viewHolder.pbDeckProgress.setProgress(deck.getProgress());
    }

    @Override
    public int getItemCount() {
        return decks.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.CardDeck_swipeLayout;
    }


}