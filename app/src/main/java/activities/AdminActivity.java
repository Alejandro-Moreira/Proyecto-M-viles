package activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

public class AdminActivity extends AppCompatActivity {

    private Button manageUsersButton;
    private Button viewStatisticsButton;
    private Button settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin); // Asegúrate de tener un layout llamado activity_admin.xml

        // Inicializar botones
        manageUsersButton = findViewById(R.id.manageUsersButton);
        viewStatisticsButton = findViewById(R.id.viewStatisticsButton);
        settingsButton = findViewById(R.id.settingsButton);

        // Configurar listeners para los botones
        manageUsersButton.setOnClickListener(v -> {
            // Aquí puedes iniciar una nueva actividad o realizar alguna acción relacionada con la gestión de usuarios
            Toast.makeText(this, "Manage Users clicked", Toast.LENGTH_SHORT).show();
        });

        viewStatisticsButton.setOnClickListener(v -> {
            // Aquí puedes iniciar una nueva actividad o realizar alguna acción relacionada con la visualización de estadísticas
            Toast.makeText(this, "View Statistics clicked", Toast.LENGTH_SHORT).show();
        });

        settingsButton.setOnClickListener(v -> {
            // Aquí puedes iniciar una nueva actividad o realizar alguna acción relacionada con la configuración
            Toast.makeText(this, "Settings clicked", Toast.LENGTH_SHORT).show();
        });
    }
}
