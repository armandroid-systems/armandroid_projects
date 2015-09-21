package armandroid.com.mx.utils;

import android.content.Context;
import android.location.Location;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import armandroid.com.mx.stpscanner.R;

/**
 * Created by zadtanikus on 26/06/15.
 */
public class Utils {
    private static final String TAG = Utils.class.getName();

    public static String convertdToDm(Location d){
        String[] direction = {"N","S","E","W"};
        double auxLat = d.getLatitude();
        double auxLon = d.getLongitude();

        int indexLa = 0;
        int indexLo = 2;

        if(auxLat < 0){ auxLat *=-1; indexLa = 1;}
        if(auxLon < 0){ auxLon *=-1; indexLo = 3;}

        String latD = ""+(int)Math.floor(auxLat);//new DecimalFormat("##").format(auxLat);
        String longD = ""+(int)Math.floor(auxLon);//new DecimalFormat("##").format(auxLon);


        Log.d(TAG,"PASO 1 "+latD+" | "+longD);
        Log.d(TAG,"PASO 1 "+auxLat+"-"+Double.parseDouble(latD));
        Log.d(TAG,"PASO 1 "+auxLon+"-"+Double.parseDouble(longD));

        String mLat = new DecimalFormat("##.####").format((auxLat - Double.parseDouble(latD))*60);
        String mLong = new DecimalFormat("##.####").format((auxLon - Double.parseDouble(longD))*60);

        Log.d(TAG,"PASO 2 "+mLat.replace(',','.')+"|"+mLong.replace(',','.'));

        Log.d(TAG,"PREVIEW "+longD+mLong.replace(',','.')+direction[indexLo]+","+latD+mLat.replace(',','.')+direction[indexLa]);
        return longD+mLong.replace(',','.')+direction[indexLo]+","+latD+mLat.replace(',','.')+direction[indexLa];

    }

    public static Location gpsToDecimal(String latitud, String longitud){
        long auxLat = 0;
        long auxLong = 0;
        double lat = 0d;
        double longi=0d;
        String direccion = "W";
        Location mLocation = new Location("dummyProvider");

        lat = Double.parseDouble(latitud.substring(0,latitud.length() - 1))/100;
        auxLat = (long)lat;
        lat = auxLat + ((auxLat -lat)/60);

        longi = Double.parseDouble(longitud.substring(0, longitud.length() - 1));
        auxLong = (long)longi;
        longi = auxLong + ((auxLong - longi)/60);

        if(longitud.contains(direccion)){
            longi *= -1;
        }

        mLocation.setLatitude(lat);
        mLocation.setLongitude(longi);

        return mLocation;
    }

    public static String[] decimalToGps(Location location){
        String degLat =new DecimalFormat("##").format( location.getLatitude());
        String degLong = new DecimalFormat("##").format( location.getLongitude());
        double minLat = (location.getLatitude()-Double.parseDouble(degLat))*60;
        double minLong = 0d;
         if(location.getLongitude() < 0){
             Log.d(TAG,"Mi resta "+location.getLongitude()+" - "+(Double.parseDouble(degLong)*-1));
             minLong =  (location.getLongitude()*-1);
             minLong -= (Double.parseDouble(degLong)*-1);
             minLong *= 60;
         }else{
             minLong = (location.getLongitude()-Double.parseDouble(degLong));
             minLong *= 60;
         }

       degLat +=minLat;
       degLong+=minLong;


        Log.d(TAG,"LAT "+degLat+"N LONG "+degLong+"W");

        String[] coords = new String[2];
        coords[0] = degLat.substring(0,9)+"N";
        coords[1] = degLong.substring(1,9)+"W";

        return coords;

    }

    public static String getdayDateTime(){
        Date d = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return sdf.format(d);
    }

    public static String getImei(Context context){
        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    public static String getSNR(String imei){
        String respuesta = "0";
        if(imei.length() > 0){
            respuesta = imei.substring(7,13);
            Log.d(TAG,respuesta);
        }
        return respuesta;
    }

    public static void createDirectoryIfNotExist(File file) throws Exception{
        if(!file.exists()){
            Log.d(TAG,"CREANDO CARPETA QUE NO EXISTIA...");
            file.mkdirs();
            //pathFile.mkdirs();
        }
    }

    public static boolean fileExist(String filePath){
        File myFile = new File(filePath);
        return myFile.exists();
    }
    public static String getDayDateAndTime(){
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyy_HHmmss");
        return sdf.format(date);
    }
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public static String getFilePath(){
        String path = "";
        Log.d(TAG,"EXTERNA AVAILABLE ["+isExternalStorageWritable()+"]");
        Log.d(TAG,"EXTERNA AVAILABLE ["+Environment.DIRECTORY_DCIM+"]");
        File directory = Environment.getExternalStoragePublicDirectory("STP");
        try {
            createDirectoryIfNotExist(directory);
            path = directory.getAbsolutePath()+"/"+getDayDateAndTime()+Constants.REPORT_SUFIX;
        } catch (Exception e) {
            Log.d(TAG,"ERROR AL CREAR CARPETA ["+e+"");
        }

        return path;
    }

}
