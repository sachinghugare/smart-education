package com.example.smarteducation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import  com.google.firebase.storage.FirebaseStorage.*;
import com.google.firebase.storage.UploadTask;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;

import java.io.File;
import java.io.IOException;

public class upload<parent> extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private static final int PICK_IMAGE_REQUEST = 234;
    private ImageView imageView;
    private Button buttonChoose, buttonUpload;
    private StorageReference mStorageRef;
    private String mypath;
    private EditText y,dep,sub,div,batch,type,fname;
    private String my,mdep,msub,mdiv,mbatch,mtype,mfname;
    private Spinner spinner;
    private static final String[] path = {"FE", "SE", "TE", "BE"};
    Uri filePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        spinner = (Spinner) findViewById(R.id.Year);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.year));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        //imageView=(ImageView) findViewById(R.id.imageView);
        buttonChoose = (Button) findViewById(R.id.buttonChoose);
        buttonUpload = (Button) findViewById(R.id.buttonUpload);
        dep = findViewById(R.id.department);
        sub   = findViewById(R.id.subject);
        batch      = findViewById(R.id.batch);
        fname=findViewById(R.id.fname);

        buttonChoose.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);

        mStorageRef = FirebaseStorage.getInstance().getReference();
    }

    private void showFileChooser() {

        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "select an image"), PICK_IMAGE_REQUEST);
    }

    private void up() {

        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            //mypath=my+"/"+(dep.getText().toString())+"/"+(sub.getText().toString())+"/"+(type.getText().toString())+"/"+(fname.getText().toString())+".pdf";
            // Uri file = Uri.fromFile(new File("path/to/images/rivers.jpg"));
            //Toast.makeText(this,mypath,Toast.LENGTH_SHORT).show();
            StorageReference riversRef = mStorageRef.child((new StringBuilder().append("FE").append("/").append("Comp").append("/").append("A").append("/").append("AT4").append("/").append("book").append("/").append("intro").append(".pdf").toString()));

            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Get a URL to the uploaded content
                            //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "File Uploaded", Toast.LENGTH_LONG);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            // ...
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG);
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            progressDialog.setMessage(((int) progress) + "% Uploaded...");
                        }
                    });


        } else {
            //display toast
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
/*
            try {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }
    }

    @Override
    public void onClick(View view) {
       // mdep=dep.getText().toString().trim();
        //msub=sub.getText().toString().trim();
        //mtype=type.getText().toString().trim();
        //mfname=fname.getText().toString().trim();
        //mbatch=batch.getText().toString().trim();
        //mdiv=div.getText().toString().trim();
        if (view == buttonChoose) {
            //open file chooser
            showFileChooser();
        } else if (view == buttonUpload) {
            up();
            //upload file to storage
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        my=adapterView.getSelectedItem().toString();
        Toast.makeText(this,adapterView.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
