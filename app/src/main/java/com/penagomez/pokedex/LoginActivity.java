package com.penagomez.pokedex;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.FirebaseApp;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


public class LoginActivity extends AppCompatActivity {

    private static final int REQ_ONE_TAP = 1001;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    private SignInClient oneTapClient;


    Button emailLoginButton;
    EditText emailEditText;
    EditText passwordEditText;
    Button registerLink;
    SignInButton signInButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

         emailLoginButton = findViewById(R.id.emailLoginButton);
         emailEditText = findViewById(R.id.emailEditText);
         passwordEditText = findViewById(R.id.passwordEditText);
         signInButton = findViewById(R.id.googleSignInButton);
         registerLink = findViewById(R.id.registerLink);


         emailLoginButton.setOnClickListener(v -> handleEmailLogin(auth));
        registerLink.setOnClickListener(v -> goToRegisterActivity());

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(this, gso);

        signInButton.setOnClickListener(v -> this.handleGoogleLogin(googleSignInClient));
    }

    private void handleGoogleLogin(GoogleSignInClient signInClient){
        oneTapClient = Identity.getSignInClient(this);

        BeginSignInRequest request = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        .setServerClientId(getString(R.string.default_web_client_id))
                        .setFilterByAuthorizedAccounts(false)
                        .build())
                .build();

        oneTapClient.beginSignIn(request)
                .addOnSuccessListener(this, new OnSuccessListener<BeginSignInResult>() {
                    @Override
                    public void onSuccess(BeginSignInResult beginSignInResult) {
                        try {
                            startIntentSenderForResult(
                                    beginSignInResult.getPendingIntent().getIntentSender(),
                                    1001,
                                    null, 0, 0, 0
                            );
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(LoginActivity.this, R.string.generic_error, Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                });
        ;

    }
    private void handleEmailLogin(FirebaseAuth auth) {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();


        if (!email.isEmpty() && !password.isEmpty()) {
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            Toast.makeText(this, "Welcome " + user.getEmail(), Toast.LENGTH_SHORT).show();

                            goToMainActivity();
                        } else {
                            Toast.makeText(this, R.string.generic_error + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, R.string.required_error, Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_ONE_TAP:
                FirebaseUser user = auth.getCurrentUser();

                try {
                    SignInCredential credential = Identity.getSignInClient(this).getSignInCredentialFromIntent(data);
                    String idToken = credential.getGoogleIdToken();

                    if (idToken != null) {
                        authenticateWithFirebase(idToken);
                        goToMainActivity();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
        }

    }

    private void authenticateWithFirebase(String idToken) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        AuthCredential firebaseCredential = GoogleAuthProvider.getCredential(idToken, null);

        firebaseAuth.signInWithCredential(firebaseCredential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        Log.d("FirebaseAuth", R.string.user_login_success + user.getDisplayName());
                    } else {
                        Log.e("FirebaseAuth", String.valueOf(R.string.generic_error), task.getException());
                    }
                });
    }

    @Override
    protected void onStart(){
        super.onStart();

        FirebaseApp.initializeApp(this);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user !=  null){
            System.out.println(user.getEmail());
            goToMainActivity();
        }
    }

    protected void goToMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);
    }

    protected void goToRegisterActivity(){
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);
    }
}
