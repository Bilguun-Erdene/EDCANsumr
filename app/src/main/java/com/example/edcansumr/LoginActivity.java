package com.example.edcansumr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.widget.Toast;

import com.example.edcansumr.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

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
    }
    private void login(String email, String pw){
        Toast.makeText(this, email+" "+pw, Toast.LENGTH_SHORT).show();
    }
}
