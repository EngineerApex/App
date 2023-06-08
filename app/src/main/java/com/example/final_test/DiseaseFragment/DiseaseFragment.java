package com.example.final_test.DiseaseFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.os.AsyncTask;
import android.util.Log;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
//import com.google.auth.Credentials;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
//import com.google.api.client.json.jackson2.JacksonFactory;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
//import com.google.api.client.auth.oauth2.Credentials;

//import com.google.auth.http.HttpCredentialsAdapter;
//import com.google.auth.oauth2.GoogleCredentials;
import com.google.gson.Gson;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;

//import com.google.api.client.auth.oauth2.Credentials;
import com.google.api.client.json.JsonFactory;
//import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.auth.oauth2.GoogleCredentials;
//import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.auth.http.HttpCredentialsAdapter;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import java.util.Arrays;
import com.google.api.client.http.HttpTransport;
import com.google.gson.Gson;
import com.google.api.client.json.gson.GsonFactory;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.api.client.http.javanet.NetHttpTransport;
import java.io.InputStream;


//import com.google.auth.oauth2.GoogleCredentials;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;

import androidx.fragment.app.Fragment;

import com.example.final_test.R;


public class DiseaseFragment extends Fragment {

    private Spinner diseasespinner;
    private static final String TAG = DiseaseFragment.class.getSimpleName();
    TextView col2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_disease, container, false);

        // Initialize the spinner
        diseasespinner = rootView.findViewById(R.id.diseaseSpinner);
        col2 = rootView.findViewById(R.id.column2);

        // Create a list of items for the spinner
        List<String> items = Arrays.asList(getResources().getStringArray(R.array.diseases_array));

        // Create the custom spinner adapter
        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(requireContext(), android.R.layout.simple_spinner_item, items, 25); // Adjust the text size (18) as desired

        // Set the adapter to the spinner
        diseasespinner.setAdapter(adapter);

        // Set a listener for item selection
        diseasespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedDisease = parent.getItemAtPosition(position).toString();
                fetchDiseaseData(selectedDisease);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        return rootView;
    }

    private void fetchDiseaseData(String disease) {
        // TODO: Implement the logic to fetch data from the Google Sheets API based on the selected disease
        // You can use the disease parameter to determine which data to fetch

        // Create an instance of the FetchSheetDataTask class and execute it
        FetchSheetDataTask fetchSheetDataTask = new FetchSheetDataTask();
        fetchSheetDataTask.execute(disease);

        // Display a toast message for now
        String message = "Fetching data for: " + disease;
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    private class FetchSheetDataTask extends AsyncTask<String, Void, List<List<Object>>> {
        private Exception exception;

        @Override
        protected List<List<Object>> doInBackground(String... params) {
            String selectedDisease = params[0];
            List<List<Object>> values = null;

            try {
                // Set up the Google Sheets API service
                Sheets sheetsService = createSheetsService();

                // Define the spreadsheet ID and range to fetch
                String spreadsheetId = "157MKLoleWozDBIhZhOLNy8Yjy6keza4fLTQZMsuPajc";
                //String range = selectedDisease;
                String range = "Disease!A2:B";  // Range to fetch both Disease_name and Remedy columns

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
        protected void onPostExecute(List<List<Object>> values) {
            if (exception != null) {
                // Handle the exception
                Log.e(TAG, "Error fetching sheet data", exception);
                return;
            }

            if (values == null || values.isEmpty()) {
                // No data available
                Log.d(TAG, "No data available in the sheet");
            } else {
                // Process the fetched data
                //processData(values);
                // Process the fetched data and display the remedy for the selected disease
                String selectedDisease = diseasespinner.getSelectedItem().toString();
                processData(values, selectedDisease);
            }
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

    private void processData(List<List<Object>> values, String selectedDisease) {
        // Process the fetched data and display it on the screen or perform any other desired actions
        // In this example, let's assume the data is a list of rows, and each row contains two values (Column 1 and Column 2)
        for (List<Object> row : values) {
            if (row.size() >= 2) {
                String column1 = String.valueOf(row.get(0));
                String column2 = String.valueOf(row.get(1));

                // Display the data on the screen or perform any other desired actions
                //Log.d(TAG, "Column 1: " + column1 + ", Column 2: " + column2);
                if (column1.equalsIgnoreCase(selectedDisease)) {
                    // Display the remedy for the selected disease
                    Log.d(TAG, "Remedy for " + column1 + ": " + column2);
                    col2.setText(column2);
                }
            }
        }
    }

}





