package com.sabunipractice.gadsleaderboard2020;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class FeedbackDialogFragment extends DialogFragment {
    private String dialogType;

    public FeedbackDialogFragment(String dialogType){
        this.dialogType = dialogType;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = null;
        if (dialogType.equals(getActivity().getString(R.string.success_dialog)))
            view = inflater.inflate(R.layout.fragment_feedback_success, null);
        else if(dialogType.equals(getActivity().getString(R.string.failed_dialog)))
            view = inflater.inflate(R.layout.fragment_feedback_failed, null);

        builder.setView(view);

        return builder.create();
    }

}
