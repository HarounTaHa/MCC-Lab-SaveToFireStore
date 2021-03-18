package com.example.storetofirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.storetofirestore.adapter.MyAdapter;
import com.example.storetofirestore.model.PersonDetails;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText ed_name,ed_number,ed_address;
    String name , number , address;
    FirebaseFirestore fireStore;
    Button buttonAddToFireStore;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    List<PersonDetails> personDetailsList;
    MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        ed_name=findViewById(R.id.editText_name);
        ed_number=findViewById(R.id.editText_number);
        ed_address=findViewById(R.id.editText_address);
        buttonAddToFireStore =findViewById(R.id.button_add);
        progressBar=findViewById(R.id.progressBar);
        personDetailsList=new ArrayList<>();
        //        -----------------------------------------------------------------
        fireStore= FirebaseFirestore.getInstance();
//        ---------------------------------------------------------------
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        ------------------------------------ Retrieve data from FireStore ------------------------
        progressBar.setVisibility(View.VISIBLE);
        CollectionReference collectionReference = fireStore.collection("contactDetails");
        Task<QuerySnapshot> task=collectionReference.get();
        task.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                Iterator<QueryDocumentSnapshot> iterator=queryDocumentSnapshots.iterator();
                while (iterator.hasNext()){
                PersonDetails personDetails=iterator.next().toObject(PersonDetails.class);
                personDetailsList.add(personDetails);
                }
                adapter= new MyAdapter(personDetailsList);
                recyclerView.setAdapter(adapter);
                progressBar.setVisibility(View.INVISIBLE);
            }
        });



//---------------------Click Add Button--------------------------------------------------
        buttonAddToFireStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                            if(!ed_name.getText().toString().isEmpty()
                                    && !ed_number.getText().toString().isEmpty()
                                    && !ed_address.getText().toString().isEmpty()){
                                progressBar.setVisibility(View.VISIBLE);
                                      name = ed_name.getText().toString();
                                      address = ed_address.getText().toString();
                                      number = ed_number.getText().toString();
//                         ------------------------ Add To FireBase ------------------------------
                                Map<String,Object> personDetails=new HashMap<>();
                                personDetails.put("name",name);
                                personDetails.put("number",number);
                                personDetails.put("address",address);
                                CollectionReference collectionReference = fireStore.collection("contactDetails");
                                Task<DocumentReference> documentReferenceTask= collectionReference.add(personDetails);
                                documentReferenceTask.addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(getApplicationContext(),"تمت الاضافة بنجاج",Toast.LENGTH_LONG).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                     progressBar.setVisibility(View.INVISIBLE);
                                     Toast.makeText(getApplicationContext(),"حدث خطأ في الاضافة",Toast.LENGTH_LONG).show();

                                    }
                                });
                            }else{
                                Toast.makeText(getApplicationContext(),"Empty field",Toast.LENGTH_LONG).show();
                            }
            }
        });


    }
    //---------------------------------------------------------------------------------
    public PersonDetails addDetails(String name, String number , String address){
        PersonDetails personDetails = new PersonDetails(name, number,address);
        return personDetails;
    }
}

