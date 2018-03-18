package androidputri.example.com.putri_1202154293_modul4;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class ListMahasiswaActivity extends AppCompatActivity {

    private ListView mListView;
    private ProgressBar mProgressBar;
    private String [] mUsers= {
            "Azizah","Septy","Ning","Tyas","Putri",
            "Puspita","Purwiranda","Citra","Andrieyani","Dwi",
            "Eko","Rajendra","Gadis","Mia","Agung",
            "Micro",};

    private AddItemToListView mAddItemToListView;
    private Button mStartAsyncTask;

    //mendeklarasikan array dan attribut

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_mahasiswa);

        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        mListView = (ListView) findViewById(R.id.listView);
        mStartAsyncTask = (Button) findViewById(R.id.button_startAsyncTask);
        mListView.setVisibility(View.GONE);
        mListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new ArrayList<String>()));

        //mendeklarasikan attribut dan melakukan setAdapter terhadap ListView dengan menggunakan Array

        mStartAsyncTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAddItemToListView = new AddItemToListView();
                mAddItemToListView.execute();

                //Melakukan eksekusi ketika Button diklik
            }
        });
    }

    public class AddItemToListView extends AsyncTask<Void, String, Void> {

        private ArrayAdapter<String> mAdapter;
        private int counter=1;
        ProgressDialog mProgressDialog = new ProgressDialog(ListMahasiswaActivity.this);

        //Deklarasi attribut

        @Override
        protected void onPreExecute() {
            mAdapter = (ArrayAdapter<String>) mListView.getAdapter();

            //for progress dialog
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setTitle("Loading Data");
            mProgressDialog.setMessage("Please wait....");
            mProgressDialog.setCancelable(false);
            mProgressDialog.setProgress(0);

            mProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel Process", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    mAddItemToListView.cancel(true);
                    mProgressBar.setVisibility(View.VISIBLE);
                    dialogInterface.dismiss();
                }
            });
            mProgressDialog.show();

            // method untuk melakukan progress dialog sebelum method onPostExecute dijalankan
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for (String item : mUsers){
                publishProgress(item);
                try {
                    Thread.sleep(100);
                }catch (Exception e){
                    e.printStackTrace();
                }
                if(isCancelled()){
                    mAddItemToListView.cancel(true);
                }
            }
            return null;

            //Method untuk melakukan aktivitas dibackground menggunakan AsyncTask
        }

        @Override
        protected void onProgressUpdate(String... values) {
            mAdapter.add(values[0]);
            Integer current_status = (int) ((counter/(float)mUsers.length)*100);
            mProgressBar.setProgress(current_status);
            mProgressDialog.setProgress(current_status);
            mProgressDialog.setMessage(String.valueOf(current_status+"%"));
            counter++;

            //Method untuk kalkulasi presentase progress dialog
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mProgressBar.setVisibility(View.GONE);
            mProgressDialog.dismiss();
            mListView.setVisibility(View.VISIBLE);

            //Method untuk melakukan eksekusi setImageBitmap setelah method doInBackground dijalankan
        }
    }}
