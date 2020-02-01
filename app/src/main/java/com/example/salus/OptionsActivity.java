package com.example.salus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import util.UserApi;

public class OptionsActivity extends AppCompatActivity {

    //XML elements
    private AutoCompleteTextView emailLogin;
    private EditText password_editText;
    private Button loginButton;
    private Button signUpButton;
    private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        emailLogin = findViewById(R.id.email);
        password_editText = findViewById(R.id.password);
        loginButton = findViewById(R.id.email_login_button);
        signUpButton = findViewById(R.id.sign_up_button);
        progressBar = findViewById(R.id.login_progress);

        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        };


    }

    public void login(View view) {
        loginEmailPasswordUser(emailLogin.getText().toString(),password_editText.getText().toString());
    }

    private void loginEmailPasswordUser(String email, String password) {
        progressBar.setVisibility(View.VISIBLE);

        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    assert user != null;
                    String currentUserId = user.getUid();
                    collectionReference.whereEqualTo("userId", currentUserId).addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                            if(e != null) {

                            }
                            assert queryDocumentSnapshots != null;

                            if(!queryDocumentSnapshots.isEmpty()) {
                                progressBar.setVisibility(View.INVISIBLE);
                                for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                                    UserApi userApi = UserApi.getInstance();
                                    userApi.setUsername(snapshot.getString("username"));
                                    userApi.setUserId(snapshot.getString("userId"));

                                    startActivity(new Intent(OptionsActivity.this, MapsActivity.class));
                                }
                            }
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });
        }
        else{
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(OptionsActivity.this,
                    "Please enter email and password",
                    Toast.LENGTH_LONG)
                    .show();
        }
    }

    public void signUp(View view) {
        startActivity(new Intent(OptionsActivity.this,SignUpActivity.class));
    }
}
