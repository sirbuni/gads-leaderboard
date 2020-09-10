package com.sabunipractice.gadsleaderboard2020;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.sabunipractice.gadsleaderboard2020.models.HoursLeader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class LearningLeaders extends Fragment {
    private static final String BASE_URL = "https://gadsapi.herokuapp.com/";
    private static final String LEARNING_LEADERS_ENDPOINT = "api/hours";
    private GAADLearnersAPI mGAADLearnersAPI;
    private static final String HoursLeaderClass = "HoursLeader";

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    public LearningLeaders() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mGAADLearnersAPI = retrofit.create(GAADLearnersAPI.class);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_learning_leaders, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = view.findViewById(R.id.learners_recyclerview);
        mLayoutManager = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        getHoursLeaders();

    }

    private void getHoursLeaders() {

        Call<List<HoursLeader>> hourLeaders = mGAADLearnersAPI.getHoursLeaders(LEARNING_LEADERS_ENDPOINT);
        hourLeaders.enqueue(new Callback<List<HoursLeader>>() {
            @Override
            public void onResponse(Call<List<HoursLeader>> call, Response<List<HoursLeader>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(), "Error: " + response.code(), Toast.LENGTH_LONG).show();
                    return;
                }

                List<HoursLeader> learners;

                if (response.body() != null) {
                    learners = new ArrayList<>(response.body());
                    Collections.sort(learners);

                    mAdapter = new RecyclerAdapter(learners, HoursLeaderClass);
                    mRecyclerView.setAdapter(mAdapter);
                }else {
                    Toast.makeText(getContext(), R.string.no_record_found, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<HoursLeader>> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
