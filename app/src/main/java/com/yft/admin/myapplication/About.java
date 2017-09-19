package com.yft.admin.myapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

public class About extends AppCompatActivity {

    TextView references1;
    TextView references2;
    TextView references3;
    TextView references4;
    TextView references5;
    TextView references6;
    TextView references7;
    TextView references8;
    TextView weAre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        references1 = (TextView) findViewById(R.id.href1);
        references2 = (TextView) findViewById(R.id.href2);
        references3 = (TextView) findViewById(R.id.href3);
        references4 = (TextView) findViewById(R.id.href4);
        references5 = (TextView) findViewById(R.id.href5);
        references6 = (TextView) findViewById(R.id.href6);
        references7 = (TextView) findViewById(R.id.href7);
        references8 = (TextView) findViewById(R.id.href8);
        weAre = (TextView) findViewById(R.id.weAre);

        references1.setText(
                Html.fromHtml(getResources().getString(R.string.author_reference1))+"\n"
        );
        references2.setText(
                Html.fromHtml(getResources().getString(R.string.author_reference2))+"\n"
        );
        references3.setText(
                Html.fromHtml(getResources().getString(R.string.author_reference3))+"\n"
        );
        references4.setText(
                Html.fromHtml(getResources().getString(R.string.author_reference4))+"\n"
        );
        references5.setText(
                Html.fromHtml(getResources().getString(R.string.author_reference5))+"\n"
        );
        references6.setText(
                Html.fromHtml(getResources().getString(R.string.author_reference6))+"\n"
        );
        references7.setText(
                Html.fromHtml(getResources().getString(R.string.author_reference7))+"\n"
        );
        references8.setText(
                Html.fromHtml(getResources().getString(R.string.author_reference8))+"\n"
        );
        weAre.setText(R.string.author_team);

        references1.setMovementMethod(LinkMovementMethod.getInstance());
        references2.setMovementMethod(LinkMovementMethod.getInstance());
        references3.setMovementMethod(LinkMovementMethod.getInstance());
        references4.setMovementMethod(LinkMovementMethod.getInstance());
        references5.setMovementMethod(LinkMovementMethod.getInstance());
        references6.setMovementMethod(LinkMovementMethod.getInstance());
        references7.setMovementMethod(LinkMovementMethod.getInstance());
        references8.setMovementMethod(LinkMovementMethod.getInstance());
    }

}
