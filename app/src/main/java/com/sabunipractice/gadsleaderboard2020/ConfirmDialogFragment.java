package com.sabunipractice.gadsleaderboard2020;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class ConfirmDialogFragment extends DialogFragment {
    private Button btnConfirm;
    private ImageButton btnClose;
    private TextView mTextView;
    private FeedbackDialogListener mListener;

    public ConfirmDialogFragment(){
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_confirm_dialog, null);
        btnConfirm = view.findViewById(R.id.btnConfirmSubmission);
        btnClose = view.findViewById(R.id.btnCloseDialog);
        mTextView = view.findViewById(R.id.confirm_dialog_title);

        builder.setView(view);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextView.setVisibility(View.GONE);
                btnConfirm.setClickable(false);
                btnConfirm.setText(R.string.submitting_form);
                mListener.submitConfirmed(true);
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            mListener = (FeedbackDialogListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() +
                    " must implement FeedbackDialogListener");
        }
    }

    public interface FeedbackDialogListener{
         void submitConfirmed(Boolean accepted);
    }

}
