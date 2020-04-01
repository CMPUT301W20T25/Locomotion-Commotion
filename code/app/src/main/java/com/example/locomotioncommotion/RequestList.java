package com.example.locomotioncommotion;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

/**
 * Custom list class for displaying request objects
 */

public class RequestList extends ArrayAdapter<Request> {
    private ArrayList<Request> requests;
    private Context context;
    public RequestList(Context context, ArrayList<Request> requests){
        super(context,0, requests);
        this.requests = requests;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        return super.getView(position, convertView, parent);
        View view = convertView;

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.list_content_request, parent,false);
        }

        Request request = requests.get(position);

        TextView start = view.findViewById(R.id.startLoc_text);
        TextView end = view.findViewById(R.id.endLoc_text);
        TextView riderText = view.findViewById(R.id.rider_text);
        TextView driverText = view.findViewById(R.id.driver_text);

        start.setText(request.getStartLocation().getName().toString());
        end.setText(request.getEndLocation().getName().toString());
        if(request.getRiderUsername() != null) {
            riderText.setText(request.getRiderUsername().toString());
            riderText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    inspectRider(v);
                }
            });
        } else{
            riderText.setText("N/A");
        }
        if(request.getDriverUsername() != null){
            driverText.setText(request.getDriverUsername().toString());
            driverText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    inspectDriver(v);
                }
            });
        } else{
            driverText.setText("N/A");
        }

        return view;

    }

    public void inspectRider(View view){
        Intent intent = new Intent(context, InspectProfile.class);
        TextView riderText = (TextView) view;
        intent.putExtra("username",riderText.getText().toString());
        context.startActivity(intent);
    }

    public void inspectDriver(View view){
        Intent intent = new Intent(context, InspectProfile.class);
        TextView driverText = (TextView) view;
        intent.putExtra("username",driverText.getText().toString());
        context.startActivity(intent);
    }

}
