package com.file.com.fileupload;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.apache.http.Header;

import java.io.File;
import java.io.FileNotFoundException;

import loopj.android.AsyncHttpClient;
import loopj.android.AsyncHttpResponseHandler;
import loopj.android.RequestParams;

public class MainActivity extends AppCompatActivity {

    private EditText et_upload;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_upload = (EditText)findViewById(R.id.et_upload);
        progressBar = (ProgressBar)findViewById(R.id.progressBar1);


    }

    public  void  click(View view){
        String path = et_upload.getText().toString().trim();
        File file = new File(path);
        if (file.exists()){
            String servleturl = getString(R.string.server);
            AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            try {


            params.put("file",file);     //上传文件
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
            asyncHttpClient.post(servleturl, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Toast.makeText(MainActivity.this,"上传成功",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onProgress(int bytesWritten, int totalSize) {  //  显示上传进度的
                   progressBar.setMax(totalSize);
                   progressBar.setProgress(bytesWritten);
                    super.onProgress(bytesWritten,totalSize);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Toast.makeText(MainActivity.this,"上传失败！！",Toast.LENGTH_SHORT).show();
                }
            });      //AsyncHttpResponseHandler

        }else{
            Toast.makeText(this,"请选择文件！！",Toast.LENGTH_SHORT).show();
        }


    }
}
