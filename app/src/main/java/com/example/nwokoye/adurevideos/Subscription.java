package com.example.nwokoye.adurevideos;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.example.nwokoye.adurevideos.util.IabHelper;
import com.example.nwokoye.adurevideos.util.IabResult;
import com.example.nwokoye.adurevideos.util.Inventory;
import com.example.nwokoye.adurevideos.util.Purchase;

import java.util.ArrayList;

public class Subscription extends AppCompatActivity {
    Activity activity ;
    private ProgressDialog progDailog;
    private static final String TAG =
            "Subscription";
    IabHelper mHelper;
    static final String ITEM_SKU = "android.test.purchased";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);
        activity = this;
        progDailog = ProgressDialog.show(activity, "Loading", "Please wait...", true);
        progDailog.setCancelable(false);


        final WebView webView = (WebView) findViewById(R.id.webView1);
        Button fab = (Button) findViewById(R.id.fab);
        SwipeRefreshLayout mySwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        webView.reload();
                    }

                });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                progDailog.show();
                view.loadUrl(url);

                return true;
            }

            @Override
            public void onPageFinished(WebView view, final String url) {
                progDailog.dismiss();
            }
        });

        webView.loadUrl("http://Aduretv.com/a/subscription.html");



    String base64EncodedPublicKey =
                "<MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA5wnb2Xoyd4tY025FyZAx7Js/U5leeo+PvGzWf1yKKdsoI/VARDf+QPvFFGoiM/dCD0gRNuhsMhr3sYpPrpAioy4xvYaGFd2TUlt932Dyrd3pbIhVTy6LaUxLe23KP8ryP72xLknDJRxQKeeSBDW9jzwP2X3Lb2y7WFASCpsjdM7nDEz9aZltv8Kms51PQs9cfcW0WhQPVyGnWCQelrzY5JHw1+800mMVuKzFiwUIPEsXU+8VdW3NVvT/gYKlywa2CzuuZkvLYmsKghuvcJoD8cj+UuT+7thEUcUQlqv+Npl3Mwz9P84uxF8geB6CAOBU96jGnW7xxwEWsyUJdGyUawIDAQAB>";

        mHelper = new IabHelper(this, base64EncodedPublicKey);

        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) {
                    Log.d(TAG, "In-app Billing setup failed: " +
                                                       result);
                } else {
                    Log.d(TAG, "In-app Billing is set up OK");
                                           }
                                       }
                                   });
    }
    public void buyClick(View view) {
        mHelper.launchPurchaseFlow(this, ITEM_SKU, 10001,
                mPurchaseFinishedListener, "mypurchasetoken");
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data)
    {
        if (!mHelper.handleActivityResult(requestCode,
                resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener
            = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result,
                                          Purchase purchase)
        {
            if (result.isFailure()) {
                // Handle error
                return;
            }
            else if (purchase.getSku().equals(ITEM_SKU)) {
                consumeItem();
               // buyButton.setEnabled(false);
            }

        }
    };
    public void consumeItem() {
        mHelper.queryInventoryAsync(mReceivedInventoryListener);
    }

    IabHelper.QueryInventoryFinishedListener mReceivedInventoryListener
            = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result,
                                             Inventory inventory) {

            if (result.isFailure()) {
                // Handle failure
            } else {
                mHelper.consumeAsync(inventory.getPurchase(ITEM_SKU),
                        mConsumeFinishedListener);
            }
        }
    };
    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener =
            new IabHelper.OnConsumeFinishedListener() {
                public void onConsumeFinished(Purchase purchase,
                                              IabResult result) {

                    if (result.isSuccess()) {
                        Intent i = new Intent(Subscription.this,StartingPoint.class);
                        startActivity(i);
                        Subscription.this.finish();
                    } else {
                        // handle error
                    }
                }
            };
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHelper != null) mHelper.dispose();
        mHelper = null;
    }
    }



