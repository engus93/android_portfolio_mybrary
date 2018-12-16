package com.example.dudu.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import ai.api.AIDataService;
import ai.api.AIListener;
import ai.api.AIServiceException;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import ai.api.model.ResponseMessage;
import ai.api.model.Result;

public class ChatBot_main extends AppCompatActivity implements AIListener {

    FirebaseRecyclerAdapter<ChatBot_Message, chat_rec> adapter;
    RecyclerView recyclerView;
    EditText chat_bot_ET;
    RelativeLayout chat_bot_send_B;
    DatabaseReference mDataBaseReference;
    Boolean flagFab = true;
    TextView chat_bot_nick;
    ImageView chat_bot_back;

    private AIService aiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        recyclerView = findViewById(R.id.chatbot_recycler);
        chat_bot_ET = findViewById(R.id.chat_bot_ET);
        chat_bot_send_B = findViewById(R.id.chat_bot_send_B);
        chat_bot_nick = findViewById(R.id.chat_bot_nick);
        chat_bot_back = findViewById(R.id.chat_bot_back);

        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChatBot_main.this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        mDataBaseReference = FirebaseDatabase.getInstance().getReference();
        mDataBaseReference.keepSynced(true);

        final AIConfiguration config = new AIConfiguration("e614e1f87d4b4fe2a30b3d1a41db6bf8 ",
                AIConfiguration.SupportedLanguages.Korean,
                AIConfiguration.RecognitionEngine.System);

        aiService = AIService.getService(this, config);
        aiService.setListener(this);

        final AIDataService aiDataService = new AIDataService(config);

        final AIRequest aiRequest = new AIRequest();

