package com.shgbit.testservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Button;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

public class MainActivity extends AppCompatActivity {

    private TextView tv_msg;
    private ICounter iService;
    private final ServiceConnection conn = new ServiceConnection(){
        //当服务被成功绑定的时候调用的方法.
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {//第二个参数就是服务中的onBind方法的返回值
            Logger.i( "-----onServiceConnected----" );
            iService = (ICounter) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Logger.w( "-----onServiceDisconnected----" );
            //add commit
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Logger.i("MainActivity onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.tv_msg = findViewById(R.id.tv_msg);

        this.findViewById(R.id.btn_start).setOnClickListener( v->{
            Intent intent = new Intent();
            intent.setClass(this, MyCountService.class);
            this.startService(intent);
            Logger.i("-------Start MyCountService-------");
            this.tv_msg.setText( "服务已启动" );
        } );

        this.findViewById(R.id.btn_stop).setOnClickListener( v ->{
            Intent intent = new Intent();
            intent.setClass(this, MyCountService.class);
            this.stopService( intent );
            Logger.i("-------Stop MyCountService-------");
            this.tv_msg.setText( "服务已停止" );
        } );

        this.findViewById(R.id.btn_bind).setOnClickListener( v->{
            Intent intent = new Intent();
            intent.setClass(this, MyCountService.class);
            this.bindService( intent,conn, Context.BIND_AUTO_CREATE);
            this.tv_msg.setText( "服务已绑定" );
        } );
    }

    @Override
    protected void onResume() {
        Logger.i("MainActivity onResume");
        super.onResume();
    }

    @Override
    protected void onStop() {
        Logger.i("MainActivity onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Logger.i("MainActivity onDestroy");
        super.onDestroy();
    }
}