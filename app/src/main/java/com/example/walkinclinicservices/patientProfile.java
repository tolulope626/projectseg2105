package com.example.walkinclinicservices;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class patientProfile extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mDatabase,nDatabase;

    String clinic;
    ArrayAdapter<String> sadapter;


    private EditText addressText;
    private Button logoutBtn,searchBtn, bookBtn;
    private Spinner toSpinner,fromSpinner,serviceSpinner,daySpinner;
    private ListView clinicList;

    public ArrayList<String> avClinicList;
    public ArrayList<String> ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        nDatabase = FirebaseDatabase.getInstance().getReference();

        clinic = "";


        logoutBtn = (Button) findViewById(R.id.logoutBtn);
        bookBtn = (Button) findViewById(R.id.bookBtn);
        searchBtn = (Button) findViewById(R.id.searchBtn);
        toSpinner = (Spinner) findViewById(R.id.toSpinner);
        fromSpinner = (Spinner) findViewById(R.id.fromSpinner);
        serviceSpinner = (Spinner) findViewById(R.id.serviceSpinner);
        daySpinner = (Spinner) findViewById(R.id.daySpinner);
        clinicList = (ListView) findViewById(R.id.clinicList);

        //set the time spinners
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Time,R.layout.support_simple_spinner_dropdown_item);
        toSpinner.setAdapter(adapter);
        fromSpinner.setAdapter(adapter);

        //set the service spinner
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,R.array.Services,R.layout.support_simple_spinner_dropdown_item);
        serviceSpinner.setAdapter(adapter2);

        //set the day spinner
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,R.array.Days,R.layout.support_simple_spinner_dropdown_item);
        daySpinner.setAdapter(adapter3);

        //initialize lists
        avClinicList = new ArrayList<>();
        //avClinicList.add("A");
        //avClinicList.add("B");
        //avClinicList.add("B");
        //avClinicList.add("B");
        ids = new ArrayList<>();

        //set the listView
        sadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,avClinicList);
        clinicList.setAdapter(sadapter);

        //check if someone is signed in
        if(mUser == null){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }else {
            //Set service spinner
            mDatabase.child("Service").addValueEventListener(new ValueEventListener() {
                final List<String> serviceList = new ArrayList<String>();
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot serviceSnapshot : dataSnapshot.getChildren()){

                        String service = serviceSnapshot.child("serviceName").getValue(String.class);
                        serviceList.add(service);

                    }

                    ArrayAdapter<String> serviceAdapter = new ArrayAdapter<String>(patientProfile.this,android.R.layout.simple_spinner_item,serviceList);
                    serviceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    serviceSpinner.setAdapter(serviceAdapter);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }

        logoutBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                mAuth.signOut();
                finish();
                startActivity(new Intent(patientProfile.this, MainActivity.class));

            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Toast.makeText(patientProfile.this,"Hello",Toast.LENGTH_SHORT);
                avClinicList.clear();
                ids.clear();

                final String from = fromSpinner.getSelectedItem().toString();
                final String to = toSpinner.getSelectedItem().toString();
                final String service = serviceSpinner.getSelectedItem().toString();
                final String day = daySpinner.getSelectedItem().toString().trim();
                String word = "";
                String word2 = "";

                mDatabase.child("Hours").addValueEventListener(new ValueEventListener() {
                    //@Override
                    String word = "";
                    String word2 = "";

                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot serviceSnapshot : dataSnapshot.getChildren()){


                            String hour = serviceSnapshot.child(day.toLowerCase()).getValue(String.class);

                            for(int i = 0; i<hour.length();i++){
                                if(hour.charAt(i) == ' '){
                                    word2 = word;
                                    word = "";
                                    continue;
                                }

                                word += hour.charAt(i);
                            }

                            int fromnum = Integer.parseInt(word2);
                            int tonum = Integer.parseInt(word);

                            String updatedf = "";
                            String updatedt = "";

                            if (from.length() == 5){
                                updatedf = Character.toString(from.charAt(0)) + Character.toString(from.charAt(0));
                            }else { updatedf = Character.toString(from.charAt(0)); }

                            if (to.length() == 5){
                                updatedt = Character.toString(to.charAt(0)) + Character.toString(to.charAt(0));
                            }else { updatedt = Character.toString(to.charAt(0)); }


                            if(fromnum <= Integer.parseInt(updatedf) || tonum > Integer.parseInt(updatedf)){
                                ids.add(serviceSnapshot.getKey());
                                updateList(serviceSnapshot.getKey());
                                //avClinicList.add(clinic);
                                Toast.makeText(patientProfile.this,"Trying",Toast.LENGTH_LONG).show();
                            }

                        }

                        sadapter = new ArrayAdapter<String>(patientProfile.this, android.R.layout.simple_list_item_1,avClinicList);
                        clinicList.setAdapter(sadapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



                /*for(int i = 0; i<ids.size();i++){
                  mDatabase.child("Employee").child(ids.get(i).trim()).addListenerForSingleValueEvent(new ValueEventListener() {
                      @Override
                      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                          employee curemploy = dataSnapshot.getValue(employee.class);
                          clinic = curemploy.getClinic();
                          avClinicList.add(clinic);
                          Toast.makeText(patientProfile.this,clinic,Toast.LENGTH_LONG).show();

                      }

                      @Override
                      public void onCancelled(@NonNull DatabaseError databaseError) {

                      }
                  });
                }*/

                //sadapter = new ArrayAdapter<String>(patientProfile.this, android.R.layout.simple_list_item_1,avClinicList);
                //clinicList.setAdapter(sadapter);

                //sadapter.notifyDataSetChanged();
                //startActivity(new Intent(patientProfile.this, patientProfile.class));

            }
        });

        bookBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                startActivity(new Intent(patientProfile.this, patientProfile2.class));

            }
        });


    }

    public void updateList(String id){
        mDatabase.child("Employee").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                employee emp = dataSnapshot.getValue(employee.class);
                clinic = emp.getClinic();
                avClinicList.add(clinic);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    /*@Override
    protected void onStart() {
        super.onStart();

        mDatabase.child("Hours").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                serviceList.clear();

                for(DataSnapshot serviceSnapshot : dataSnapshot.getChildren()){
                    Service service = serviceSnapshot.getValue(Service.class);
                    serviceList.add(service);
                    ids.add(serviceSnapshot.getKey());
                }

                ServiceListAdapter adapter = new ServiceListAdapter(adminProfile.this,R.layout.adapter_view_layout,serviceList);
                mListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }*/
}
