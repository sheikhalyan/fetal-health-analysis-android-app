package com.example.finalfyp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Aloka extends AppCompatActivity {
    SessionManager sessionManager;
    TextView getname,femurLengthTextView,imagePathTextView,plotImagePathTextView;
    ImageView imageView;
    Button btnSshowImage, btnUuploadImage;

    Uri selectedImage;

    String imageUrl = "http://192.168.0.107:5000/get_image?filename=ALOKA_plot.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aloka);
        sessionManager = new SessionManager(getApplicationContext());
        getname = findViewById(R.id.getname);
        String username = getIntent().getStringExtra("name");
        getname.setText(username);
        imageView = findViewById(R.id.plotImageView2);
        femurLengthTextView = findViewById(R.id.femurLengthTextView);
        imagePathTextView = findViewById(R.id.imagePathTextView);
        plotImagePathTextView = findViewById(R.id.plotImagePathTextView);
        btnSshowImage = findViewById(R.id.btnShowImage);
        btnUuploadImage = findViewById(R.id.btnUploadImage);
        btnUuploadImage.setVisibility(View.GONE);

        try {
            btnSshowImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 3);

                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            selectedImage = data.getData();
            imageView.setImageURI(selectedImage);
            if (imageView.getDrawable() != null) {
                btnSshowImage.setVisibility(View.GONE);
                btnUuploadImage.setVisibility(View.VISIBLE);

                btnUuploadImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (selectedImage != null) {
                            uploadImage(selectedImage); // Call uploadImage method with selected image URI
                        } else {
                            // Handle case where no image is selected
                            Toast.makeText(getApplicationContext(), "No image selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        }
        // Zoomy.Builder builder = new Zoomy.Builder(this).target(imageView).animateZooming(false).enableImmersiveMode(false);

    }

    private File saveBitmapToFile(Bitmap bitmap) {
        // Create a file in the cache directory
        File file = new File(getCacheDir(), "selectedImage");
        try {
            // Compress the bitmap and write it to the file
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    // Define a method to convert URI to Bitmap
    private Bitmap uriToBitmap(Uri uri) throws IOException {
        return MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
    }

    private void uploadImage(Uri imageUri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            File imageFile = saveBitmapToFile(bitmap);
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), imageFile);
            MultipartBody.Part body = MultipartBody.Part.createFormData("file", imageFile.getName(), requestFile);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.0.107:5000")
                    .build();

            // Create an instance of the ApiService interface
            MyAPI apiService = retrofit.create(MyAPI.class);


            // Call the uploadImage method
            Call<ResponseBody> call = apiService.uploadImage3(body);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        // Image upload successful
                        //textView.setText("Image upload successful");
// Retrieve parameters from the server response body
                        try {
                            String responseBody = response.body().string();
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(responseBody);
                            } catch (JSONException e) {
                                //throw new RuntimeException(e);
                                // textView.setText("Error: "+e);
                            }

                            // Get the parameters
                            String machine = jsonObject.getString("machine");
                            double femurLength = jsonObject.getDouble("femur_length");
                            String ImagePath = jsonObject.getString("image_path");
                            String plotImagePath = jsonObject.getString("plot_path");

                            //textView.setText("");
                            btnUuploadImage.setVisibility(View.GONE);
                            //machineTextView.setText("Machine: " + machine);
                            femurLengthTextView.setText("Femur Length: " + femurLength);
                            // imagePathTextView.setText("Image Path: " + ImagePath);
                            // plotImagePathTextView.setText("Plot Image Path: " + plotImagePath);
                            // Now you can load the image from the imagePath if needed
                            // For example, if imagePath is a URL, you can use a library like Picasso or Glide to load the image into the ImageView


                            Picasso.get().load(imageUrl)
                                    .placeholder(R.drawable.back2) // Placeholder image while loading
                                    .error(R.drawable.back1) // Error image if unable to load
                                    .into(imageView, new com.squareup.picasso.Callback() {
                                        @Override
                                        public void onSuccess() {

                                        }

                                        @Override
                                        public void onError(Exception e) {
                                            Log.e("Picasso", "Error loading image: " + e.getMessage());
                                        }
                                    }); // Load into the ImageView
                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                            //textView.setText("Error while parsing response: " + e.getMessage());
                        }
                        // Log.d(TAG, "Image upload successful");
                    } else {
                        // Image upload failed
                        // textView.setText("Image upload failed");
                    }
                }


                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    // Network error or server error
                    //textView.setText("Image upload failed. Error: " + t.getMessage());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }

