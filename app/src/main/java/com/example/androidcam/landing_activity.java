package com.example.androidcam;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.androidcam.databinding.ActivityLandingBinding;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;


public class landing_activity extends AppCompatActivity {



    Switch switchCNN,switchFFNN;
    EditText key;
    public boolean switchCNNvalue = false, switchFFNNvalue = false;
    String keyText;
    public String activeAlgo;
    private AppBarConfiguration appBarConfiguration;
    private ActivityLandingBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     binding = ActivityLandingBinding.inflate(getLayoutInflater());
     setContentView(binding.getRoot());



        // on below line we are initializing our variables.
        switchCNN = findViewById(R.id.switchCNN);
        switchFFNN = findViewById(R.id.switchFFNN);

        // on below line we are adding check change listener for our switch.
        switchCNN.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    switchCNNvalue = true;
                    switchFFNNvalue = false;
                    switchFFNN.setChecked(false);
                    activeAlgo = "CNN";
                } else {
                    switchCNNvalue = false;
                }
            }
        });

        switchFFNN.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    switchCNNvalue = false;
                    switchFFNNvalue = true;
                    switchCNN.setChecked(false);
                    activeAlgo = "FFNN";
                } else {
                    switchFFNNvalue = false;

                }
            }
        });




        final Button button = findViewById(R.id.buttonenter);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if ((switchCNNvalue) || (switchFFNNvalue) ){
                    key = findViewById(R.id.txtbxServerKey);
                    keyText = key.getText().toString();
                    if (keyText.matches("")) {
                        Context context = getApplicationContext();
                        CharSequence text = "Empty Key!";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                    else {

                        // proceed
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        intent.putExtra("activeAlgo", activeAlgo);
                        intent.putExtra("keyText", keyText);
                        startActivity(intent);

                        Context context = getApplicationContext();
                        CharSequence text = "OK!" +activeAlgo;
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                }
                else   {
                    //display error; indicate unchecked switch
                    Context context = getApplicationContext();
                    CharSequence text = "Please select an Algo!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }



            }
        });
    }



    }
