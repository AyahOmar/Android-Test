package com.datechnologies.androidtest.chat;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.datechnologies.androidtest.MainActivity;
import com.datechnologies.androidtest.R;
import com.datechnologies.androidtest.api.DataModel;
import com.datechnologies.androidtest.api.GetDataService;
import com.datechnologies.androidtest.api.RetrofitBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Screen that displays a list of chats from a chat log.
 */
public class ChatActivity extends AppCompatActivity {

    //==============================================================================================
    // Class Properties
    //==============================================================================================

    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;
    private ProgressBar progressBar;

    //==============================================================================================
    // Static Class Methods
    //==============================================================================================

    public static void start(Context context)
    {
        Intent starter = new Intent(context, ChatActivity.class);
        context.startActivity(starter);
    }

    //==============================================================================================
    // Lifecycle Methods
    //==============================================================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Chat");
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        // TODO: Make the UI look like it does in the mock-up. Allow for horizontal screen rotation. //Done

        // TODO: Retrieve the chat data from http://dev.rapptrlabs.com/Tests/scripts/chat_log.php  //Done
        // TODO: Parse this chat data from JSON into ChatLogMessageModel and display it.           //Done

        getChatDataFromApi();

    }
    //==============================================================================================
    //Method to generate List of data using RecyclerView with custom adapter
    //==============================================================================================
    private void generateDataList(DataModel photoList) {
        recyclerView = findViewById(R.id.recyclerView);
        chatAdapter = new ChatAdapter(this,photoList.getData());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ChatActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(chatAdapter);
    }

    //==============================================================================================
    //Retrieve the chat data from API using Retrofit
    //==============================================================================================
    private void getChatDataFromApi(){
        Call<DataModel> call = RetrofitBuilder.service.getChat();
        call.enqueue(new Callback<DataModel>() {
            @Override
            public void onResponse(Call<DataModel> call, Response<DataModel> response) {
                progressBar.setVisibility(View.INVISIBLE);
                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<DataModel> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(ChatActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    //==============================================================================================
    // Handle back button in action bar
    //==============================================================================================
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        this.finish();
        return true;
    }
}
