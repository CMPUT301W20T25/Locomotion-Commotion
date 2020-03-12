package com.example.locomotioncommotion;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import org.w3c.dom.Text;

public class confirmRequestFragment extends DialogFragment {
    //show all the details of the request made

    private TextView start;
    private TextView end;
    private TextView estPrice;
    private TextView addPrice;
    private TextView totalPrice;
    private OnFragmentInteractionListener listener;

    public interface OnFragmentInteractionListener {
        void onOkPressed(String startLocation, String endLocation, String cost);
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState){
        //layout for the fragment
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.confirm_request_frag_layout, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Confirm Request Details")
                .setNegativeButton("Cancel",null)
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String startLocation = start.getText().toString();
                        String endLocation = end.getText().toString();
                        String est = estPrice.getText().toString();
                        String add = addPrice.getText().toString();
                        String total = totalPrice.getText().toString();
                        listener.onOkPressed(startLocation, endLocation, total);
                    }
                }).create();
    }
}
