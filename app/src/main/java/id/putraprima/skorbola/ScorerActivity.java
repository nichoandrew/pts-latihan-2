package id.putraprima.skorbola;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ScorerActivity extends AppCompatActivity {
    EditText editSkorView;

    Button buttonSkorView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scorer);

        editSkorView = findViewById(R.id.editText);
        buttonSkorView = findViewById(R.id.button);

        buttonSkorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String scorerName = editSkorView.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("scorerName", scorerName);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
