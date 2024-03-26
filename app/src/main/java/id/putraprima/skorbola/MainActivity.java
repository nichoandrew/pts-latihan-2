package id.putraprima.skorbola;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    EditText homeTeamInput, awayTeamInput;
    ImageView homeTeamLogo, awayTeamLogo;
    Button nextButton;

    private static final int HOME_LOGO_REQUEST_CODE = 1;
    private static final int AWAY_LOGO_REQUEST_CODE = 2;
    private static final int PICK_IMAGE_REQUEST = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TODO

        homeTeamInput = findViewById(R.id.home_team);
        awayTeamInput = findViewById(R.id.away_team);
        homeTeamLogo = findViewById(R.id.home_logo);
        awayTeamLogo = findViewById(R.id.away_logo);
        nextButton = findViewById(R.id.btn_team);



        // Mengatur listener untuk nextButton
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Validasi input Home Team dan Away Team
                String homeTeam = homeTeamInput.getText().toString().trim();
                String awayTeam = awayTeamInput.getText().toString().trim();

                if (homeTeam.isEmpty() || awayTeam.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Mohon lengkapi nama tim", Toast.LENGTH_SHORT).show();
                } else {
                    // Pindah ke MatchActivity
                    Intent intent = new Intent(MainActivity.this, MatchActivity.class);
                    intent.putExtra("homeTeam", homeTeam);
                    intent.putExtra("awayTeam", awayTeam);

                    // Mengambil URI dari gambar logo Home Team dan Away Team (jika sudah diatur)
                    if (homeTeamLogo.getDrawable() != null) {
                        // Mengambil URI dari ImageView homeTeamLogo
                        Uri homeTeamUri = getImageUri(MainActivity.this, homeTeamLogo.getDrawable());
                        intent.putExtra("homeTeamLogoUri", homeTeamUri.toString());
                    }

                    if (awayTeamLogo.getDrawable() != null) {
                        // Mengambil URI dari ImageView awayTeamLogo
                        Uri awayTeamUri = getImageUri(MainActivity.this, awayTeamLogo.getDrawable());
                        intent.putExtra("awayTeamLogoUri", awayTeamUri.toString());
                    }


                    startActivity(intent);
                }
            }
        });

        // Menambahkan fungsi untuk mengganti logo Home Team
        homeTeamLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(intent, HOME_LOGO_REQUEST_CODE);
            }
        });

        // Menambahkan fungsi untuk mengganti logo Away Team
        awayTeamLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, AWAY_LOGO_REQUEST_CODE);

            }
        });

        //Fitur Main Activity
        //1. Validasi Input Home Team
        //2. Validasi Input Away Team
        //3. Ganti Logo Home Team
        //4. Ganti Logo Away Team
        //5. Next Button Pindah Ke MatchActivity
    }

    // Method untuk mendapatkan URI dari Drawable
    private Uri getImageUri(Context context, Drawable drawable) {
        Bitmap bitmap;
        if (drawable instanceof BitmapDrawable) {
            bitmap = ((BitmapDrawable) drawable).getBitmap();
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
        }

        // Mengonversi Bitmap menjadi Uri
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Title", null);
        return Uri.parse(path);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            if (requestCode == HOME_LOGO_REQUEST_CODE) {
                homeTeamLogo.setImageURI(uri);
                Toast.makeText(MainActivity.this, "Logo Home Team diganti", Toast.LENGTH_SHORT).show();
            } else if (requestCode == AWAY_LOGO_REQUEST_CODE) {
                awayTeamLogo.setImageURI(uri);
                Toast.makeText(MainActivity.this, "Logo Away Team diganti", Toast.LENGTH_SHORT).show();
            }

        }
    }

}
