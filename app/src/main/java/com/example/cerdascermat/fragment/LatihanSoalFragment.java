package com.example.cerdascermat.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;

import com.example.cerdascermat.R;


public class LatihanSoalFragment extends Fragment {

    ProgressBar bar;
    WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_latihan_soal, container, false);

        webView = view.findViewById(R.id.webView);
        bar = view.findViewById(R.id.progressBar2);

        webView.setWebViewClient(new myWebclient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://forms.gle/93GWpdak2RvF4eQZ7");

        return view;
    }

    public class myWebclient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            bar.setVisibility(View.GONE);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith("http:") || url.startsWith("https:")) {
                return false;
            } else {
                if (url.startsWith("intent://")) {
                    try {
                        Context context = webView.getContext();
                        Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                        if (intent != null) {
                            PackageManager packageManager = context.getPackageManager();
                            ResolveInfo info = packageManager.resolveActivity(intent,
                                    PackageManager.MATCH_DEFAULT_ONLY);
                            if ((intent != null) && ((intent.getScheme().equals("https"))
                                    || (intent.getScheme().equals("http")))) {
                                String fallbackUrl = intent.getStringExtra(
                                        "browser_fallback_url");
                                webView.loadUrl(fallbackUrl);
                                return true;
                            }
                            if (info != null) {
                                context.startActivity(intent);
                            } else {
                                // Call external broswer
                                String fallbackUrl = intent.getStringExtra(
                                        "browser_fallback_url");
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                                        Uri.parse(fallbackUrl));
                                context.startActivity(browserIntent);
                            }
                            return true;
                        } else {
                            return false;
                        }
                    } catch (Exception e) {
                        return false;
                    }
                } else {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    webView.getContext().startActivity(intent);
                    return true;
                }
            }
        }
    }

}