        chat_bot_send_B.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View view) {
                aiService.startListening();

                String message = chat_bot_ET.getText().toString().trim();

                if (!message.equals("")) {

                    //현재 시간 등록
                    Date date = new Date();
                    final SimpleDateFormat dateFormat = new SimpleDateFormat("HH시 mm분", java.util.Locale.getDefault());
                    String set_date = dateFormat.format(date);

                    ChatBot_Message chatBotMessage = new ChatBot_Message(message, "user",set_date);
                    mDataBaseReference.child("ChatBot").child(App.user_UID_get()).push().setValue(chatBotMessage);

                    aiRequest.setQuery(message);
                    new AsyncTask<AIRequest, Void, AIResponse>() {

                        @Override
                        protected AIResponse doInBackground(AIRequest... aiRequests) {
                            final AIRequest request = aiRequests[0];
                            try {
                                Log.d("DialogFlowAsyncTask", "Request" + aiRequest.toString());
                                final AIResponse response = aiDataService.request(aiRequest);
                                return response;
                            } catch (AIServiceException e) {
                            }
                            return null;
                        }

                        protected void onPostExecute(AIResponse response) {
                            if (response != null) {
                                Result result = response.getResult();
//                                String reply = result.getFulfillment().getSpeech();
                                String reply = result.getFulfillment().getSpeech();

                                //현재 시간 등록
                                Date date = new Date();
                                final SimpleDateFormat dateFormat = new SimpleDateFormat("HH시 mm분", Locale.getDefault());
                                String set_date = dateFormat.format(date);

                                if (reply.length() != 0) {
                                    ChatBot_Message chatBotMessage = new ChatBot_Message(reply, "bot", set_date);
                                    mDataBaseReference.child("ChatBot").child(App.user_UID_get()).push().setValue(chatBotMessage);
                                } else {
                                    int MessageCount = result.getFulfillment().getMessages().size();
                                    if (MessageCount > 1) {
                                        for (int i = 0; i < MessageCount; i++) {
                                            ResponseMessage.ResponseSpeech responseMessage = (ResponseMessage.ResponseSpeech) result.getFulfillment().getMessages().get(i);
                                            Log.e("chatBot", "" + responseMessage.getSpeech());
                                            String temp = String.valueOf(responseMessage.getSpeech());
                                            temp = temp.replace("[", "");
                                            temp = temp.replace("]", "");
                                            ChatBot_Message chatBotMessage = new ChatBot_Message(temp, "bot", set_date);
                                            mDataBaseReference.child("ChatBot").child(App.user_UID_get()).push().setValue(chatBotMessage);
                                        }
                                    }
                                }
                            } else {
                            }
                        }
                    }.execute(aiRequest);

                } else {
                    //   aiService.startListening();
                }

                chat_bot_ET.setText("");

            }
        });


        //send 버튼 변하기
        chat_bot_ET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ImageView fab_img = (ImageView) findViewById(R.id.fab_img);
                Bitmap img = BitmapFactory.decodeResource(getResources(), R.drawable.ic_send_white_24dp);
                Bitmap img1 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_mic_white_24dp);


                if (s.toString().trim().length() != 0 && flagFab) {
                    ImageViewAnimatedChange(ChatBot_main.this, fab_img, img);
                    flagFab = false;

                } else if (s.toString().trim().length() == 0) {
                    ImageViewAnimatedChange(ChatBot_main.this, fab_img, img1);
                    flagFab = true;

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        adapter = new FirebaseRecyclerAdapter<ChatBot_Message, chat_rec>(ChatBot_Message.class, R.layout.chatbot_msglist, chat_rec.class, mDataBaseReference.child("ChatBot").child(App.user_UID_get())) {
            @Override
            protected void populateViewHolder(chat_rec viewHolder, ChatBot_Message model, int position) {

                if (model.getMsgUser().equals("user")) {

                    viewHolder.rightText.setText(model.getMsgText());
                    viewHolder.chat_bot_time_me.setText(model.getSend_time());

                    viewHolder.user_message.setVisibility(View.VISIBLE);
                    viewHolder.chat_bot_message.setVisibility(View.GONE);

                } else {

                    if(model.getMsgText().contains("\\n")){
                        String temp = model.getMsgText();
                        model.setMsgText(temp.replace("\\n", "\n"));
                    }

                    viewHolder.leftText.setText(model.getMsgText());
                    viewHolder.chat_bot_time.setText(model.getSend_time());

                    viewHolder.user_message.setVisibility(View.GONE);
                    viewHolder.chat_bot_message.setVisibility(View.VISIBLE);
                }
            }

        };

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);

                int msgCount = adapter.getItemCount();
                int lastVisiblePosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();

                if (lastVisiblePosition == -1 || (positionStart >= (msgCount - 1) && lastVisiblePosition == (positionStart - 1))) {
                    recyclerView.scrollToPosition(positionStart);

                }

            }
        });

        recyclerView.setAdapter(adapter);
    }

    public void ImageViewAnimatedChange(Context c, final ImageView v, final Bitmap new_image) {
        final Animation anim_out = AnimationUtils.loadAnimation(c, R.anim.zoom_out);
        final Animation anim_in = AnimationUtils.loadAnimation(c, R.anim.zoom_in);
        anim_out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                v.setImageBitmap(new_image);
                anim_in.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                    }
                });
                v.startAnimation(anim_in);
            }
        });
        v.startAnimation(anim_out);
    }

    @Override
    public void onResult(ai.api.model.AIResponse response) {

        Log.d("DialogFlow", "onResult" + response.toString());

        Result result = response.getResult();

        //현재 시간 등록
        Date date = new Date();
        final SimpleDateFormat dateFormat = new SimpleDateFormat("HH시 mm분", java.util.Locale.getDefault());
        String set_date = dateFormat.format(date);

        String message = result.getResolvedQuery();
        ChatBot_Message chatBotMessage0 = new ChatBot_Message(message, "user", set_date);
        mDataBaseReference.child("ChatBot").child(App.user_UID_get()).push().setValue(chatBotMessage0);

        String reply = result.getFulfillment().getSpeech();
        ChatBot_Message chatBotMessage = new ChatBot_Message(reply, "bot", set_date);
        mDataBaseReference.child("ChatBot").child(App.user_UID_get()).push().setValue(chatBotMessage);

    }

    @Override
    public void onError(ai.api.model.AIError error) {

    }

    @Override
    public void onAudioLevel(float level) {

    }

    @Override
    public void onListeningStarted() {

    }

    @Override
    public void onListeningCanceled() {

    }

    @Override
    public void onListeningFinished() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

}

