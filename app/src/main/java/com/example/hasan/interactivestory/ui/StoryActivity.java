package com.example.hasan.interactivestory.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hasan.interactivestory.R;
import com.example.hasan.interactivestory.model.Page;
import com.example.hasan.interactivestory.model.Story;

public class StoryActivity extends Activity {

    public static final String TAG =StoryActivity.class.getSimpleName();

    private Story mStory = new Story();
    private ImageView mImageView;
    private TextView mTextView;
    private Button mChoice1;
    private Button mChoice2;
    private String mName;
    private Page mCurrentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
        Intent intent = getIntent();
        mName = intent.getStringExtra(getString(R.string.key_name));

        if (mName == null){
            mName = "friend";
        }

        Log.d(TAG, mName);

        mImageView = (ImageView) findViewById(R.id.StoryImageView);
        mTextView = (TextView) findViewById(R.id.StoryTextView);
        mChoice1 = (Button) findViewById(R.id.ChoiceButton1);
        mChoice2 = (Button) findViewById(R.id.ChoiceButton2);

        loadPage(0);
    }

    private void loadPage (int choice) {
        mCurrentPage = mStory.getPage(choice);

        Drawable drawable = getResources().getDrawable(mCurrentPage.getImageId());
        mImageView.setImageDrawable(drawable);

        String pageText = mCurrentPage.getText();
        // Add the name if placeholder is included, won't add if not included.
        pageText = String.format(pageText, mName);
        mTextView.setText(pageText);

        if (mCurrentPage.getIsFinal()) {

            mChoice1.setVisibility(View.INVISIBLE);
            mChoice2.setText(getString(R.string.rejoue));
            mChoice2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }

        else {
            mChoice1.setText(mCurrentPage.getChoice1().getText());
            mChoice2.setText(mCurrentPage.getChoice2().getText());

            mChoice1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int nextPage = mCurrentPage.getChoice1().getNextPage();
                    loadPage(nextPage);
                }
            });

            mChoice2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int nextPage = mCurrentPage.getChoice2().getNextPage();
                    loadPage(nextPage);
                }
            });
        }
    }


   }
