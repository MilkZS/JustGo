package com.milkzs.android.wheretotravel.search;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.flexbox.FlexboxLayout;
import com.milkzs.android.wheretotravel.R;

import java.util.ArrayList;

public class SearchView extends LinearLayout implements View.OnClickListener {

    private String TAG = "SearchView";

    private Context mContext;

    private ImageView deleteImg;
    private EditText searchEd;
    private ListView showList;

    private FlexboxLayout historyFlexboxlayout;
    private FlexboxLayout hotFlexboxlayout;

    private ArrayAdapter<String> autoComplete;
    private ArrayList<TextView> historyList;
    private ArrayList<TextView> hotList;

    private SearchViewListener searchViewListener;

    private SharedPreferences sharedPreferences ;

    public SearchView(Context context) {
        super(context);
    }

    public SearchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        sharedPreferences = context.getSharedPreferences(
                SearchActivity.FILE_SHARE,Context.MODE_PRIVATE);
        LayoutInflater.from(context).inflate(R.layout.search_view, this);
        initView();
    }

    /**
     * init child view and set listener
     */
    private void initView() {
        Button backBt = findViewById(R.id.back_button);
        Button searchBt = findViewById(R.id.search_button);
        deleteImg = findViewById(R.id.img_delete);
        searchEd = findViewById(R.id.edit_view_search);
        showList = findViewById(R.id.show_list_search);
        historyFlexboxlayout = findViewById(R.id.flex_box_tag_search_history);
        hotFlexboxlayout = findViewById(R.id.flex_box_tag_search_hot);

        backBt.setOnClickListener(this);
        searchBt.setOnClickListener(this);
        deleteImg.setOnClickListener(this);

        showList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = showList.getAdapter().getItem(position).toString();
                searchEd.setText(text);
                searchEd.setSelection(text.length());
                showList.setVisibility(View.GONE);
                notifyToSearch(text);
            }
        });

        searchEd.addTextChangedListener(new EditChangeListener());
        // when user push enter key.start to search
        searchEd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    showList.setVisibility(View.GONE);
                    addHistoryTAGToSharePre(v.getText().toString());
                    notifyToSearch(searchEd.getText().toString());
                }
                return true;
            }
        });
    }

    /**
     * init flexbox layout and add data .
     */
    public void initFlexboxLayout() {
        for (final TextView textView : historyList) {
            addSearchTag(textView,1);
        }
        for (TextView textView : hotList) {
            addSearchTag(textView,0);
        }
    }

    /**
     * add text view for history and hot search tag.
     * @param textView  a TextView with tag data in it.
     * @param mode history tag or search tag.
     */
    private void addSearchTag(final TextView textView, final int mode){

        textView.setBackgroundResource(R.drawable.tag_search_background);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.setMargins(30, 15, 30, 15);
        textView.setLayoutParams(params);
        final String text = textView.getText().toString();
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEd.setText(text);
                if (mode == 0){
                    addHistoryTAGToSharePre(text);
                }
                notifyToSearch(text);
            }
        });
        if (mode == 0){
            hotFlexboxlayout.addView(textView);
        }else{
            historyFlexboxlayout.addView(textView);
        }
    }

    /**
     * add single data for history tag.
     * @param text tag content
     */
    private void addHistoryTAGToSharePre(String text){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String sHistory = sharedPreferences.getString(
                SearchActivity.FLAG_SEARCH_HISTORY,"");
        sHistory = sHistory + "=" + text;
        editor.putString(SearchActivity.FLAG_SEARCH_HISTORY,sHistory);
        editor.apply();
        editor.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_button: {
                ((Activity) mContext).finish();
            }
            break;
            case R.id.search_button: {
                Log.d(TAG, "click search button");
                notifyToSearch(searchEd.getText().toString());
            }
            break;
            case R.id.img_delete: {
                deleteImg.setVisibility(View.GONE);
                searchEd.setText("");
            }
            break;
        }
    }

    public void notifyToSearch(String sText) {
        searchViewListener.startToSearch(sText);
    }

    /**
     * interface method to add listener by user
     *
     * @param searchViewListener SearchViewListener
     */
    public void setClickListener(SearchViewListener searchViewListener) {
        this.searchViewListener = searchViewListener;
    }

    /**
     * interface method to add adapter of listview by user
     *
     * @param autoComplete ArrayAdapter<String>
     */
    public void setAutoCompleteAdapter(ArrayAdapter<String> autoComplete) {
        this.autoComplete = autoComplete;
    }

    public void setHistoryList(ArrayList<TextView> historyList) {
        this.historyList = historyList;
    }

    public void setHotList(ArrayList<TextView> hotList) {
        this.hotList = hotList;
    }

    public interface SearchViewListener {
        /**
         * start to search by text input.This is the end search word.
         *
         * @param text search text by user
         */
        void startToSearch(String text);

        /**
         * For auto complete editView , when user input one word,we search at time.
         *
         * @param text words input by user
         */
        void refreshSearchEdit(String text);


    }

    private class EditChangeListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!"".equals(s.toString())) {
                deleteImg.setVisibility(VISIBLE);
                if (autoComplete != null) {
                    Log.d(TAG, "auto to complete data");
                    showList.setAdapter(autoComplete);
                } else {
                    Toast.makeText(getContext(), "adapter is null", Toast.LENGTH_SHORT).show();
                }
                showList.setVisibility(VISIBLE);
                if (searchViewListener != null) {
                    searchViewListener.refreshSearchEdit(s.toString());
                }

            } else {
                deleteImg.setVisibility(View.GONE);
                showList.setVisibility(View.GONE);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
