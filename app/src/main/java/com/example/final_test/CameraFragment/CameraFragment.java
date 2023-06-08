package com.example.final_test.CameraFragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.final_test.DiseaseFragment.DiseaseFragment;
import com.example.final_test.R;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;

import android.graphics.Bitmap;
import android.app.Activity;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import androidx.core.app.ActivityCompat;
import androidx.annotation.NonNull;


//import kotlinx.android.synthetic.main.fragment_camera.*
import android.text.Html;
import okhttp3.*;
import java.io.InputStream;
//import java.awt.Color;
//import java.awt.image.BufferedImage;


public class CameraFragment extends Fragment {

    private static final String TAG = CameraFragment.class.getSimpleName();
    private String leafName;

    private static final int REQUEST_CODE = 22;
    private static final int RESULT_OK = -1;
    private static final int REQUEST_CODE_GALLERY = 24;
    private static final int REQUEST_CODE_CAMERA = 25;
    private static final int PERMISSION_REQUEST_CODE = 123;
    //private static final int PERMISSION_REQUEST_CODE = 100;

    private static final int REQUEST_CODE_CAMERA_PERMISSION = 1001;
    //private static final int REQUEST_CODE_CAMERA = 1;


    Button capture;
    Button gallery;
    ImageView cameraw;
    Button sendbutton;
    TextView pred;

    TextView scif;
    TextView orig;
    TextView mnm;
    TextView use;

