package recibos;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Entity mapped to table TAREAS.
 */
public class Tareas implements Parcelable {

    private Long id;
    private String snr;
    private String latitud;
    private String longitud;
    private String fechahora;
    private String bcRecibo;
    private String cuenta;
    private String servicio;
    private String atendido;

    public Tareas() {
    }

    public Tareas(Long id) {
        this.id = id;
    }

    public Tareas(Parcel in){
        this.id = in.readLong();
        this.snr = in.readString();
        this.latitud = in.readString();
        this.longitud = in.readString();
        this.fechahora = in.readString();
        this.bcRecibo = in.readString();
        this.cuenta = in.readString();
        this.servicio = in.readString();
        this.atendido = in.readString();
    }
    public Tareas(Long id, String snr, String latitud, String longitud, String fechahora, String bcRecibo, String cuenta, String servicio, String atendido) {
        this.id = id;
        this.snr = snr;
        this.latitud = latitud;
        this.longitud = longitud;
        this.fechahora = fechahora;
        this.bcRecibo = bcRecibo;
        this.cuenta = cuenta;
        this.servicio = servicio;
        this.atendido = atendido;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSnr() {
        return snr;
    }

    public void setSnr(String snr) {
        this.snr = snr;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getFechahora() {
        return fechahora;
    }

    public void setFechahora(String fechahora) {
        this.fechahora = fechahora;
    }

    public String getBcRecibo() {
        return bcRecibo;
    }

    public void setBcRecibo(String bcRecibo) {
        this.bcRecibo = bcRecibo;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getAtendido() {
        return atendido;
    }

    public void setAtendido(String atendido) {
        this.atendido = atendido;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(fechahora);
        dest.writeString(longitud);
        dest.writeString(latitud);
        dest.writeString(fechahora);
        dest.writeString(bcRecibo);
        dest.writeString(cuenta);
        dest.writeString(servicio);
        dest.writeString(atendido);
    }
    public static final Parcelable.Creator<Tareas> CREATOR =
            new Parcelable.Creator<Tareas>() {
                @Override
                public Tareas createFromParcel(Parcel source) {
                    return null;
                }

                @Override
                public Tareas[] newArray(int size) {
                    return new Tareas[0];
                }
            };
}
