package com.amodia_pc.midtermexamv2.apis;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.amodia_pc.midtermexamv2.Entities.Books;
import com.amodia_pc.midtermexamv2.HttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by AMODIA-PC on 3/5/2016.
 */
public class BookApis {
    public static final String BASE_URL     = "http://joseniandroid.herokuapp.com";
    private static final String TAG_ID   = "_id";
    private static final String TAG_TITLE  = "title";
    private static final String TAG_GENRE  = "genre";
    private static final String TAG_AUTHOR  = "author";
    private static final String TAG_ISREAD  = "isRead";

    private static final String TAG_NEWBOOK  = "Book";

    public static ArrayList<Books> getBook() {
        Uri builtUri = Uri.parse(BookApis.BASE_URL).buildUpon()
                .appendEncodedPath("api")
                .appendEncodedPath("books")
                .build();

        String json = HttpUtils.GET(builtUri);
        Log.d("Boholst", json);
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        ArrayList<Books> bookList = new ArrayList<>();
        try {
            JSONArray jsonArr = new JSONArray(json);
            int arrSize = jsonArr.length();

            for (int i = 0; i < arrSize; i++) {
                JSONObject jsonObject = jsonArr.getJSONObject(i);
                String  id = jsonObject.getString(TAG_ID),
                        title = jsonObject.getString(TAG_TITLE),
                        genre = jsonObject.getString(TAG_GENRE),
                        author = jsonObject.getString(TAG_AUTHOR);
                boolean isRead = jsonObject.getBoolean(TAG_ISREAD);

                bookList.add(new Books(id, title, genre, author, isRead));

            }
            return bookList;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("DANICA_PRETTY",e.toString());
            return null;
        }
    }

    public static void addBook(Books book) {
        Uri uri = Uri.parse(BookApis.BASE_URL).buildUpon()
                .appendEncodedPath("api")
                .appendEncodedPath("books")
                .build();
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(TAG_NEWBOOK, book);
            Log.d("POST_RESPONSE",HttpUtils.POST(uri, jsonObject));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void searchBook(Uri uri, Books book) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(TAG_NEWBOOK, book);
            Log.d("POST_RESPONSE", HttpUtils.POST(uri, jsonObject));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
