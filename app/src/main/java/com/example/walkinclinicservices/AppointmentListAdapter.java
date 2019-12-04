package com.example.walkinclinicservices;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class AppointmentListAdapter extends ArrayAdapter<Appointment> {

    private static final String TAG = "AppointmentListAdapter";
    private Context mContext;
    int mResource;

    public AppointmentListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Appointment> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name = getItem(position).getClinicname();
        String time = getItem(position).getTime();

        Appointment app = new Appointment(name,time);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);

        TextView tvName = (TextView) convertView.findViewById(R.id.textView1);
        TextView tvRole = (TextView) convertView.findViewById(R.id.textView3);

        tvName.setText(name);
        tvRole.setText(time);

        return convertView;
    }

}
