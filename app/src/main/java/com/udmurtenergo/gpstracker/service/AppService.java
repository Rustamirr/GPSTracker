package com.udmurtenergo.gpstracker.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.GpsStatus;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Binder;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import com.udmurtenergo.gpstracker.App;
import com.udmurtenergo.gpstracker.database.model.FullLocation;
import com.udmurtenergo.gpstracker.di.module.ServiceModule;
import com.udmurtenergo.gpstracker.interactor.gps.GpsListener;

import javax.inject.Inject;

public class AppService extends Service implements ServiceContract.Service {
    @Inject
    public ServiceContract.Controller controller;
    private ServiceBinder binder;
    private ConnectivityManager connectivityManager;
    private TelephonyManager telephonyManager;
    private GpsListener listener; // presenter

    @Override
    public void onCreate() {
        super.onCreate();
        connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        binder = new ServiceBinder();
        App.getInstance().getInjector().getServiceComponent(new ServiceModule(this)).inject(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        controller.onStartCommand();
        return START_STICKY; // Служба будет перезапущена при нехватке памяти
    }

    @Override
    public void onDestroy() {
        controller.onDestroy();
        super.onDestroy();
    }

    public class ServiceBinder extends Binder {
        public ServiceContract.Service getService() {
            return AppService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    @SuppressLint("MissingPermission")
    public String getDeviceImei(){
        return telephonyManager.getDeviceId();
    }

    @Override
    public boolean networkIsOnline(){
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public void registerListener(GpsListener listener){
        this.listener = listener;
    }

    @Override
    public void unRegisterListener(){
        listener = null;
    }

    @Override
    public void onLocationChanged(Location location){
        if (listener != null){
            listener.onLocationChanged(location);
        }
    }

    @Override
    public void onGpsStatusChanged(GpsStatus gpsStatus) {
        if (listener != null){
            listener.onGpsStatusChanged(gpsStatus);
        }
    }

    @Override
    public void onFilteredLocationChanged(FullLocation fullLocation) {
        if (listener != null){
            listener.onFilteredLocationChanged(fullLocation);
        }
    }
}
