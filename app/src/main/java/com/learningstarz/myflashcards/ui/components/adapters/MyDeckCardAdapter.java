package com.learningstarz.myflashcards.ui.components.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import com.learningstarz.myflashcards.data_storage.DataManager;
import com.learningstarz.myflashcards.tools.Tools;
import com.learningstarz.myflashcards.types.Deck;
import com.learningstarz.myflashcards.ui.activities.CardsActivity;

import java.util.ArrayList;

/**
 * Created by ZahARin on 01.02.2016.
 */
public class MyDeckCardAdapter extends RecyclerSwipeAdapter<MyDeckCardAdapter.SimpleViewHolder> {
    Context mContext;
    ArrayList<Deck> decks;
    private SwipeLayout prevLayout = null; // previous layout. needs to close it when new opens

    public MyDeckCardAdapter(Context context, int sortType) {
        this.decks = Tools.sortDecks(DataManager.getDecks(), sortType);
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
        final Deck deck = decks.get(position);

        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        viewHolder.swipeLayout.addSwipeListener(new SimpleSwipeListener() {

            @Override
            public void onStartOpen(SwipeLayout layout) {
                super.onStartOpen(layout);
                if (prevLayout != null && prevLayout != layout) {
                    prevLayout.close();
                    prevLayout = layout;
                } else {
                    prevLayout = layout;
                }
                layout.open();
            }

            @Override
            public void onStartClose(SwipeLayout layout) {
                super.onStartClose(layout);

                layout.close();
            }
        });

        viewHolder.swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO create study activity
            }
        });

        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemManger.removeShownLayouts(viewHolder.swipeLayout);
                DataManager.deleteDeckByUId(deck.getUid());
                decks.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, decks.size());
                closeAllItems();
                //TODO Create delete card_edit from server mechanism
            }
        });

        if (deck.getOwner() != 1) {
            viewHolder.btnEdit.setVisibility(View.GONE);
        } else {
            viewHolder.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent cardsIntent = new Intent(mContext, CardsActivity.class);
                    cardsIntent.putExtra(Tools.deckExtraTag, deck);
                    mContext.startActivity(cardsIntent);
                    mItemManger.closeAllItems();
                    prevLayout.close();
                    prevLayout = null;

                }
            });
        }

        viewHolder.tvDeckTitle.setText(deck.getTitle());
        viewHolder.tvDeckAuthor.setText(deck.getAuthor());
        viewHolder.tvCardsCount.setText(deck.getCardsCount() + "");
        viewHolder.tvProgress.setText(deck.getProgress() + "");
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
