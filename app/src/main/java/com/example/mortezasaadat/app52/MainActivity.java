package com.example.mortezasaadat.app52;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mortezasaadat.app52.Model.ItunesStuff;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {



    TextView txtArtistName;
    TextView txtType;
    TextView txtKind;
    TextView txtCollectionName;
    TextView txtTrackName;
    ImageView imgArt;
    String imgURL;
    Button btnGetData;
    Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        txtArtistName = (TextView) findViewById(R.id.txtArtistName);
        txtType = (TextView) findViewById(R.id.txtType);
        txtKind = (TextView) findViewById(R.id.txtKind);
        txtCollectionName = (TextView) findViewById(R.id.txtCollectionName);
        txtTrackName = (TextView) findViewById(R.id.txtTrackName);
        imgArt = (ImageView) findViewById(R.id.imgArt);
        btnGetData = (Button) findViewById(R.id.btnGetData);

        btnGetData.setOnClickListener(MainActivity.this);


    }


    @Override
    public void onClick(View v) {


        JSONItunesStuffTask jsonItunesStuffTask = new JSONItunesStuffTask(MainActivity.this);
        jsonItunesStuffTask.execute();

    }

    private class JSONItunesStuffTask extends AsyncTask<String, Void, ItunesStuff> {


        Context context;

        ProgressDialog progressDialog;

        public JSONItunesStuffTask(Context context) {

            this.context = context;
            progressDialog = new ProgressDialog(context);

        }


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog.setTitle("Downloading Info From Itunes.....Please Wait");
                progressDialog.show();

            }

        @Override
        protected ItunesStuff doInBackground(String... params) {

            ItunesStuff itunesStuff = new ItunesStuff();

            ItunesHTTPClient itunesHTTPClient = new ItunesHTTPClient();

            String data = (itunesHTTPClient.getItunesStuffData());

            try {

                itunesStuff = JsonItunesParser.getItunesStuff(data);
                imgURL = itunesStuff.getArtistViewURL();
                bitmap = (itunesHTTPClient.getBitmapFromURL(imgURL));



            } catch (Throwable t) {

                t.printStackTrace();

            }

            return itunesStuff;


        }

        @Override
        protected void onPostExecute(ItunesStuff itunesStuff) {
            super.onPostExecute(itunesStuff);



            txtArtistName.setText(itunesStuff.getArtistName());
            txtType.setText(itunesStuff.getType());
            txtKind.setText(itunesStuff.getKind());
            txtCollectionName.setText(itunesStuff.getCollectionName());
            txtTrackName.setText(itunesStuff.getTrackName());
            imgArt.setImageBitmap(bitmap);

            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

        }
    }



}
