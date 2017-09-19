package com.test.nutshell.logintest.injection.module;


import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jetbrains.annotations.NotNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import com.test.nutshell.logintest.data.network.ApiService;
import com.test.nutshell.logintest.injection.ApplicationContext;

@Module
public final class ServiceModule {
    @Provides
    @Singleton
    @NotNull
    public final Gson provideGson() {
        return new GsonBuilder()
                .enableComplexMapKeySerialization()
                .create();
    }

    @Provides
    @Singleton
    @NotNull
    public final OkHttpClient provideHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(logging);
        return builder.build();
    }

    @Provides
    @Singleton
    @NotNull
    public final ApiService provideApiService(@NotNull OkHttpClient httpClient, @NotNull Gson gson) {
        return new retrofit2.Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/")
                .client(httpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(ApiService.class);
    }

    @Provides
    @NotNull
    @Singleton
    public final SharedPreferences provideSharedPreferences(@NotNull Context context) {
        return context.getSharedPreferences("pref", Context.MODE_PRIVATE);
    }

}
