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
import com.sabunipractice.gadsleaderboard2020.models.SkillIQLeader;
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
public class SkillIQLeaders extends Fragment {
    private static final String BASE_URL = "https://gadsapi.herokuapp.com/";
    private static final String SKILL_IQ_LEADERS_ENDPOINT = "api/skilliq";
    private GAADLearnersAPI mGAADLearnersAPI;
    private static final String SkillIQClass = "SkillIQ";

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    public SkillIQLeaders() {
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
        return inflater.inflate(R.layout.fragment_skill_iq_leaders, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = view.findViewById(R.id.skill_iq_recyclerview);
        mLayoutManager = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        getSkillIQLeaders();
    }

    private void getSkillIQLeaders() {
        Call<List<SkillIQLeader>> skillIQLeaders = mGAADLearnersAPI.getSkillIQLeaders(SKILL_IQ_LEADERS_ENDPOINT);

        skillIQLeaders.enqueue(new Callback<List<SkillIQLeader>>() {
            @Override
            public void onResponse(Call<List<SkillIQLeader>> call, Response<List<SkillIQLeader>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(), "Error: " + response.code(), Toast.LENGTH_LONG).show();
                    return;
                }

                List<SkillIQLeader> learners;

                if (response.body() != null) {
                    learners = new ArrayList<>(response.body());
                    Collections.sort(learners);

                    mAdapter = new RecyclerAdapter(learners, SkillIQClass);
                    mRecyclerView.setAdapter(mAdapter);
                }else {
                    Toast.makeText(getContext(), R.string.no_record_found, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<SkillIQLeader>> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
