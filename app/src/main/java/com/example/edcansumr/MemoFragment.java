package com.example.edcansumr;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableArrayList;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.edcansumr.databinding.FragmentMeneBinding;
import com.example.edcansumr.databinding.FragmentMoreBinding;

public class MemoFragment extends Fragment {

    public static MemoFragment newInstance(){
        return new MemoFragment();
    }

    private ObservableArrayList<MemoModel> items = new ObservableArrayList<>();

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

        binding.fabMemo.setOnClickListener(v -> startActivity(new Intent(mContext, NewMemoActivity.class)));
        binding.setItems(items);

        MemoModel model1 = new MemoModel();
        model1.setEmail("");
        model1.setText("테스트 내용 1");
        model1.setTime("대충 작성된 시간");

        MemoModel model2 = new MemoModel();
        model2.setEmail("");
        model2.setText("테스트 내용 2");
        model2.setTime("대충 작성된 시간");

        items.add(model1);
        items.add(model2);

        return binding.getRoot();
    }
}