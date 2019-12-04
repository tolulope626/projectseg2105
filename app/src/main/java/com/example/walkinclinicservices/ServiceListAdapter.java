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
import java.util.List;

public class ServiceListAdapter extends ArrayAdapter<Service> {

    private static final String TAG = "ServiceListAdapter";
    private Context mContext;
    int mResource;

    public ServiceListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Service> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name = getItem(position).getServiceName();
        String role = getItem(position).getRoleName();

        Service service  = new Service(name,role);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);

        TextView tvName = (TextView) convertView.findViewById(R.id.textView1);
        TextView tvRole = (TextView) convertView.findViewById(R.id.textView3);

        tvName.setText(name);
        tvRole.setText(role);

        return convertView;
    }
}
