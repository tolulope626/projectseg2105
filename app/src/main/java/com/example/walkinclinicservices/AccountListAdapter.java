package com.example.walkinclinicservices;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AccountListAdapter extends ArrayAdapter<employee> {
    private static final String TAG = "AccountListAdapter";
    private Context mContext;
    int mResource;

    public AccountListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<employee> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name = getItem(position).getFirstName();
        String email = getItem(position).getEmail();
        String password = getItem(position).getPassword();

        employee employee2  = new employee(name,email,password);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);

        TextView tvName = (TextView) convertView.findViewById(R.id.textView1);
        TextView tvRole = (TextView) convertView.findViewById(R.id.textView3);

        tvName.setText(name);
        tvRole.setText(email);

        return convertView;
    }



}
