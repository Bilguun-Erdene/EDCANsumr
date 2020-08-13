package com.example.edcansumr;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.edcansumr.databinding.FragmentMeneBinding;
import com.example.edcansumr.databinding.FragmentMoreBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MoreFragment extends Fragment {
    public static MoreFragment newInstance() {
        return new MoreFragment();
    }

    private Context mContext;
    private FragmentMoreBinding binding;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_more, container, false);

        binding.setUser(UserCache.getUser(mContext));

        binding.btnMoreLogout.setOnClickListener(v -> {

            logout();
        });
        binding.btnMorePw.setOnClickListener(v -> {
            firebaseAuth.sendPasswordResetEmail(UserCache.getUser(mContext).getEmail());
            Toast.makeText(mContext, "sent email", Toast.LENGTH_SHORT).show();
            logout();
        });
        binding.btnMoreDelete.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("Delete account");
            builder.setMessage("Are you really want to delete this account?");
            builder.setPositiveButton("Delete", (dialogInterface, i) -> {
                firebaseAuth.getCurrentUser().delete().addOnSuccessListener(runnable -> {
                    firebaseFirestore
                            .collection("users")
                            .document(UserCache.getUser(mContext).getEmail())
                            .delete()
                            .addOnSuccessListener(runnable1 -> {
                                Toast.makeText(mContext, "Deleted", Toast.LENGTH_SHORT).show();
                                logout();
                            })
                            .addOnFailureListener(e -> Toast.makeText(mContext, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show());
                    logout();
                }).addOnFailureListener(e -> Toast.makeText(mContext, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show());
            });
            builder.setNegativeButton("Cancel", (dialogInterface, i) -> {
                builder.show();
            });
        });
        return binding.getRoot();
    }

    private void logout() {
        UserCache.clear(mContext);
        firebaseAuth.signOut();
        startActivity(new Intent(mContext, LoginActivity.class));
        getActivity().finish();
    }
}