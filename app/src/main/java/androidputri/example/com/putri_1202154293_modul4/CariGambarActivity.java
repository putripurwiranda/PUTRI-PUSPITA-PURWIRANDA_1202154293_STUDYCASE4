package androidputri.example.com.putri_1202154293_modul4;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;

public class CariGambarActivity extends AppCompatActivity {

    //Deklarasi variable
    private ImageView downloadedImage;
    private ProgressDialog mProgressDialog;
    private EditText linkUrl;
    private Button imageDownloaderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari_gambar);

        imageDownloaderButton = (Button) findViewById(R.id.button_startAsyncTask);
        downloadedImage = (ImageView) findViewById(R.id.ImageView);
        linkUrl = (EditText)findViewById(R.id.urlText);


        imageDownloaderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//Eksekusi ketika button diklik
                String downloadUrl = linkUrl.getText().toString();
                if(downloadUrl.isEmpty()){
                    //Menampilkan toast/pesan ketika button diklik
                    Toast.makeText(CariGambarActivity.this,"Masukkan URL gambar terlebih dahulu",Toast.LENGTH_SHORT).show();
                }else {
                    // Execute DownloadImage AsyncTask
                    new ImageDownloader().execute(downloadUrl);
                }
            }
        });
    }
    private class ImageDownloader extends AsyncTask<String, Void, Bitmap> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create progress dialog
            mProgressDialog = new ProgressDialog(CariGambarActivity.this);
            // Set judul progress dialog
            mProgressDialog.setTitle("Search Image");
            // Set pesan pada progress dialog
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // menampilkan progress dialog
            mProgressDialog.show();
        }

        @Override
        protected Bitmap doInBackground(String... URL) {

            String imageURL = URL[0];

            Bitmap bitmap = null;
            try {
                // Download Image dari URL
                InputStream input = new java.net.URL(imageURL).openStream();

                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }


        @Override
        protected void onPostExecute(Bitmap result) {
            // Set bitmap ke dalam ImageView
            downloadedImage.setImageBitmap(result);
            // Tutup progress dialog
            mProgressDialog.dismiss();
        }
    }
}



