package com.collegecronista.vijay.recipes;

import android.database.Cursor;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private RecyclerView recyclerView;
    //private RecyclerView.Adapter adapter;
    private List<RecipeModel> recipeModels;
    private RecipeDB recipeDB;
    private Toast toast;
    private RecipeAdapter recipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recipeDB = new RecipeDB(this,"",null,1);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recipeModels = new ArrayList<>();
        Cursor cursor = recipeDB.get_information();
        if(cursor.moveToFirst()) {
            do {
                RecipeModel recipeModel = new RecipeModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
                recipeModels.add(recipeModel);
            } while (cursor.moveToNext());
            recipeAdapter = new RecipeAdapter(recipeModels,this);
            recyclerView.setAdapter(recipeAdapter);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.option_menu,menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.add:
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                View view = getLayoutInflater().inflate(R.layout.add_recipes_layout,null);
                final EditText title = (EditText)view.findViewById(R.id.add_title);
                final EditText description = (EditText)view.findViewById(R.id.add_description);
                Button button = (Button)view.findViewById(R.id.add_button);
                //builder.setPositiveButton();
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!title.getText().toString().isEmpty() && !description.getText().toString().isEmpty()){
                            long id =recipeDB.set_information(title.getText().toString(),description.getText().toString());
                            RecipeModel recipeModel = new RecipeModel((int)id,title.getText().toString(),description.getText().toString());
                            recipeModels.add(recipeModel);
                            recipeAdapter.notifyDataSetChanged();

//                            if(toast != null){toast.cancel();}
//                            toast = Toast.makeText(MainActivity.this,"recipes added",Toast.LENGTH_SHORT);
//                            toast.show();

                        }else{
                            if(toast != null){toast.cancel();}
                            toast = Toast.makeText(getApplicationContext(),"columns are empty",Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                });
                builder.setView(view);
                AlertDialog dialog = builder.create();
                builder.show();
                //dialog.dismiss();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText = newText.toLowerCase();
        List<RecipeModel> newlist = new ArrayList<>();
        for (RecipeModel recipeModel:recipeModels){
            String title = recipeModel.getTitle().toLowerCase();
            if(title.contains(newText)){
                newlist.add(recipeModel);
            }
        }
        recipeAdapter.setFilter(newlist);
        return true;
    }
}
