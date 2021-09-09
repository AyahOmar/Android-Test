package com.datechnologies.androidtest.login;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.datechnologies.androidtest.MainActivity;
import com.datechnologies.androidtest.R;
import com.datechnologies.androidtest.api.GetDataService;
import com.datechnologies.androidtest.api.LoginModel;
import com.datechnologies.androidtest.api.RetrofitBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A screen that displays a login prompt, allowing the user to login to the D & A Technologies Web Server.
 *
 */
public class LoginActivity extends AppCompatActivity {

    EditText emailEditText;
    EditText passwordEditText;
    Button loginButton;

    //==============================================================================================
    // Static Class Methods
    //==============================================================================================

    public static void start(Context context)
    {
        Intent starter = new Intent(context, LoginActivity.class);
        context.startActivity(starter);
    }

    //==============================================================================================
    // Lifecycle Methods
    //==============================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Login");
        emailEditText = (EditText)findViewById(R.id.email);
        passwordEditText = (EditText)findViewById(R.id.password);
        loginButton = (Button)findViewById(R.id.login);

        // TODO: Make the UI look like it does in the mock-up. Allow for horizontal screen rotation.    //Done
        // TODO: Add a ripple effect when the buttons are clicked                             //Done
        // TODO: Save screen state on screen rotation, inputted username and password should not disappear on screen rotation  //Done

        // TODO: Send 'email' and 'password' to http://dev.rapptrlabs.com/Tests/scripts/login.php       //Done
        // TODO: as FormUrlEncoded parameters.                                                          //Done

        // TODO: When you receive a response from the login endpoint, display an AlertDialog. //Done
        // TODO: The AlertDialog should display the 'code' and 'message' that was returned by the endpoint.  //Done
        // TODO: The AlertDialog should also display how long the API call took in milliseconds.           //Done
        // TODO: When a login is successful, tapping 'OK' on the AlertDialog should bring us back to the MainActivity  //Done

//         TODO: The only valid login credentials are:
//         TODO: email: info@rapptrlabs.com
//         TODO: password: Test123
//         TODO: so please use those to test the login.

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email  = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(LoginActivity.this,"Please fill blank fields", Toast.LENGTH_SHORT).show();
                }else {
                    closeKeyboard();
                    loginRequest(email,password);
                }
            }
        });


    }

    //==============================================================================================
    // Handle Login Request using Retrofit
    //==============================================================================================
    public void loginRequest(String email, String password){

        Call<LoginModel> call = RetrofitBuilder.service.login(email,password);
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {

                if(response.body() == null){
                    Toast.makeText(LoginActivity.this, "Incorrect Email or Password!", Toast.LENGTH_SHORT).show();
                }else {
                    showDialog(response);
                }
            }
            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //==============================================================================================
    // to close Keyboard after Press Login Button
    //==============================================================================================
    private void closeKeyboard()
    {
        // this will give us the view which is currently focus in this layout
        View view = this.getCurrentFocus();
        if (view != null) {
            // now assign the system service to InputMethodManager
            InputMethodManager manager = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    //==============================================================================================
    // Show Dialog
    //==============================================================================================
    private  void showDialog(Response<LoginModel> response){
        long time = response.raw().receivedResponseAtMillis();
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setMessage(response.body().getMessage()+"\n"+"Received-Millis: "+  time)
                .setTitle(response.body().getCode());

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
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
