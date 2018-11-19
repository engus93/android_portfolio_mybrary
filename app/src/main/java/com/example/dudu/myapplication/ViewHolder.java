package com.example.dudu.myapplication;

import android.support.v7.widget.RecyclerView;
import android.view.View;

class ViewHolder extends RecyclerView.ViewHolder {

    public final View mView;
    public ViewHolder(View itemView) {
        super(itemView);
        mView = itemView;

        // 레이아웃 객체화 findViewById
    }
}