package com.example.z3;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Messenger;
import android.os.ParcelFileDescriptor;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.z3.RoomDatabase.Task;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class InfoActivity extends AppCompatActivity {


    ShortTask deliveredTask;
    TextView title;
    TextView category;
    TextView description;
    ImageView photo;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        deliveredTask = (ShortTask) getIntent().getSerializableExtra("Task");
        title = findViewById(R.id.titleShortTaskInfo);
        category = findViewById(R.id.categoryShortTaskInfo);
        description = findViewById(R.id.descriptionShortTaskInfo);
        photo = findViewById(R.id.photoShortTaskInfo);
        title.setText(deliveredTask.getTitle());
        category.setText(deliveredTask.getCategory());
        description.setText(deliveredTask.getDescription());
        if(!deliveredTask.getPhoto().equals("")){
            new GetImageFromUrl(photo).execute(deliveredTask.getPhoto());
        }
        else
            photo.setImageResource(R.drawable.no_image);
    }

    public class GetImageFromUrl extends AsyncTask<String, Void, Bitmap>{
        ImageView imageView;
        public GetImageFromUrl(ImageView img){
            this.imageView = img;
        }
        @Override
        protected Bitmap doInBackground(String... url) {
            String stringUrl = url[0];
            bitmap = null;
            InputStream inputStream;
            try {
                inputStream = new java.net.URL(stringUrl).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (Exception e) {
                return null;
            }
            return bitmap;
        }
        @Override
        protected void onPostExecute(Bitmap bitmap){
            super.onPostExecute(bitmap);
            if(bitmap != null)
                imageView.setImageBitmap(bitmap);
            else
                imageView.setImageResource(R.drawable.no_image);
        }
    }
}