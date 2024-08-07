package activities;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.example.maprealtime.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText fullNameEditText, emailEditText, passwordEditText;
    private Button registerButton, loginButton;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Inicializar Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // Inicializar Database Reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Usuarios");

        // Inicializar vistas
        fullNameEditText = findViewById(R.id.full_name);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        registerButton = findViewById(R.id.register_button);
        loginButton = findViewById(R.id.login_button);
        progressBar = findViewById(R.id.progressBar);

        // Botón de registro
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = fullNameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (TextUtils.isEmpty(fullName)) {
                    fullNameEditText.setError("Se requiere nombre completo.");
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    emailEditText.setError("Correo electronico es requerido.");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    passwordEditText.setError("Contraseña es requerida.");
                    return;
                }

                if (password.length() < 6) {
                    passwordEditText.setError("La contraseña debe tener al menos 6 caracteres.");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                // Registrar usuario
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Envia un correo electronico de verificación
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    if (user != null) {
                                        user.sendEmailVerification()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            // Guarda los detalles del usuario en Firebase Database
                                                            User userDetails = new User(fullName, email);
                                                            databaseReference.child(user.getUid()).setValue(userDetails)
                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            if (task.isSuccessful()) {
                                                                                Toast.makeText(RegisterActivity.this, "Registro exitoso. Por favor revise su correo electrónico para verificación.", Toast.LENGTH_LONG).show();
                                                                                mAuth.signOut();
                                                                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                                                                finish();
                                                                            } else {
                                                                                Toast.makeText(RegisterActivity.this, "No se pudieron guardar los detalles del usuario: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                                                            }
                                                                        }
                                                                    });
                                                        } else {
                                                            Toast.makeText(RegisterActivity.this, "No se pudo enviar el correo electrónico de verificación: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                                        }
                                                        progressBar.setVisibility(View.GONE);
                                                    }
                                                });
                                    }
                                } else {
                                    Toast.makeText(RegisterActivity.this, "Registro fallido: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        });
            }
        });

        // Botón de inicio de sesión
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
    }

    // Guardar los detalles del  usuario
    public static class User {
        public String fullName;
        public String email;

        public User() {
        }

        public User(String fullName, String email) {
            this.fullName = fullName;
            this.email = email;
        }
    }
}