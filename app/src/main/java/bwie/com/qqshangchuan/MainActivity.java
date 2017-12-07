package bwie.com.qqshangchuan;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import bwie.com.qqshangchuan.net.OkHttpUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mIv;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = new Handler();
        initView();


    }

    private void initView() {
        mIv = (ImageView) findViewById(R.id.iv);
        mIv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv) {
            uploadFile();
        }
    }

    private void uploadFile() {
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        String absolutePath = externalStorageDirectory.getAbsolutePath()+"/b.jpg";
        File file = new File(absolutePath);
        Map<String, Object> params = new HashMap<>();
        params.put("uid", "71");
        params.put("file", file);
        OkHttpUtils.getOkHttpUtils().upload("https://www.zhaoapi.cn/file/upload", params, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                showMsg(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                showMsg(body.string());
            }
        });

    }

    private void showMsg(final String msg) {
        handler.post(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();

                File externalStorageDirectory = Environment.getExternalStorageDirectory();
                String absolutePath = externalStorageDirectory.getAbsolutePath()+"/b.jpg";
                Bitmap bitmap = BitmapFactory.decodeFile(absolutePath);
                mIv.setImageBitmap(bitmap);
            }
        });

    }


}
