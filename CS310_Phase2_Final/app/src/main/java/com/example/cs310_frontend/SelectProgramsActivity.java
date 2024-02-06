package com.example.cs310_frontend;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import android.widget.ArrayAdapter;
import android.util.Log;


public class SelectProgramsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_programs);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            ((Window) window).addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.status_bar_color));
        }

        TextView txtName = findViewById(R.id.username);
        Button submit = findViewById(R.id.submit);

        Intent intent = getIntent();
        String receivedName = intent.getStringExtra("name");
        String receivedId = intent.getStringExtra("id");

        txtName.setText("Name: " + receivedName + "\nID: " + receivedId);

        List<String> items = new ArrayList<>();
        items.add("Minor Selection");
        items.add("ANALY");
        items.add("ART");
        items.add("CHEM");
        items.add("CONF");
        items.add("DECB");
        items.add("ENERG");
        items.add("ENTREP");
        items.add("FIN");
        items.add("GEN");
        items.add("MATH");
        items.add("PHIL");
        items.add("PHYS");
        items.add("PSIR");

        Spinner minorSpinner = findViewById(R.id.minorSpin);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        minorSpinner.setAdapter(adapter);
        minorSpinner.setSelection(items.indexOf("Minor Selection"));

        List<String> items2 = new ArrayList<>();
        items2.add("Major Selection");
        items2.add("CS");
        items2.add("BIO");
        items2.add("ECON");
        items2.add("IE");
        items2.add("MAN");
        items2.add("MAT");
        items2.add("ME");
        items2.add("PSIR");
        items2.add("PSY");
        items2.add("VA");
        items2.add("EE");

        Spinner majorSpinner = findViewById(R.id.majorSpin);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        majorSpinner.setAdapter(adapter2);
        majorSpinner.setSelection(items2.indexOf("Major Selection"));

        submit.setOnClickListener(view -> {
            String selectedMinor = (String) minorSpinner.getSelectedItem();
            String selectedMajor = (String) majorSpinner.getSelectedItem();

            Intent nextIntent = new Intent(SelectProgramsActivity.this, ThirdPageActivity.class);

            nextIntent.putExtra("selectedMajor", selectedMajor);
            nextIntent.putExtra("selectedMinor", selectedMinor);

            startActivity(nextIntent);
        });
    }
}
