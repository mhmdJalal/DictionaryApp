package com.codepolitan.s3madekamus.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.codepolitan.s3madekamus.R;
import com.codepolitan.s3madekamus.model.KamusModel;
import com.codepolitan.s3madekamus.ui.detailKamus.DetailKamusActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchKamusAdapter extends RecyclerView.Adapter<SearchKamusAdapter.ViewHolder> {

    private List<KamusModel> kamusModels = new ArrayList<>();

    public void replaceAll(List<KamusModel> items) {
        kamusModels = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kamus, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(kamusModels.get(position));
    }

    @Override
    public int getItemCount() {
        return kamusModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_word)
        TextView tv_word;
        @BindView(R.id.tv_translate)
        TextView tv_translate;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final KamusModel item) {
            tv_word.setText(item.getWord());
            tv_translate.setText(item.getTranslate());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), DetailKamusActivity.class);
                    intent.putExtra("word", String.valueOf(item.getWord()));
                    intent.putExtra("translate", String.valueOf(item.getTranslate()));
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
