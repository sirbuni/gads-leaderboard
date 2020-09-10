package com.sabunipractice.gadsleaderboard2020;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SubmissionFormActivity extends AppCompatActivity {
    private EditText mFirstName;
    private EditText mLastName;
    private EditText mEmail;
    private EditText mGithubLink;

    private static final String BASE_URL = "https://docs.google.com/forms/u/0/d/e/";
    private Retrofit mRetrofit;
    private GAADLearnersAPI mGAADLearnersAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submission_form);


        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mGAADLearnersAPI = mRetrofit.create(GAADLearnersAPI.class);

        mFirstName = findViewById(R.id.etFirstName);
        mLastName = findViewById(R.id.etLastName);
        mEmail = findViewById(R.id.etEmailAddress);
        mGithubLink = findViewById(R.id.etGithubLink);

        ImageButton backButton = findViewById(R.id.btnBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        Button submitButton = findViewById(R.id.btnSubmit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });

    }

    private void submitForm() {
        String fName = mFirstName.getText().toString();
        String lName = mLastName.getText().toString();
        String email = mEmail.getText().toString();
        String gitLink = mGithubLink.getText().toString();

        //Handle form submission to Google forms
        Call<Void> call = mGAADLearnersAPI.submitProject(fName, lName, email, gitLink, email);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(!response.isSuccessful()){
//                    TODO: Show failed dialog
                    Toast.makeText(getApplicationContext(),
                            "Error: " + response.code() +". Request unsuccessful.",
                            Toast.LENGTH_LONG).show();
                }

//                TODO: Show success dialog
                Toast.makeText(getApplicationContext(),
                        "Form submitted successfully",
                        Toast.LENGTH_LONG).show();
                Log.d("FormSubmission", response.code() + "");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 2000);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(),
                        "Error: " + t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
