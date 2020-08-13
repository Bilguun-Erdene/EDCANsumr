package com.example.edcansumr;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableArrayList;
import androidx.recyclerview.widget.RecyclerView;

public class BindinOptions {

    @BindingAdapter("memoItem")
    public static void bindMemoItema(RecyclerView recyclerView, ObservableArrayList<MemoModel> items){
        MemoAdapter adapter = (MemoAdapter) recyclerView.getAdapter();
        if(adapter != null)adapter.setItem(items);
    }

}
