package top.itmp.examples;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by hz on 2016/3/30.
 */
public class ExamplesApplication extends Application {

    private final static int DEFAULT_MAX_CACHE_SIZE = (int) (Runtime.getRuntime().maxMemory() / 1024);
    private final static int DEFAULT_CACHE_SIZE = DEFAULT_MAX_CACHE_SIZE / 8;

    private static Context context;
    private static RequestQueue requestQueue;
    private static ImageLoader imageLoader;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

    }

    public static Context getContext() {
        return context;
    }

    public static RequestQueue getRequestQueue() {
        if(requestQueue == null){
            //requestQueue = Volley.newRequestQueue(this);
            requestQueue = Volley.newRequestQueue(context.getApplicationContext(), new HurlStack());
        }
        return requestQueue;
    }

    public static ImageLoader getImageLoader(){
        return getImageLoader(DEFAULT_CACHE_SIZE);
    }

    public static ImageLoader getImageLoader(final int maxCacheSize) {
        if(imageLoader == null){
            imageLoader = new ImageLoader(getRequestQueue(), new ImageLoader.ImageCache() {
                private final LruCache<String, Bitmap>
                        cache = new LruCache<>(maxCacheSize);
                @Override
                public Bitmap getBitmap(String url) {
                    return cache.get(url);
                }

                @Override
                public void putBitmap(String url, Bitmap bitmap) {
                    cache.put(url, bitmap);
                }
            });
        }
        return imageLoader;
    }
}
