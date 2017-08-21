package cn.s07150818edu.rxjavaonetime;

import com.google.gson.Gson;

/**
 * Created by Administrator on 2017/8/20.
 */

public class Msg {
    private String name;
    private String tel;

    public Msg(String name, String tel) {
        this.name = name;
        this.tel = tel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
