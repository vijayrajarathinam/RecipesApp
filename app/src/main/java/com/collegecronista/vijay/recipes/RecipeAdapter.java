package com.collegecronista.vijay.recipes;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vijay on 15/07/2017.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    List<RecipeModel> recipeModels;
    Context context;
    RecipeModel recipeModel;
    RecipeDB recipeDB ;
    Toast toast;
    public RecipeAdapter(List<RecipeModel> recipeModels,Context context){
        this.recipeModels = recipeModels;
        this.context = context;
        this.recipeDB = new RecipeDB(context,"",null,1);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        recipeModel = recipeModels.get(position);
        holder.title.setText(recipeModel.getTitle());
        holder.description.setText(recipeModel.getDescription());
        holder.delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recipeDB.delete_information(recipeModels.get(position).getId());
                recipeModels.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,getItemCount());
                if(toast != null ){toast.cancel();}
                toast=Toast.makeText(v.getContext(),String.valueOf(recipeModels.get(position).getTitle())+" menu removed from list",Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipeModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView title;
        public TextView description;
        public Button delete_btn;
        public CardView cardView;
        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.title);
            description = (TextView)itemView.findViewById(R.id.description);
            delete_btn = (Button)itemView.findViewById(R.id.delete_button);
            cardView = (CardView)itemView.findViewById(R.id.card_view);
        }
    }

    public void setFilter(List<RecipeModel> recipeModelList){
        recipeModels = new ArrayList<>();
        recipeModels.addAll(recipeModelList);
        notifyDataSetChanged();
    }
}