    Bitmap bitmap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_camera, container, false);

        capture = rootView.findViewById(R.id.camerabutton);
        cameraw = rootView.findViewById(R.id.camerawindow);
        gallery = rootView.findViewById(R.id.gallerybutton);
        sendbutton = rootView.findViewById(R.id.sendButton);

        pred = rootView.findViewById(R.id.leafname);
        scif = rootView.findViewById(R.id.scientificname);
        orig = rootView.findViewById(R.id.origin);
        mnm = rootView.findViewById(R.id.type);
        use = rootView.findViewById(R.id.uses);

        //Capture Button
        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, REQUEST_CODE);
            }
        });

        //Upload from Gallery Button
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
                } else {
                    openGallery();
                }
            }
        });

        sendbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bitmap != null) {
                    // Convert the bitmap to a byte array
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                    sendImage(byteArray);
                } else {
                    Toast.makeText(getContext(), "No image selected", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d(TAG, "onActivityResultXXXXXXXXXXXXXXXXXXXXXX: requestCode=" + requestCode + ", resultCode=" + resultCode + ", data=" + data);

        // Check if the camera permission is granted
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // Request the camera permission
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CODE);
        } else {
            // Camera permission is already granted, proceed with capturing the image
            //captureImage();
            openCamera();
        }

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                Bundle extras = data.getExtras();
                if (extras != null && extras.containsKey("data")) {
                    Bitmap photo = (Bitmap) extras.get("data");
                    cameraw.setImageBitmap(photo);
                    bitmap = photo;
                } else {
                    // If the captured image is not available in extras, try to load it from the URI
                    Uri uri = data.getData();
                    if (uri != null) {
                        try {
                            InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
                            Bitmap photo = BitmapFactory.decodeStream(inputStream);
                            cameraw.setImageBitmap(photo);
                            inputStream.close();
                            bitmap = photo;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } else if (resultCode == Activity.RESULT_OK) {
            // Handle gallery result
            if (requestCode == REQUEST_CODE_GALLERY) {
                Uri selectedImageUri = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                Cursor cursor = getActivity().getContentResolver().query(selectedImageUri, filePathColumn, null, null, null);
                if (cursor != null) {
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();
                    Bitmap photo = BitmapFactory.decodeFile(filePath);
                    cameraw.setImageBitmap(photo);
                    int width = photo.getWidth();
                    int height = photo.getHeight();
                    Toast.makeText(getContext(), "Image Resolution: " + width + " x " + height, Toast.LENGTH_SHORT).show();
                    bitmap = photo;
                }
            }
        } else {
            Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
        }



        ////OnActivity ends here---------
    }

    private boolean checkCameraPermission() {
        int resultCamera = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA);
        int resultStorage = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return resultCamera == PackageManager.PERMISSION_GRANTED && resultStorage == PackageManager.PERMISSION_GRANTED;
    }


    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, REQUEST_CODE_CAMERA_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                captureImage();
            } else {
                Toast.makeText(getContext(), "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_CODE);
        } else {
            Toast.makeText(getContext(), "No camera app found", Toast.LENGTH_SHORT).show();
        }
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(cameraIntent, REQUEST_CODE_CAMERA); //request_code_camera
        } else {
            Toast.makeText(getContext(), "No camera app found: open Camera", Toast.LENGTH_SHORT).show();
        }
    }


    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, REQUEST_CODE_GALLERY);
    }


    private void sendImage(byte[] byteArray) {

//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//        byte[] byteArray = stream.toByteArray();

        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("multipart/form-data");
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", "image.jpg", RequestBody.create(MediaType.parse("image/*jpg"), byteArray))
                .build();

        Request request = new Request.Builder()
                .url("https://rfa-fastapi1.onrender.com/predict") //http://192.168.56.1:8000/predict -- https://rfa-fastapi1.onrender.com/predict -- https://ml-api-mzmc.onrender.com/predict -- https://machinelearning-8lze.onrender.com
                .post(requestBody)
                .build();

        //okhttp
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Handle failure
                Log.e("Request", "Failedddd", e);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "Request failed!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    // Handle successful response
                    final String responseBody = response.body().string().replaceAll("[\\[\\]\"]", ""); //  ["lemon"]
                    //final String predictedLabel = responseBody.replace("[", "").replace("]", "");

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(), "Predicted label: " + responseBody, Toast.LENGTH_SHORT).show();
                            String pname = "<font color='#00FF00'><b>Plant name: </b></font> " + responseBody;
                            pred.setText(Html.fromHtml(pname));

                            // Fetch data from the Google Sheet based on the leaf name
                            fetchSheetData(responseBody);
                        }
                    });
                } else {
                    // Handle unsuccessful response
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(), "Request failed2!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }


        });

    }

    private void fetchSheetData(String leafName) {
        // TODO: Implement the logic to fetch data from the Google Sheets API based on the selected disease

        String range = "Leaf!A2:E"; // Specify the range for the "Leaf" sheet

        // Create an instance of the FetchSheetDataTask class and execute it
        FetchSheetDataTask fetchSheetDataTask = new FetchSheetDataTask(leafName);
        fetchSheetDataTask.execute(range, leafName);
    }


    private class FetchSheetDataTask extends AsyncTask<String, Void, List<List<Object>>> {
        private Exception exception;
        private String leafName;

        public FetchSheetDataTask(String leafName) {
            this.leafName = leafName;
        }

        @Override
        protected List<List<Object>> doInBackground(String... params) {

            String range = params[0];
            List<List<Object>> values = null;

            try {
                // Set up the Google Sheets API service
                Sheets sheetsService = createSheetsService();

                // Define the spreadsheet ID
                String spreadsheetId = "157MKLoleWozDBIhZhOLNy8Yjy6keza4fLTQZMsuPajc";

                // Make the API request to fetch the values
                ValueRange response = sheetsService.spreadsheets().values()
                        .get(spreadsheetId, range)
                        .execute();

                values = response.getValues();
            } catch (Exception e) {
                this.exception = e;
            }

            return values;
        }

        @Override
        protected void onPostExecute(List<List<Object>> result) {
            if (result != null && result.size() > 0) {
                // Iterate through the rows to find the matching leaf name
                for (List<Object> row : result) {
                    String leafName = row.get(0).toString();
                    if (leafName.equals(this.leafName)) {
                        // Fetch the values from the matching row
                        String column1 = row.get(0).toString();
                        String column2 = row.get(1).toString();
                        String column3 = row.get(2).toString();
                        String column4 = row.get(3).toString();
                        String column5 = row.get(4).toString();

                        // Update the UI elements with the fetched data
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String a = "&nbsp;" + "<font color='#00FF00'><b>Scif. name:</b></font> " + column2;
                                String b = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + "<font color='#00FF00'><b>Type:</b></font> "  + column3;
                                String c = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + "<font color='#00FF00'><b>Origin:</b></font> " + column4;
                                String d = "<font color='#00FF00'><b>Uses</b></font><br>" + column5;
                                scif.setText(Html.fromHtml(a));
                                mnm.setText(Html.fromHtml(b));
                                orig.setText(Html.fromHtml(c));
                                use.setText(Html.fromHtml(d));
                            }
                        });

                        // Display the values or perform any other operation as needed
                        Log.d("Leaf Data", "Column 1: " + column1 + ", Column 2: " + column2 + ", Column 3: " + column3 + ", Column 4: " + column4 + ", Column 5: ");
                        return;
                    }
                }
            }
            // Leaf name not found or no data fetched
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getContext(), "No data found for the leaf", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private Sheets createSheetsService() throws IOException {
        // Load the credentials JSON file (generated from the Google Cloud Console)

        GoogleCredential credential = GoogleCredential.fromStream(getResources().openRawResource(R.raw.credentials))
                .createScoped(Arrays.asList(SheetsScopes.SPREADSHEETS_READONLY));

        // Build the Sheets service
        return new Sheets.Builder(
                new NetHttpTransport(),
                JacksonFactory.getDefaultInstance(),
                credential)
                .setApplicationName(getString(R.string.app_name))
                .build();


    }


}