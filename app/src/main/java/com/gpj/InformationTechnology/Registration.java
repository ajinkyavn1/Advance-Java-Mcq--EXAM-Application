package com.gpj.InformationTechnology;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.vikasojha.quizbee.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.util.HashMap;

public class Registration extends AppCompatActivity {
    Button result,register,logout;
    EditText editTextPassword,editTextEmail,Name,Enrollment;
    ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        editTextEmail=findViewById(R.id.editTextEmail);
        editTextPassword=findViewById(R.id.editTextPassword);
        Name=findViewById(R.id.name);
        Enrollment=findViewById(R.id.Enrollment);
        progressDialog = new ProgressDialog(this);
        register=findViewById(R.id.buttonRegistration);
        result=findViewById(R.id.result);
        ImageView logout=  findViewById(R.id.Log_out);
        databaseReference =FirebaseDatabase.getInstance().getReference("users");
        firebaseAuth = FirebaseAuth.getInstance();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                onDestroy();
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();




            }
        });

        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent=new Intent(getApplicationContext(),Msbteresult.class);
                startActivity(intent);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);


            }
        });


    }
    private void registerUser(){
     final String name,enrollment;
        //getting email and password from edit texts
        final String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();
                name=Name.getText().toString();
        enrollment=Enrollment.getText().toString();

        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(name)){
            Toast.makeText(this,"Please enter Name",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(enrollment)){
            Toast.makeText(this,"Please enter Enrollment",Toast.LENGTH_LONG).show();
            return;
        }

        //email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();
        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){
                            //display some message here
                            //String user=firebaseAuth.getCurrentUser().getUid();

                            //DatabaseReference reference=databaseReference.child(user);
                            //reference.child("Email").setValue(email);
                            //reference.child("Name").setValue(name);
                            //reference.child("Enrollment").setValue(enrollment);
                            //reference.child("uid").setValue(user);
                            String classs="IF5I";
                            FirebaseUser user=firebaseAuth.getCurrentUser();
                            String email=user.getEmail();
                            String uid=user.getUid();
                            HashMap<String, String> hashMap=new HashMap<String, String>();
                            hashMap.put("email",email);
                            hashMap.put("uid",uid);
                            hashMap.put("Name",name);
                            hashMap.put("Enrollment",enrollment);
                            hashMap.put("Class",classs);
                            FirebaseDatabase database=FirebaseDatabase.getInstance();
                            DatabaseReference reference=database.getReference("users");
                            reference.child(uid).setValue(hashMap);


                            Toast.makeText(Registration.this,"Successfully registered",Toast.LENGTH_LONG).show();
                        }else{
                            //display some message here
                            Toast.makeText(Registration.this,"User already  registered",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        clearApplicationData();
    }

    public void clearApplicationData() {
        File cache = getCacheDir();
        File appDir = new File(cache.getParent());
        if (appDir.exists()) {
            String[] children = appDir.list();
            for (String s : children) {
                if (!s.equals("lib")) {
                    deleteDir(new File(appDir, s));
                    Log.i("EEEEEERRRRRROOOOOOORRRR", "**************** File /data/data/APP_PACKAGE/" + s + " DELETED *******************");
                }
            }
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            int i = 0;
            while (i < children.length) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
                i++;
            }
        }

        assert dir != null;
        return dir.delete();
    }
}

