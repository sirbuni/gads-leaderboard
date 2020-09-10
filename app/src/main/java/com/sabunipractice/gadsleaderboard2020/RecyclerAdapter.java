package com.sabunipractice.gadsleaderboard2020;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.sabunipractice.gadsleaderboard2020.models.HoursLeader;
import com.sabunipractice.gadsleaderboard2020.models.SkillIQLeader;
import com.squareup.picasso.Picasso;
import java.util.List;
import java.util.Locale;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<?> mList;
    private String listType;
    private static final String HoursLeaderClass = "HoursLeader";
    private static final String SkillIQClass = "SkillIQ";

    public RecyclerAdapter(List<?> members, String listType) {
        this.mList = members;
        this.listType = listType;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.learner_info, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(listType.equals(HoursLeaderClass)){
            HoursLeader hoursLeader = (HoursLeader) mList.get(position);

            Picasso.get().load(hoursLeader.getBadgeUrl()).into(holder.mImageView);
            holder.mTextViewName.setText(hoursLeader.getName());
            holder.mTextViewExtra.setText(String.format(
                    Locale.ENGLISH, "%s learning hours, %s",
                    String.valueOf(hoursLeader.getHours()), hoursLeader.getCountry())
            );
        }else  if (listType.equals(SkillIQClass)){
            SkillIQLeader skillIQLeader = (SkillIQLeader) mList.get(position);

            Picasso.get().load(skillIQLeader.getBadgeUrl()).into(holder.mImageView);
            holder.mTextViewName.setText(skillIQLeader.getName());
            holder.mTextViewExtra.setText(String.format(Locale.ENGLISH,
                    "%s skill IQ score, %s",
                    String.valueOf(skillIQLeader.getScore()), skillIQLeader.getCountry())
            );
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mTextViewName;
        TextView mTextViewExtra;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.badge);
            mTextViewName = itemView.findViewById(R.id.full_name);
            mTextViewExtra = itemView.findViewById(R.id.extra_info);
        }
    }
}
