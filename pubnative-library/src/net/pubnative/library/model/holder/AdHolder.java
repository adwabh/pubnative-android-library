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
package net.pubnative.library.model.holder;

import net.pubnative.library.model.APIEndpoint;
import net.pubnative.library.model.response.Ad;

import org.droidparts.util.L;

import android.view.View;

public abstract class AdHolder<T extends Ad>
{
    public T           ad;
    private final View view;

    public AdHolder(View view)
    {
        this.view = view;
    }

    public View getView()
    {
        return view;
    }

    @SuppressWarnings("unchecked")
    public <V extends View> V getView(int id)
    {
        View v = getView();
        if (v != null)
        {
            try
            {
                return (V) v.findViewById(id);
            }
            catch (ClassCastException e)
            {
                L.e(e);
            }
        }
        return null;
    }

    public abstract APIEndpoint getFormat();
}
