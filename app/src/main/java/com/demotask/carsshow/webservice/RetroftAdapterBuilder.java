package com.demotask.carsshow.webservice;

import android.content.Context;

import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;
import retrofit.client.OkClient;
import com.squareup.okhttp.OkHttpClient;


/**
 * Created by edrsoftware on 25.06.15.
 */
public class RetroftAdapterBuilder {

    public static retrofit.RestAdapter buildAdapter(Context context) {
        return buildAdapter(context, RESTServiceConstants.currentWebserviceUrl);
    }

    public static retrofit.RestAdapter buildAdapter(Context context, String serverUrl) {
        // create client manually to be able to supply own SSLContext
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setSslSocketFactory(createSSLSocketFactory(context));

        // build the REST Adapter
        RestAdapter.Builder b = new RestAdapter.Builder();
        b.setRequestInterceptor(new AuthorizationInterceptor());
        b.setEndpoint(serverUrl);
        b.setClient(new OkClient(okHttpClient));

        if (AnalyticsDimensions.UserType.PRODUCTIVE.equals(ApplicationConstants.ANALYTICS_USER_TYPE)) {
            b.setLogLevel(LogLevel.BASIC);
        } else {
            b.setLogLevel(LogLevel.FULL);
        }

        // GsonBuilder builder = new GsonBuilder();
        // builder.setDateFormat(DateUtil.asanaFormatString);

        // GsonConverter converter = new GsonConverter(builder.create());
        // b.setConverter(converter);

        RestAdapter ra = b.build();

        return ra;
    }

        private static SSLSocketFactory createSSLSocketFactory(Context context) {
        SSLContext sslContext;
        try {
            sslContext = SSLContext.getInstance("TLS");
            KeyStore[] keyStores = new KeyStore[] { loadKeyStore(context) };
            TrustManager additionalKeyTrustManager = new AdditionalKeyStoresTrustManager(keyStores);

            TrustManager[] trustManagers = new TrustManager[] { additionalKeyTrustManager };
            sslContext.init(null, trustManagers, null);
        } catch (GeneralSecurityException e) {
            throw new AssertionError(); // The system has no TLS. Just give up.
        }
        return sslContext.getSocketFactory();
    }

    private static KeyStore loadKeyStore(Context context) {
        try {
            final KeyStore ks = KeyStore.getInstance("BKS");
            final InputStream in = context.getResources().openRawResource(R.raw.mystore);

            try {
                // don't forget to put the password used above in strings.xml/mystore_password
                ks.load(in, context.getString(R.string.mystore_password).toCharArray());
            } finally {
                in.close();
            }

            return ks;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private RetrofitAdapterBuilder() {
    }
}
