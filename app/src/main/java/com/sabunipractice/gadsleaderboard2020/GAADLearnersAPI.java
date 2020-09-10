package com.sabunipractice.gadsleaderboard2020;

import com.sabunipractice.gadsleaderboard2020.models.HoursLeader;
import com.sabunipractice.gadsleaderboard2020.models.SkillIQLeader;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface GAADLearnersAPI {

    @GET
    Call<List<SkillIQLeader>> getSkillIQLeaders(@Url String url);

    @GET
    Call<List<HoursLeader>> getHoursLeaders(@Url String url);

    @FormUrlEncoded
    @POST("1FAIpQLSfGcYF9hkMbYJxu6GC5-BkZ9qb07wolFDKvTYXOYulZ21RvFg/formResponse")
    Call<Void> submitProject(
            @Field("entry.1795268855") String firstName,
            @Field("entry.1433567522") String lastName,
            @Field("entry.1173153793") String email,
            @Field("entry.558006571") String githubUrl,
            @Field("emailAddress") String emailAddress
    );
}

