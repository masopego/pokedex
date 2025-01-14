package com.penagomez.pokedex.ui.sign;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.penagomez.pokedex.R;

public class RegisterActivity extends AppCompatActivity {
    FirebaseAuth auth = FirebaseAuth.getInstance();

    int PASSWORD_MAX_LENTH = 6;

    Button registerButton;
    EditText emailEditText;
    EditText passwordEditText;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        registerButton = findViewById(R.id.registerButton);


        registerButton.setOnClickListener(v -> handleRegister(auth));
    }

    private void handleRegister(FirebaseAuth auth){
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();


        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this,  R.string.required_error, Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, R.string.invalid_format_error, Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < PASSWORD_MAX_LENTH) {
            Toast.makeText(this,  R.string.max_length_error, Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        Toast.makeText(this, getString(R.string.user_register_success) + " " +  user.getEmail(), Toast.LENGTH_SHORT).show();

                        goToLoginActivity();
                    } else {
                        Toast.makeText(this, getString(R.string.generic_error) + " " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    protected void goToLoginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);
    }
}
