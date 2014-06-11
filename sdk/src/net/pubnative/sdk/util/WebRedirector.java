/**
 * Copyright 2014 PubNative GmbH
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package net.pubnative.sdk.util;

import static android.content.Intent.ACTION_VIEW;
import net.pubnative.sdk.R;

import org.droidparts.util.L;
import org.droidparts.util.intent.IntentHelper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.net.Uri;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebRedirector implements OnCancelListener {

	private static final String MARKET_PREFIX = "market://details?id=";
	private static final String PLAYSTORE_PREFIX = "https://play.google.com/store/apps/details?id=";

	private WebView webView;

	private final Activity act;
	private final String link;

	private Dialog loadingDialog;

	private boolean cancelled = false;

	public WebRedirector(Activity act, String link) {
		this.act = act;
		this.link = link;
	}

	public void doBrowserRedirect() {
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
		act.startActivity(intent);
	}

	public void doBackgroundRedirect() {
		try {
			loadingDialog = ProgressDialog.show(act, null,
					act.getString(R.string.loading___), true);
			webView = makeWebView();
			webView.loadUrl(link);
		} catch (Exception ignored) {
		}
	}

	public void cancel() {
		cancelled = true;
		try {
			webView.stopLoading();
			loadingDialog.dismiss();
		} catch (Exception ignored) {
		}
	}

	@Override
	public void onCancel(DialogInterface dialog) {
		cancel();
	}

	@SuppressLint("SetJavaScriptEnabled")
	private WebView makeWebView() {
		WebViewClient wvc = new WebViewClient() {

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				if (isPlayStoreLink(url)) {
					openInPlayStore(url);
				} else {
					view.loadUrl(url);
				}
				return true;
			}

			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				L.w("Page error code : %s, desc : %s.", errorCode, description);
				openInPlayStore(link);
			}
		};
		WebView wv = new WebView(act);
		wv.setWebChromeClient(new WebChromeClient());
		wv.clearCache(true);
		wv.clearHistory();
		wv.getSettings().setJavaScriptEnabled(true);
		wv.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		wv.setWebViewClient(wvc);
		return wv;
	}

	private static boolean isPlayStoreLink(String url) {
		return url.startsWith(MARKET_PREFIX)
				|| url.startsWith(PLAYSTORE_PREFIX);
	}

	private void openInPlayStore(String url) {
		if (!cancelled) {
			cancel();
			Intent intent = new Intent(ACTION_VIEW, toPlayStoreUri(url));
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			IntentHelper.startActivityOrWarn(act, intent);
		}
	}

	private static Uri toPlayStoreUri(String url) {
		if (url.startsWith(PLAYSTORE_PREFIX)) {
			String appId = url.substring(PLAYSTORE_PREFIX.length());
			url = MARKET_PREFIX + appId;
		}
		return Uri.parse(url);
	}

}
