package cn.s07150818edu.rxjavaonetime;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/8/20.
 */

public class guanjianci extends AppCompatActivity{
    private EditText mEditText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guanjianci);
        mEditText= (EditText) findViewById(R.id.ed);

        RxTextView.textChanges(mEditText).debounce(200, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<CharSequence>() {
                    @Override
                    public boolean test(CharSequence charSequence) throws Exception {
                        return mEditText.getText().toString().length()>0;
                    }
                }).flatMap(new Function<CharSequence, ObservableSource<List<String>>>() {
            @Override
            public ObservableSource<List<String>> apply(CharSequence charSequence) throws Exception {
                List<String> list=new ArrayList<String>();
                list.add("hhh");
                list.add("keyi");
                return Observable.just(list);
            }
        }).observeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<String>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<String> value) {
                Log.d("tag", "onNext: "+value);

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {

            }
        });

    }
}
