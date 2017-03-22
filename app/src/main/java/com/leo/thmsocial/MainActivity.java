package com.leo.thmsocial;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

import com.leo.Views.Background;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private TextView mDesc, mDesc0, mDesc1, mDesc2;
    private Background mBackground, mBackground0, mBackground1, mBackground2;
    private SeekBar mSeekBar, mSeekBar0, mSeekBar1, mSeekBar2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDesc = (TextView) findViewById(R.id.tv_cash_grade_blind);
        mBackground = (Background) findViewById(R.id.background_cash_grade_blind);
        mSeekBar = (SeekBar) findViewById(R.id.seekbar_cash_grade_blind);
        mDesc0  = (TextView) findViewById(R.id.tv_cash_grade);
        mBackground0 = (Background) findViewById(R.id.background_cash_grade);
        mSeekBar0 = (SeekBar) findViewById(R.id.seekbar_cash_grade);
        mDesc1  = (TextView) findViewById(R.id.tv_cash_grade1);
        mBackground1 = (Background) findViewById(R.id.background_cash_grade1);
        mSeekBar1 = (SeekBar) findViewById(R.id.seekbar_cash_grade1);
        mDesc2  = (TextView) findViewById(R.id.tv_cash_grade2);
        mBackground2 = (Background) findViewById(R.id.background_cash_grade2);
        mSeekBar2 = (SeekBar) findViewById(R.id.seekbar_cash_grade2);
        init();
        mSeekBar.setOnSeekBarChangeListener(this);
        mSeekBar0.setOnSeekBarChangeListener(this);
        mSeekBar1.setOnSeekBarChangeListener(this);
        mSeekBar2.setOnSeekBarChangeListener(this);
    }

    Integer []bs0 = {1,2};
    Integer []bs1 = {1,2,4,8,16,32};
    String []strs = {"aa"};
    String []strs1 = {"aaa","bb","ccccccc","dd","eeeeeee","ff","gg","hh"};
    private void init() {
        initSeekBar(mSeekBar,mBackground,mDesc, bs0, 0);
        initSeekBar(mSeekBar0,mBackground0,mDesc0, bs1, 3);
        initSeekBar(mSeekBar1,mBackground1,mDesc1, strs, 0);
        initSeekBar(mSeekBar2,mBackground2,mDesc2, strs1, 5);
    }

    private void initSeekBar(SeekBar seekBar, Background bg, TextView tv, Object [] objs, int index){
        int length = objs.length;
        if(length==0)return;
        bg.setText(objs);
        if(length == 1){
            bg.setCurrentNumber(1);
        }else {
            if(index>length) index=0;
            bg.setCurrentNumber(index+1);
        }
        tv.setText(objs[index] + "");
        seekBar.setProgress((int) (bg.getPercent() * 100));
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        float percent = progress/100f;
        int currentPoint;
        switch (seekBar.getId()) {
            case R.id.seekbar_cash_grade_blind:
                mBackground.setPercent(percent);
                currentPoint = mBackground.getCurrentPointNumber();
                log(""+currentPoint);
                mDesc.setText(String.valueOf(bs0[currentPoint]));
                break;
            case R.id.seekbar_cash_grade:
                mBackground0.setPercent(percent);
                currentPoint = mBackground0.getCurrentPointNumber();
                mDesc0 .setText(String.valueOf(bs1[currentPoint]));
                log(""+currentPoint);
                break;
            case R.id.seekbar_cash_grade1:
                mBackground1.setPercent(percent);
                currentPoint = mBackground1.getCurrentPointNumber();
                mDesc1 .setText(strs[currentPoint]);
                log(""+currentPoint);
                break;
            case R.id.seekbar_cash_grade2:
                mBackground2.setPercent(percent);
                currentPoint = mBackground2.getCurrentPointNumber();
                mDesc2 .setText(strs1[currentPoint]);
                log(""+currentPoint);
                break;
        }
    }
    private void log(String text){
        Log.e("==========", text);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        int progress = 0;
        switch (seekBar.getId()){
            case R.id.seekbar_cash_grade_blind:
                progress = (int) (mBackground.getPercent() * 100);
                break;
            case R.id.seekbar_cash_grade:
                progress = (int) (mBackground0.getPercent() * 100);
                break;
            case R.id.seekbar_cash_grade1:
                progress = (int) (mBackground1.getPercent() * 100);
                break;
            case R.id.seekbar_cash_grade2:
                progress = (int) (mBackground2.getPercent() * 100);
                break;
        }
        seekBar.setProgress(progress);
    }
}
