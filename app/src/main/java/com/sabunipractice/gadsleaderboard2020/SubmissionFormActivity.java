package com.sabunipractice.gadsleaderboard2020;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SubmissionFormActivity extends AppCompatActivity implements ConfirmDialogFragment.FeedbackDialogListener {
    private EditText mFirstName;
    private EditText mLastName;
    private EditText mEmail;
    private EditText mGithubLink;
    private static final String TAG = "SubmissionFormActivity";

    private static final String BASE_URL = "https://docs.google.com/forms/u/0/d/e/";
    private Retrofit mRetrofit;
    private GAADLearnersAPI mGAADLearnersAPI;
    private FeedbackDialogFragment mDialogFragment;
    private ConfirmDialogFragment mConfirmDialogFragment;
//    private Boolean mFormIsValid = true;
    String fName;
    String lName;
    String email;
    String gitLink;


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
        // Submit form
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateForm()){
                    return;
                }
                    showConfirmationDialog();
            }
        });


    }

    private Boolean validateForm() {
//        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
//        Boolean valid = false;
        fName = mFirstName.getText().toString().trim();
        lName = mLastName.getText().toString().trim();
        email = mEmail.getText().toString().trim();
        gitLink = mGithubLink.getText().toString().trim();

        if (fName.isEmpty() && lName.isEmpty() && email.isEmpty() && gitLink.isEmpty()) {
            Toast.makeText(this, R.string.form_all_fields_required, Toast.LENGTH_LONG).show();
            return false;
        }

        if (!email.matches(String.valueOf(Patterns.EMAIL_ADDRESS))) {
            Toast.makeText(this, R.string.form_valid_email_required, Toast.LENGTH_LONG).show();
            return false;
        }

        if (!isValidUrl(gitLink)) {
            Toast.makeText(this, R.string.form_valid_url_required, Toast.LENGTH_LONG).show();
            return false;
        }

        return  true;

    }

    private void showConfirmationDialog() {
        mConfirmDialogFragment = new ConfirmDialogFragment();
        FragmentManager fm = getSupportFragmentManager();
        mConfirmDialogFragment.show(fm, "confirm_dialog_fragment");
    }

    private void submitForm() {
        //Handle form submission to Google forms
        Call<Void> call = mGAADLearnersAPI.submitProject(fName, lName, email, gitLink, email);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                mConfirmDialogFragment.dismiss();
                if (!response.isSuccessful()) {
                    showFailedSubmissionDialog(getString(R.string.failed_dialog));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mDialogFragment.dismiss();
                        }
                    }, 5000);
                    return;
                }

                showSuccessfulSubmissionDialog(getString(R.string.success_dialog));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDialogFragment.dismiss();
                    }
                }, 5000);
                Log.d(TAG, response.code() + ": Form Submitted");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                showFailedSubmissionDialog(getString(R.string.failed_dialog));
            }
        });
    }

    private boolean isValidUrl(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void showSuccessfulSubmissionDialog(String dialogType) {
//        args = "success";

        mDialogFragment = new FeedbackDialogFragment(dialogType);
        FragmentManager fm = getSupportFragmentManager();
        mDialogFragment.show(fm, "Successful Submission Dialog");
    }

    private void showFailedSubmissionDialog(String dialogType) {
//        args = "fail";

        mDialogFragment = new FeedbackDialogFragment(dialogType);
        FragmentManager fm = getSupportFragmentManager();
        mDialogFragment.show(fm, "Failed Submission Dialog");
    }

    @Override
    public void submitConfirmed(Boolean accepted) {
        if (accepted) {
            submitForm();
        }
    }
}
