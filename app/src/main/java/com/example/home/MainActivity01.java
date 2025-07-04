package com.example.home;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity01 extends AppCompatActivity {

    private List<Users> User = new ArrayList<Users>(){
        {
            add(new Users(
                    "梁",
                    "10分钟前",
                    "这是梁的动态内容",
                    "25",
                    "3",
                    R.drawable.baseline_man_24,
                    R.drawable.baseline_more_horiz_24,
                    R.drawable.baseline_people_24,
                    R.drawable.baseline_favorite_border_24,
                    R.drawable.baseline_insert_comment_24,
                    R.drawable.baseline_share_24
            ));
            add(new Users(
                    "德",
                    "10分钟前",
                    "这是德的动态内容",
                    "25",
                    "3",
                    R.drawable.baseline_man_24,
                    R.drawable.baseline_more_horiz_24,
                    R.drawable.baseline_people_24,
                    R.drawable.baseline_favorite_border_24,
                    R.drawable.baseline_insert_comment_24,
                    R.drawable.baseline_share_24
            ));
            add(new Users(
                    "钊",
                    "10分钟前",
                    "这是钊的动态内容",
                    "25",
                    "3",
                    R.drawable.baseline_man_24,
                    R.drawable.baseline_more_horiz_24,
                    R.drawable.baseline_people_24,
                    R.drawable.baseline_favorite_border_24,
                    R.drawable.baseline_insert_comment_24,
                    R.drawable.baseline_share_24
            ));
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main01);

        RecyclerView recyclerView = findViewById(R.id.recView);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        UsersAdapter adapter = new UsersAdapter(User);
        recyclerView.setAdapter(adapter);
    }
}