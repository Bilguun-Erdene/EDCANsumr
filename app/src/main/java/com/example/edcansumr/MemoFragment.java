package com.example.edcansumr;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableArrayList;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.edcansumr.databinding.FragmentMeneBinding;
import com.example.edcansumr.databinding.FragmentMoreBinding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Locale;

public class MemoFragment extends Fragment {

    public static MemoFragment newInstance(){
        return new MemoFragment();
    }

    private ObservableArrayList<MemoModel> items = new ObservableArrayList<>();
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    private Context mContext;
    private FragmentMeneBinding binding;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mene, container, false);

        binding.recyclerMemo.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        MemoAdapter adapter = new MemoAdapter();
        binding.recyclerMemo.setAdapter(adapter);

        adapter.setOnItemClickListener(((view, item) -> {
            Intent intent = new Intent(mContext, NewMemoActivity.class);
            intent.putExtra("edit_text" , item.getText());
            intent.putExtra("memo_text" , item.getText());
            startActivity(intent);
        }));
        adapter.setOnItemLongClickListener(((view, item) -> {

            return true;
        }));

        binding.fabMemo.setOnClickListener(v -> startActivity(new Intent(mContext, NewMemoActivity.class)));
        binding.setItems(items);

        getMemos();


        return binding.getRoot();
    }

    @Override
    public void onResume(){
        super.onResume();
        getMemos();
    }

    private void getMemos(){
        items.clear();
        firebaseFirestore
                .collection("memo")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot d : queryDocumentSnapshots){
                        items.add(d.toObject(MemoModel.class));
                    }

                    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd hh:mm aa", Locale.ENGLISH);
                    Collections.sort(items, (memoModel, t1) -> {
                        try {
                            return format.parse(t1.getTime()).compareTo(format.parse(memoModel.getTime()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                            return 0;
                        }
                    });

                })
                .addOnFailureListener(e -> Toast.makeText(mContext, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show());
    }
}