package com.sabunipractice.gadsleaderboard2020;

import com.sabunipractice.gadsleaderboard2020.models.SkillIQLeader;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface GAADLearnersAPI {

    @GET
    Call<List<SkillIQLeader>> getSkillIQs(@Url String url);

}

