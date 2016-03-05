package com.amodia_pc.midtermexamv2;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;

import com.amodia_pc.midtermexamv2.Constants.Constants;
import com.amodia_pc.midtermexamv2.Entities.Books;
import com.amodia_pc.midtermexamv2.apis.BookApis;

import java.util.ArrayList;

public class BookDetails extends AppCompatActivity {

    private EditText mtvTitle;
    private EditText mtvGenre;
    private EditText mtvAuthor;
    private CheckBox mcbIsRead;
    private Books mBook;
    private int mAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        findViews();

        Intent intent = getIntent();
        if (intent == null) {
            throw new RuntimeException("BookDetails is expecting an int extra passed by Intent");
        }

        mAction = intent.getIntExtra(Constants.EXTRA_ACTION, 0);

        switch (mAction) {
            case Constants.ADD_BOOKDETAIL: {
                break;
            } case Constants.EDIT_BOOKDETAIL: {
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(mBook.getTitle());
                }
                mBook = intent.getParcelableExtra(Constants.EXTRA_POSITION);
                if (mBook == null) {
                    throw new NullPointerException("No movie found at the passed position.");
                }
                new FetchBookTask().execute();
                break;
            } case Constants.VIEW_BOOKDETAIL: {
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(mBook.getTitle());
                }
                mBook = intent.getParcelableExtra(Constants.EXTRA_POSITION);
                if (mBook == null) {
                    throw new NullPointerException("No movie found at the passed position.");
                }
                new FetchBookTask().execute();
                break;
            } default: {
                break;
            }
        }

    }

    public void findViews() {
        mtvTitle = (EditText) findViewById(R.id.etTitle);
        mtvAuthor = (EditText) findViewById(R.id.etAuthor);
        mtvGenre = (EditText) findViewById(R.id.etGenre);
        mcbIsRead = (CheckBox) findViewById(R.id.cbIsRead);

    }

    public class FetchBookTask extends AsyncTask<String, Void, ArrayList<Books>> {
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected  ArrayList<Books> doInBackground(String... params) {
            return BookApis.getBook();
        }

        @Override
        protected void onPostExecute( ArrayList<Books> bookList) {
            mtvAuthor.setText(mBook.getAuthor());
            mtvGenre.setText(mBook.getGenre());
            mtvTitle.setText(mBook.getTitle());
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        switch (mAction) {
            case Constants.ADD_BOOKDETAIL: {
                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.menu_add_book, menu);
                return true;
            } case Constants.EDIT_BOOKDETAIL: {
                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.menu_get_book_details, menu);
                return true;
            } case Constants.VIEW_BOOKDETAIL: {
                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.menu_get_book_details, menu);
                return true;
            } default: {
                return false;
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_edit) {
            return true;
        } else if (id == R.id.action_delete) {
            return true;
        } else if (id == R.id.action_ok) {
           return true;
        } else if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
