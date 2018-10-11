package com.openclassrooms.freezap.Controllers;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.openclassrooms.freezap.R;
import com.openclassrooms.freezap.Utils.MyAsyncTask;
import com.openclassrooms.freezap.Utils.MyHandlerThread;
import com.openclassrooms.freezap.Utils.Utils;

//Ligne 1 : Implements the MyAsyncTask callbck methods
public class MainActivity extends AppCompatActivity implements MyAsyncTask.Listeners {

    //FOR DESIGN
    private ProgressBar progressBar;
    private MyHandlerThread handlerThread;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get progressbar from layout
        this.progressBar = findViewById(R.id.activity_main_progress_bar);
        this.configureHandlerThread();
    }

    @Override
    protected void onDestroy() {
        //3 - QUIT HANDLER THREAD (Free precious ressources)
        handlerThread.quit();
        super.onDestroy();
    }

    // ------------
    // ACTIONS
    // ------------

    public void onClickButton(View v){
        int buttonTag = Integer.valueOf(v.getTag().toString());
        switch (buttonTag){
            case 10: // CASE USER CLICKED ON BUTTON "EXECUTE ACTION IN MAIN THREAD"
                Utils.executeLongActionDuring7seconds();
                break;
            case 20: // CASE USER CLICKED ON BUTTON "EXECUTE ACTION IN BACKGROUND"
                this.startHandlerThread();
                break;
            case 30:
                break;
            case 40:
                break;
            case 50:
                break;
            case 60:
                this.startAsyncTask();
                break;
            case 70:
                break;
        }
    }
    // 3 - We create and strat our AsyncTask
    private void startAsyncTask() {
        new MyAsyncTask(this).execute();
    }

    private void startHandlerThread() {
        handlerThread.startHandler();
    }

    private void configureHandlerThread() {
        handlerThread = new MyHandlerThread("MyAwesomeHandlerThread", this.progressBar);
    }

    // 2 - Override methods of callback
    @Override
    public void onPreExecute() {
        // 2.1 - We update our UI before task(staring ProgressBar)
        this.updateUIBeforeTask();
    }

    @Override
    public void doInBackground() {

    }

    @Override
    public void onPostExecute(Long taskEnd) {
        // 2.2 - we update our UI before task (stopping ProgressBar)
        this.updateUIAfterTask(taskEnd);
    }

    private void updateUIBeforeTask() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void updateUIAfterTask(Long taskEnd) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, "Task is finally at : "+taskEnd+" !", Toast.LENGTH_SHORT).show();
    }
}
