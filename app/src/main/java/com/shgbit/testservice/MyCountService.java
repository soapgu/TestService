package com.shgbit.testservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.orhanobut.logger.Logger;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class MyCountService extends Service {
    private Long countValue;
    private boolean isStart;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public MyCountService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Logger.i( "------MyCountService onBind-------" );
        return new MyBinder();
    }

    @Override
    public void onCreate() {
        Logger.i("-------MyCountService onCreate-------");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.i("-------MyCountService onStartCommand-------");
        if(!isStart) {
            isStart = true;
            StartCountEngine();
        } else {
            Logger.w("MyCountService has been started!");
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Logger.i("-------MyCountService onDestroy-------");
        disposables.dispose();
        super.onDestroy();
    }

    private void StartCountEngine() {
        disposables.add(
                Observable.interval( 3 , TimeUnit.SECONDS )
                        .subscribe( t -> {
                                    countValue = t;
                                    Logger.i( "current count %d",countValue );
                                    //sendBroadcast(new Intent(Broadcasts.First));
                                } ,
                                e -> Logger.e( e, "On Error" ),
                                ()-> Logger.i("Stop Engine"))
        );

    }

    private class MyBinder extends Binder implements ICounter {

        @Override
        public Long getCount() {
            return countValue;
        }
    }
}
