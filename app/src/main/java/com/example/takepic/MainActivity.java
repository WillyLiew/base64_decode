package com.example.takepic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class MainActivity extends AppCompatActivity {

    DatabaseReference dr;
    Button btn;
    ImageView imageView;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn=findViewById(R.id.takePicBtn);
        imageView=findViewById(R.id.imageView);
        tv=findViewById(R.id.textView2);

        dr=FirebaseDatabase.getInstance().getReference();
        dr.child("photo_WXY").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String test=dataSnapshot.getValue().toString();
                String imageString = test.substring(1,test.length()-1);
                imageString=imageString.replace("%2F","/");
                imageString=imageString.replace("%2B","+");
                String base64Image = imageString.split(",")[1];
                byte[] decodedString = Base64.decode(base64Image,Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                imageView.setImageBitmap(decodedByte);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void takePic(View view) {
        dr.child("capture_Photo").setValue(1);
    }
}
