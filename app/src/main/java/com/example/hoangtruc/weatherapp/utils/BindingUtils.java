package com.example.hoangtruc.weatherapp.utils;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.hoangtruc.weatherapp.MainActivity;
import com.example.hoangtruc.weatherapp.R;

public class BindingUtils {
    @BindingAdapter("loadImage")
    public static void loadImage(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .into(imageView);
    }

    @BindingAdapter("adapter")
    public static void setRecyclerAdapter(RecyclerView view, RecyclerView.Adapter adapter) {
        view.setHasFixedSize(true);
        view.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        view.setAdapter(adapter);
    }

    @BindingAdapter("makeMove")
    public static void makeTextMove(TextView textView, boolean move) {
        textView.setSelected(true);
    }

    @BindingAdapter("font")
    public static void setFont(View view, FontStyle fontStyle) {
        if (view instanceof TextView) {
            ((TextView) view).setTypeface(FuncionUtils.getTypeface(fontStyle.toString()));
        } else if (view instanceof EditText) {
            ((EditText) view).setTypeface(FuncionUtils.getTypeface(fontStyle.toString()));
        } else if (view instanceof Button) {
            ((Button) view).setTypeface(FuncionUtils.getTypeface(fontStyle.toString()));
        }
    }


     @BindingAdapter("setSplash")
    public static void runSplash(final View view,boolean b) {
//        final Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(3000);
//
//                    view.setVisibility(View.GONE);
//
//                } catch (Exception ex) {
//
//                } finally {
//
//
//                }
//            }
//        });
//        thread.start();
         new Handler().postDelayed(new Runnable(){
             @Override
             public void run() {
                 view.setVisibility(View.GONE);

             }
         }, 3000);
    }
}
