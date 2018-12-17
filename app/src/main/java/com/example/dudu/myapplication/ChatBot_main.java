package com.example.dudu.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mozilla.javascript.tools.jsc.Main;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

    ArrayList<Search_01_ArrayList> chat_bot_search = new ArrayList<>();

    ChatBot_Horizental_Adapter chatBot_horizental_adapter = null;

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
//        linearLayoutManager.setStackFromEnd(true);
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

                    ChatBot_Message chatBotMessage = new ChatBot_Message(message, "user", set_date);
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
            @SuppressLint("StaticFieldLeak")
            @Override
            protected void populateViewHolder(final chat_rec viewHolder, ChatBot_Message model, int position) {

                viewHolder.chat_bot_ReRe.setVisibility(View.GONE);

                if (model.getMsgUser().equals("user")) {

                    viewHolder.rightText.setText(model.getMsgText());
                    viewHolder.chat_bot_time_me.setText(model.getSend_time());

                    viewHolder.user_message.setVisibility(View.VISIBLE);
                    viewHolder.chat_bot_message.setVisibility(View.GONE);

                } else {

                    //NextLine 줄 처리
                    if (model.getMsgText().contains("\\n")) {
                        String temp = model.getMsgText();
                        model.setMsgText(temp.replace("\\n", "\n"));
                    }

                    viewHolder.leftText.setText(model.getMsgText());
                    viewHolder.chat_bot_time.setText(model.getSend_time());

                    viewHolder.user_message.setVisibility(View.GONE);
                    viewHolder.chat_bot_message.setVisibility(View.VISIBLE);

                    if (model.getMsgText().contains("베스트셀러") || model.getMsgText().contains("신간도서") || model.getMsgText().contains("추천도서")
                            || model.getMsgText().contains("책 리스트입니다.")) {

                        viewHolder.chat_bot_ReRe.setVisibility(View.VISIBLE);

                        viewHolder.chat_bot_ReRe.setHasFixedSize(true);
                        viewHolder.chat_bot_ReRe.getItemAnimator();
                        LinearLayoutManager mLayoutManager = new LinearLayoutManager(ChatBot_main.this);
                        ((LinearLayoutManager) mLayoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
                        viewHolder.chat_bot_ReRe.setLayoutManager(mLayoutManager);

                        if (model.getMsgText().contains("베스트셀러")) {
                            chatBot_horizental_adapter = new ChatBot_Horizental_Adapter("베스트셀러");
                            viewHolder.chat_bot_ReRe.setAdapter(chatBot_horizental_adapter);
                        } else if (model.getMsgText().contains("신간도서")) {
                            chatBot_horizental_adapter = new ChatBot_Horizental_Adapter("신간도서");
                            viewHolder.chat_bot_ReRe.setAdapter(chatBot_horizental_adapter);
                        } else if (model.getMsgText().contains("추천도서")) {
                            chatBot_horizental_adapter = new ChatBot_Horizental_Adapter("추천도서");
                            viewHolder.chat_bot_ReRe.setAdapter(chatBot_horizental_adapter);
                        } else if (model.getMsgText().contains("책 리스트입니다.")) {

                            String temp = null;

                            temp = model.getMsgText().replace("의 책 리스트입니다.", "");

                            final String finalTemp = temp;

                            new AsyncTask<Void, Void, Void>() {

                                String search_word = finalTemp;
                                String search_url = "http://book.interpark.com/api/search.api?key=9A0ACD60A50795084682869204DE13D2A6A3FAB4767E8869BD4C8340C8F61FAC&output=json&sort=salesPoint&maxResults=30&queryType=author&query=";

                                @Override
                                protected Void doInBackground(Void... params) {

                                    URL url = null;
                                    HttpURLConnection urlConnection = null;
                                    BufferedInputStream buf = null;

                                    try {

                                        //[URL 지정과 접속]

                                        //웹서버 URL 지정
                                        url = new URL(search_url + search_word);

                                        //URL 접속
                                        urlConnection = (HttpURLConnection) url.openConnection();

                                        //[웹문서 소스를 버퍼에 저장]
                                        //데이터를 버퍼에 기록

                                        BufferedReader bufreader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

                                        String line = null;
                                        String page = "";

                                        //버퍼의 웹문서 소스를 줄단위로 읽어(line), Page에 저장함
                                        while ((line = bufreader.readLine()) != null) {
                                            page += line;
                                        }

                                        //읽어들인 JSON포맷의 데이터를 JSON객체로 변환
                                        JSONObject json = new JSONObject(page);

                                        //item 에 해당하는 배열을 할당
                                        JSONArray jArr = json.getJSONArray("item");

                                        chat_bot_search.clear();

                                        //배열의 크기만큼 반복하면서, name과 address의 값을 추출함
                                        for (int i = 0; i < 10; i++) {

                                            //i번째 배열 할당
                                            json = jArr.getJSONObject(i);

                                            //책 데이터 추출
                                            String title = json.getString("title");
                                            String coverLargeUrl = json.getString("coverLargeUrl");
                                            String author = json.getString("author");

                                            String mobileLink = json.getString("mobileLink");
                                            String book_main = json.getString("description");
                                            String price = json.getString("priceStandard");
                                            String book_publisher = json.getString("publisher");
                                            String date = json.getString("pubDate");
                                            double star = json.getDouble("customerReviewRank")/2.0;

                                            chat_bot_search.add(new Search_01_ArrayList(coverLargeUrl, title, author, price + "원", book_publisher, date , star, book_main, mobileLink));

                                        }

                                    } catch (IOException e) {

                                        e.printStackTrace();

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    return null;

                                }

                                @Override
                                protected void onPostExecute(Void aVoid) {
                                    super.onPostExecute(aVoid);

                                    chatBot_horizental_adapter = new ChatBot_Horizental_Adapter("검색");
                                    viewHolder.chat_bot_ReRe.setAdapter(chatBot_horizental_adapter);

                                }

                            }.execute();

                        }

                    }

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

    class ChatBot_Horizental_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        List<Search_01_ArrayList> books_info = new ArrayList<>();

        //글라이드 오류 방지
        public RequestManager mGlideRequestManager;

        public ChatBot_Horizental_Adapter(String subject) {

            switch (subject) {

                case "베스트셀러": {

                    books_info.addAll(App.search_best_book_info_ArrayList);

                    break;
                }

                case "추천도서": {

                    books_info.addAll(App.chat_bot_recommendbook);

                    break;
                }

                case "신간도서": {

                    books_info.addAll(App.chat_bot_newbook);

                    break;
                }

                case "검색": {

                    books_info.addAll(chat_bot_search);

                    break;
                }
            }

        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {

            View v1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_bot_re, parent, false);

            return new chat_bot_re(v1);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
            chat_bot_re chat_bot_re = ((chat_bot_re) holder);

            //글라이드 오류 방지
            mGlideRequestManager = Glide.with(ChatBot_main.this);

            mGlideRequestManager.load(books_info.get(position).drawableId).fitCenter().into(chat_bot_re.chat_bot_book_imge_B);
            chat_bot_re.chat_bot_book_name.setText(books_info.get(position).name);
            chat_bot_re.chat_bot_book_author.setText(books_info.get(position).author);
            chat_bot_re.chat_bot_book_date.setText("출간일 : " + books_info.get(position).date);

            chat_bot_re.chat_bot_cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    App.chat_bot_search_book = books_info.get(position);
                    Intent intent = new Intent(ChatBot_main.this, ChatBot_Search.class);
                    startActivity(intent);

                }
            });

        }

        @Override
        public int getItemCount() {
            return books_info.size();
        }

        private class chat_bot_re extends RecyclerView.ViewHolder {

            TextView chat_bot_book_name;
            TextView chat_bot_book_author;
            TextView chat_bot_book_date;
            ImageView chat_bot_book_imge_B;
            CardView chat_bot_cardview;

            chat_bot_re(View view) {
                super(view);
                chat_bot_book_imge_B = view.findViewById(R.id.chat_bot_book_imge_B);
                chat_bot_book_name = view.findViewById(R.id.chat_bot_book_name);
                chat_bot_book_author = view.findViewById(R.id.chat_bot_book_author);
                chat_bot_book_date = view.findViewById(R.id.chat_bot_book_date);
                chat_bot_cardview = view.findViewById(R.id.chat_bot_cardview);

            }
        }

    }

}


