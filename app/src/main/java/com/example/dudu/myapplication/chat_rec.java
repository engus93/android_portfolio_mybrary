package com.example.dudu.myapplication;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class chat_rec extends RecyclerView.ViewHolder  {

    TextView leftText,rightText;
    TextView chat_bot_nick, chat_bot_time, chat_bot_time_me;

    ConstraintLayout chat_bot_message;
    ConstraintLayout user_message;

    CircleImageView chat_bot_image;

    public chat_rec(View itemView){
        super(itemView);

        chat_bot_message = itemView.findViewById(R.id.chat_bot_message);
        user_message = itemView.findViewById(R.id.user_message);
        leftText = (TextView)itemView.findViewById(R.id.leftText);
        rightText = (TextView)itemView.findViewById(R.id.rightText);
        chat_bot_image = itemView.findViewById(R.id.chat_bot_image);
        chat_bot_nick = itemView.findViewById(R.id.chat_bot_nick);
        chat_bot_time = itemView.findViewById(R.id.chat_bot_time);
        chat_bot_time_me = itemView.findViewById(R.id.chat_bot_time_me);

    }

}
