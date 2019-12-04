package com.example.walkinclinicservices;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
    public int counter;


    private EditText addressText;
    private Button logoutBtn,searchBtn,viewBBtn;
    private Spinner toSpinner,fromSpinner,serviceSpinner,daySpinner;
    private ListView clinicList;

    public ArrayList<String> avClinicList;
    public ArrayList<String> avService;
    public ArrayList<String> ids;
    public ArrayList<String> ids2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        nDatabase = FirebaseDatabase.getInstance().getReference();

        clinic = "";

        counter = 0;


        logoutBtn = (Button) findViewById(R.id.logoutBtn);
        searchBtn = (Button) findViewById(R.id.searchBtn);
        viewBBtn = (Button) findViewById(R.id.viewBBtn);
        toSpinner = (Spinner) findViewById(R.id.toSpinner);
        fromSpinner = (Spinner) findViewById(R.id.fromSpinner);
        serviceSpinner = (Spinner) findViewById(R.id.serviceSpinner);
        daySpinner = (Spinner) findViewById(R.id.daySpinner);
        clinicList = (ListView) findViewById(R.id.clinicList);

        //set the time spinners
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Time, R.layout.support_simple_spinner_dropdown_item);
        toSpinner.setAdapter(adapter);
        fromSpinner.setAdapter(adapter);

        //set the service spinner
        //ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,R.array.Services,R.layout.support_simple_spinner_dropdown_item);
        //serviceSpinner.setAdapter(adapter2);

        //set the day spinner
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.Days, R.layout.support_simple_spinner_dropdown_item);
        daySpinner.setAdapter(adapter3);

        //initialize lists
        avClinicList = new ArrayList<>();
        avService = new ArrayList<>();


        ids = new ArrayList<>();
        ids2 = new ArrayList<>();

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

        clinicList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String who = ids.get(position);
                String i = clinicList.getItemAtPosition(position).toString();
                final String from = fromSpinner.getSelectedItem().toString();
                final String to = toSpinner.getSelectedItem().toString();

                String updatedf = "";
                String updatedt = "";

                if (from.length() == 5){
                    updatedf = Character.toString(from.charAt(0)) + Character.toString(from.charAt(1));
                }else { updatedf = Character.toString(from.charAt(0)); }

                if (to.length() == 5){
                    updatedt = Character.toString(to.charAt(0)) + Character.toString(to.charAt(1));
                }else { updatedt = Character.toString(to.charAt(0)); }

                Intent newint = new Intent(patientProfile.this, patientProfile2.class);
                newint.putExtra("id", who);
                newint.putExtra("name", i);
                newint.putExtra("From", updatedf);
                newint.putExtra("To", updatedt);
                startActivity(newint);
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                avClinicList.clear();
                ids.clear();

                final String from = fromSpinner.getSelectedItem().toString();
                final String to = toSpinner.getSelectedItem().toString();
                final String service = serviceSpinner.getSelectedItem().toString();
                final String day = daySpinner.getSelectedItem().toString().trim();
                String word = "";
                String word2 = "";


                mDatabase.child("Hours").addValueEventListener(new ValueEventListener() {
                    @Override
                    //String word = "";
                    //String word2 = "";

                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot serviceSnapshot : dataSnapshot.getChildren()){


                            String hour = serviceSnapshot.child(day.toLowerCase()).getValue(String.class);
                            String hour2 = serviceSnapshot.child(day.toLowerCase() + '2').getValue(String.class);


                            int fromnum = Integer.parseInt(hour);
                            int tonum = Integer.parseInt(hour2);



                            String updatedf = "";
                            String updatedt = "";

                            if (from.length() == 5){
                                updatedf = Character.toString(from.charAt(0)) + Character.toString(from.charAt(0));
                            }else { updatedf = Character.toString(from.charAt(0)); }

                            if (to.length() == 5){
                                updatedt = Character.toString(to.charAt(0)) + Character.toString(to.charAt(0));
                            }else { updatedt = Character.toString(to.charAt(0)); }


                            if(fromnum <= Integer.parseInt(updatedf) || tonum > Integer.parseInt(updatedt)){
                                //ids.add(serviceSnapshot.getKey());
                                checkService(service, serviceSnapshot.getKey());


                            }

                        }

                        //sadapter = new ArrayAdapter<String>(patientProfile.this, android.R.layout.simple_list_item_1,avClinicList);
                        //clinicList.setAdapter(sadapter);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });


        viewBBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startActivity(new Intent(patientProfile.this, Bookings.class));

            }
        });



    }

    public void checkService(final String service, final String id){

            mDatabase.child("ClinicService").child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot serviceSnapshot : dataSnapshot.getChildren()) {
                        Service serv = serviceSnapshot.getValue(Service.class);
                        String idk = serv.getServiceName();

                        if(idk.equals(service)){
                            updateList(id);
                            break;
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


    }

    public void updateList(final String id){
        mDatabase.child("Employee").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot empSnapshot : dataSnapshot.getChildren()){
                    if(empSnapshot.getKey().equals(id)){

                        employee emp = empSnapshot.getValue(employee.class);
                        clinic = emp.getClinic();
                        avClinicList.add(clinic);
                        ids.add(empSnapshot.getKey());
                    }
                }

                sadapter = new ArrayAdapter<String>(patientProfile.this, android.R.layout.simple_list_item_1,avClinicList);
                clinicList.setAdapter(sadapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    public void OnOpenInGoogleMaps (View view) {
        EditText teamAddres = (EditText) findViewById(R.id.addressText);
// Create a Uri from an intent string. Use the result to create an Intent.
        Uri gmmIntentUri = Uri.parse("http://maps.google.co.in/maps?q="+teamAddres.getText()); // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri); // Make the Intent explicit by setting the Google Maps package
        mapIntent.setPackage("com.google.android.apps.maps");
// Attempt to start an activity that can handle the Intent
        startActivity(mapIntent);
    }


}
