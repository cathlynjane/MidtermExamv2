package com.amodia_pc.midtermexamv2;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.amodia_pc.midtermexamv2.Adapters.BookAdapters;
import com.amodia_pc.midtermexamv2.Constants.Constants;
import com.amodia_pc.midtermexamv2.Entities.Books;
import com.amodia_pc.midtermexamv2.apis.BookApis;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ListView mListView;
    private ProgressBar mProgressBar;
    private TextView mtvProgress;
    private TextView mtvEmpty;
    private BookAdapters adapter;
    private FloatingActionButton fab;
    private ArrayList<Books> bookArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BookDetails.class);
                intent.putExtra(Constants.EXTRA_ACTION, Constants.ADD_BOOKDETAIL);
                startActivity(intent);
            }
        });

        mtvEmpty = (TextView) findViewById(android.R.id.empty);
        mtvProgress = (TextView) findViewById(R.id.tvprogress);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        mListView = (ListView) findViewById(R.id.list);
        mListView.setOnItemClickListener(this);

        new FetchBookTask().execute();

    }

    public class FetchBookTask extends AsyncTask<String, Void, ArrayList<Books>> {

        protected void onPreExecute() {
            mProgressBar.setVisibility(View.VISIBLE);
            mtvProgress.setVisibility(View.VISIBLE);
            mtvEmpty.setVisibility(View.GONE);
            fab.setVisibility(View.GONE);
            mListView.setVisibility(View.GONE);
        }

        @Override
        protected  ArrayList<Books> doInBackground(String... params) {
            return BookApis.getBook();
        }

        @Override
        protected void onPostExecute( ArrayList<Books> bookList) {

            bookArrayList = bookList;
            mProgressBar.setVisibility(View.GONE);
            mtvProgress.setVisibility(View.GONE);
            fab.setVisibility(View.VISIBLE);

            if (bookList!=null) {
                adapter = new BookAdapters(
                        MainActivity.this,
                        R.layout.list_item, bookList);

                mListView.setAdapter(adapter);
                mListView.setVisibility(View.VISIBLE);
            }
            else {
                mtvEmpty.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, BookDetails.class);
        intent.putExtra(Constants.EXTRA_POSITION, bookArrayList.get(position).getId());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
