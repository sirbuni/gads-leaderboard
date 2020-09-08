package com.sabunipractice.gadsleaderboard2020;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.sabunipractice.gadsleaderboard2020.models.HoursLeader;
import com.sabunipractice.gadsleaderboard2020.models.SkillIQLeader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String BASE_URL = "https://gadsapi.herokuapp.com/";
    private static final String LEARNING_LEADERS_ENDPOINT = "api/hours";
    private static final String SKILL_IQ_LEADERS_ENDPOINT = "api/skilliq";
    private TextView mTextView;
    private GAADLearnersAPI mGAADLearnersAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.textView);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mGAADLearnersAPI = retrofit.create(GAADLearnersAPI.class);

//        getSkillIQLeaders();

        getHoursLeaders();
    }

    private void getHoursLeaders() {
        Call<List<HoursLeader>> hourLeaders = mGAADLearnersAPI.getHoursLeaders(LEARNING_LEADERS_ENDPOINT);
        hourLeaders.enqueue(new Callback<List<HoursLeader>>() {
            @Override
            public void onResponse(Call<List<HoursLeader>> call, Response<List<HoursLeader>> response) {
                if(!response.isSuccessful()){
                    mTextView.setText(String.format(Locale.ENGLISH, "%s%d",
                            getString(R.string.response_code), response.code()));
                    return;
                }

                List<HoursLeader> learners;

                if (response.body() != null) {
                    learners = new ArrayList<>(response.body());
                    Collections.sort(learners);

                    for(HoursLeader leader : learners){
                        String content = "";
                        content += "Name: " + leader.getName() + "\n";
                        content += "Hours: " + leader.getHours() + "\n";
                        content += "Country: " + leader.getCountry() + "\n";
                        content += "BadgeUrl: " + leader.getBadgeUrl() + "\n\n";

                        mTextView.append(content);
                    }
                }else {
                    mTextView.setText(R.string.no_records);
                }
            }

            @Override
            public void onFailure(Call<List<HoursLeader>> call, Throwable t) {
                mTextView.setText(t.getMessage());
            }
        });
    }

    private void getSkillIQLeaders() {
        Call<List<SkillIQLeader>> skillIQLeaders = mGAADLearnersAPI.getSkillIQLeaders(SKILL_IQ_LEADERS_ENDPOINT);

        skillIQLeaders.enqueue(new Callback<List<SkillIQLeader>>() {
            @Override
            public void onResponse(Call<List<SkillIQLeader>> call, Response<List<SkillIQLeader>> response) {
                if(!response.isSuccessful()){
                    mTextView.setText(String.format(Locale.ENGLISH, "%s%d",
                            getString(R.string.response_code), response.code()));
                    return;
                }

                List<SkillIQLeader> learners;

                if (response.body() != null) {
                    learners = new ArrayList<>(response.body());
                    Collections.sort(learners);

                    for(SkillIQLeader leader : learners){
                        String content = "";
                        content += "Name: " + leader.getName() + "\n";
                        content += "Score: " + leader.getScore() + "\n";
                        content += "Country: " + leader.getCountry() + "\n";
                        content += "BadgeUrl: " + leader.getBadgeUrl() + "\n\n";

                        mTextView.append(content);
                    }
                }else {
                    mTextView.setText(R.string.no_records);
                }
            }

            @Override
            public void onFailure(Call<List<SkillIQLeader>> call, Throwable t) {
                mTextView.setText(t.getMessage());
            }
        });
    }
}