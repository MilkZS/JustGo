package com.milkzs.android.wheretotravel.search;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
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

import com.milkzs.android.wheretotravel.R;

public class SearchView extends LinearLayout implements View.OnClickListener{

    private String TAG = "SearchView";

    private Context mContext;

    private Button backBt;
    private Button searchBt;
    private ImageView deleteImg;
    private EditText searchEd;
    private ListView showList;

    private ArrayAdapter<String> autoComplete;

    private SearchViewListener searchViewListener;

    public SearchView(Context context) {
        super(context);
    }

    public SearchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.search_view,this);
        initView();
    }

    /**
     * init child view and set listener
     */
    private void initView(){
        backBt = findViewById(R.id.back_button);
        searchBt = findViewById(R.id.search_button);
        deleteImg = findViewById(R.id.img_delete);
        searchEd = findViewById(R.id.edit_view_search);
        showList = findViewById(R.id.show_list_search);

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
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    showList.setVisibility(View.GONE);
                    notifyToSearch(searchEd.getText().toString());
                }
                return true;
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_button:{
                ((Activity)mContext).finish();
            }break;
            case R.id.search_button:{
                notifyToSearch(searchEd.getText().toString());
            }break;
            case R.id.img_delete:{
                deleteImg.setVisibility(View.GONE);
                searchEd.setText("");
            }break;
        }
    }

    public void notifyToSearch(String sText){
        searchViewListener.startToSearch(sText);
    }

    /**
     * interface method to add listener by user
     * @param searchViewListener SearchViewListener
     */
    public void setClickListener(SearchViewListener searchViewListener){
        this.searchViewListener = searchViewListener;
    }

    /**
     * interface method to add adapter of listview by user
     * @param autoComplete ArrayAdapter<String>
     */
    public void setAutoCompleteAdapter(ArrayAdapter<String> autoComplete){
        this.autoComplete = autoComplete;
    }

    public interface SearchViewListener{
        /**
         * start to search by text input.This is the end search word.
         *
         * @param text search text by user
         */
        void startToSearch(String text);

        /**
         * For auto complete editView , when user input one word,we search at time.
         * @param text words input by user
         */
        void refreshSearchEdit(String text);
    }

    private class EditChangeListener implements TextWatcher{

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(!"".equals(s.toString())){
                deleteImg.setVisibility(VISIBLE);
                showList.setAdapter(autoComplete);
                if(searchViewListener != null){
                    searchViewListener.refreshSearchEdit(s.toString());
                }
                showList.setVisibility(VISIBLE);
            }else{
                deleteImg.setVisibility(View.GONE);
                showList.setVisibility(View.GONE    );
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
