package activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import com.example.maprealtime.R;

public class AdminActivity extends AppCompatActivity {

    private Button manageUsersButton;
    private Button viewStatisticsButton;
    private Button settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        // Inicializar botones
        manageUsersButton = findViewById(R.id.manageUsersButton);
        viewStatisticsButton = findViewById(R.id.viewStatisticsButton);
        settingsButton = findViewById(R.id.settingsButton);

        // Configurar listeners para los botones
        manageUsersButton.setOnClickListener(v -> {
            Toast.makeText(this, "Administrar usuarios", Toast.LENGTH_SHORT).show();
        });

        viewStatisticsButton.setOnClickListener(v -> {
            Toast.makeText(this, "Ver estadísticas", Toast.LENGTH_SHORT).show();
        });

        settingsButton.setOnClickListener(v -> {
            Toast.makeText(this, "Configuración", Toast.LENGTH_SHORT).show();
        });
    }
}
