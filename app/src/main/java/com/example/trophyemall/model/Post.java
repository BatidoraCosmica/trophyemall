package com.example.trophyemall.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.Calendar;

/**
 * Esta es la clase pojo utilizada para las publicaciones. No creo que sea necesaria una
 * explicación referente a esta clase
 */

@Entity(tableName = Post.TABLE_NAME,
        indices = {@Index(value = {Post.ID},unique = true)})
public class Post implements Parcelable {

    public static final String TABLE_NAME="post";
    public static final String ID= BaseColumns._ID;
    public static final String USUARIO="usuario";
    public static final String TIPO="tipo";
    public static final String DESCRIPCION="descripcion";
    public static final String FOTO_URI="foto_uri";
    public static final String ORDER="order";

    @ColumnInfo(name = ID)
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = USUARIO)
    private String usuario;
    @ColumnInfo(name = TIPO)
    private String tipo;
    @ColumnInfo(name = DESCRIPCION)
    private String descripcion;
    @ColumnInfo(name = FOTO_URI)
    private String fotoUri;
    @ColumnInfo(name = ORDER)
    private long order;

    public Post() {
    }

    public Post(String usuario, String tipo, String descripcion, String fotoUri) {
        this.order = Calendar.getInstance().getTimeInMillis();
        this.usuario = usuario;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.fotoUri = fotoUri;
    }

    @Ignore
    public Post(String usuario, String tipo, String descripcion) {
        this.order = Calendar.getInstance().getTimeInMillis();
        this.usuario = usuario;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.fotoUri = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFotoUri() {
        return fotoUri;
    }

    public void setFotoUri(String fotoUri) {
        this.fotoUri = fotoUri;
    }

    public long getOrder() {
        return order;
    }

    public void setOrder(long order) {
        this.order = order;
    }

    public static String getTableName() {
        return TABLE_NAME;
    }

    public static String getID() {
        return ID;
    }

    public static String getUSUARIO() {
        return USUARIO;
    }

    public static String getTIPO() {
        return TIPO;
    }

    public static String getDESCRIPCION() {
        return DESCRIPCION;
    }

    public static String getORDER() {
        return ORDER;
    }

    protected Post(Parcel in) {
        id = in.readInt();
        usuario = in.readString();
        tipo = in.readString();
        descripcion = in.readString();
        fotoUri = in.readString();
        order = in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(usuario);
        dest.writeString(tipo);
        dest.writeString(descripcion);
        dest.writeString(fotoUri);
        dest.writeLong(order);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Post> CREATOR = new Parcelable.Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };
}
