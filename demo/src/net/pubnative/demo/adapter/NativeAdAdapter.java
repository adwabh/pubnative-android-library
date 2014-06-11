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
package net.pubnative.demo.adapter;

import static org.droidparts.util.ui.ViewUtils.findViewById;
import net.pubnative.demo.R;
import net.pubnative.sdk.model.holder.NativeAdHolder;
import android.content.Context;
import android.view.LayoutInflater;

public class NativeAdAdapter extends AbstractAdHolderAdapter<NativeAdHolder> {

	public NativeAdAdapter(Context ctx) {
		super(ctx);
	}

	@Override
	public NativeAdHolder makeAndAddHolder() {
		NativeAdHolder h = new NativeAdHolder();
		h.view = LayoutInflater.from(getContext()).inflate(
				R.layout.view_response_native_format, null);
		h.iconView = findViewById(h.view, R.id.view_icon);
		h.titleView = findViewById(h.view, R.id.view_title);
		h.subtitleView = findViewById(h.view, R.id.view_subtitle);
		h.ratingView = findViewById(h.view, R.id.view_rating);
		h.descriptionView = findViewById(h.view, R.id.view_description);
		h.bannerView = findViewById(h.view, R.id.view_banner);
		h.downloadView = findViewById(h.view, R.id.view_download);
		add(h);
		return h;
	}

}
