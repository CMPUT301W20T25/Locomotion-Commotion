package com.example.locomotioncommotion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

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

        start.setText(request.getStartLocation().toString());
        end.setText(request.getEndLocation().toString());

        return view;

    }

}
