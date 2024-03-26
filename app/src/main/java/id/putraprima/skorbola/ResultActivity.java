package id.putraprima.skorbola;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class ResultActivity extends AppCompatActivity {

    TextView winningTextView, pemainTextView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        winningTextView = findViewById(R.id.textView3);
        pemainTextView = findViewById(R.id.textView4);



        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String winning = extras.getString("winner");
            ArrayList<String> winningScorers = getIntent().getStringArrayListExtra("winningScorers");
            Collections.sort(winningScorers);

            winningTextView.setText(winning);
            pemainTextView.setText("Pencetak Gol :\n" + TextUtils.join("\n", winningScorers));
        }

    }
}
