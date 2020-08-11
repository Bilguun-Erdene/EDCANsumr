package com.example.edcansumr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.edcansumr.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setEmail("");
        binding.setPw("");

        binding.btnLoginSignin.setOnClickListener(view -> {
            login(binding.getEmail(), binding.getPw());
        });
        binding.btnLoginSignup.setOnClickListener(view -> {
            startActivity(new Intent (LoginActivity.this, RegisterActivity.class));
        });
    }
    private void login(String email, String pw){
        firebaseFirestore
            .collection("users")
                .document()
                .get()
                .addOnSuccessListener(runnable -> {
                    firebaseAuth
                            .signInWithEmailAndPassword(email, pw)
                            .addOnSuccessListener(runnable1 -> {
                                //goto main
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            })
                            .addOnFailureListener(e -> Toast.makeText(this, e.getLocalizedMessage(),Toast.LENGTH_SHORT));
                })
                .addOnFailureListener(e -> Toast.makeText(this, e.getLocalizedMessage(),Toast.LENGTH_SHORT));

    }
}
