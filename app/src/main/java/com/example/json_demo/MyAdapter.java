package com.example.json_demo;

import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Random;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
    List<QuoteModel> list;
    int[] backgrounds = {
            R.drawable.bg_beach,
            R.drawable.bg_forest,
            R.drawable.bg_moutain,
            R.drawable.bg_river
    };


    public MyAdapter(Context context, List<QuoteModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quote,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        QuoteModel quoteModel = list.get(position);
        holder.quote.setText(quoteModel.getQuote());
        holder.author.setText("-"+quoteModel.getAuthor()+"-");
        holder.linearLayout.setBackgroundResource(backgrounds[getRamdomBackground()]);
    }

    @Override
    public int getItemCount() {
        if (list!=null)
        {
            return list.size();
        }
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView quote,author;
        private LinearLayout linearLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            quote = itemView.findViewById(R.id.quote);
            author = itemView.findViewById(R.id.author);
            linearLayout = itemView.findViewById(R.id.contain);
        }
    }
    public int getRamdomBackground()
    {
        Random r = new Random();
        return r.nextInt(backgrounds.length);

    }

}
