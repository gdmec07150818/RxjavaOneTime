package cn.s07150818edu.rxjavaonetime;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView te;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button= (Button) findViewById(R.id.bt);
        Button button1= (Button) findViewById(R.id.bt1);
        button.setOnClickListener(this);
        button1.setOnClickListener(this);
        te= (TextView) findViewById(R.id.tex);
//        merge操作符可以连接多个Observable成一个Observable；
        RxView.clicks(button1).throttleFirst(1, TimeUnit.MILLISECONDS).subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object value) {
                Log.d("TAG", "onNext: "+value);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }


    public Observable<String> getObservable() {
//        Observable observable=Observable.just("b","a");
//        Observable observable=Observable.fromArray("b","a");
        return Observable.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "haha";
            }
        });
//      return  Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> e) throws Exception {
//                    e.onNext("Rxjava第一次上手");
//                    e.onNext("希望可以快点学会");
//                    e.onComplete();
////                e.onError();
//
//            }
//        });

    }

    public Observer<String> getObserve(){
        return new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                //方法最先调用的
                Log.d("Tag", "onSubscribe: ");
            }

            @Override
            public void onNext(String value) {
                Log.d("Tag", "onNext: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("Tag", "onError: ");
            }

            @Override
            public void onComplete() {
                Log.d("Tag", "onComplete: ");
            }
        };
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt:
                Observable<String> observable=getObservable();
//                Observer<String> observer=getObserve();
//                observable.subscribe(observer);
                observable.subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d("Tag", "accept: "+s);
                        te.append(s);
                        te.append("/n");
                    }
                });

                break;
            case R.id.bt1:
                Observable.create(new ObservableOnSubscribe<Object>() {
                    @Override
                    public void subscribe(ObservableEmitter<Object> e) throws Exception {
//                        可以在这里执行耗时的IO操作如网络请求等
                        Msg msg=new Msg("HAHA","11111111111");
                        e.onNext(msg);
                    }
                })
//                        控制Observer和Observable不同情况下的线程，下面方法可将网络请求等耗时操作置于subscribe中，针对Observable子线程
                        .subscribeOn(Schedulers.io())
                        //  UI操作置于主线程中，只针对Observer
                        .observeOn(AndroidSchedulers.mainThread()).
                        subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object value) {
//                        在这可以执行UI操作
//                        Log.d("TAG", "onNext: "+value);
//                        te.setText(value.toString());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
                break;
        }
    }
}
