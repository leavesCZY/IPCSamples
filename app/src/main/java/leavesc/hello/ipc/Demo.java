package leavesc.hello.ipc;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者：leavesC
 * 时间：2019/4/4 10:46
 * 描述：
 */
public class Demo implements Parcelable {

    private String stringField;

    private int intField;

    private boolean booleanField;

    public Demo(String stringField, int intField, boolean booleanField) {
        this.stringField = stringField;
        this.intField = intField;
        this.booleanField = booleanField;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.stringField);
        dest.writeInt(this.intField);
        dest.writeByte(this.booleanField ? (byte) 1 : (byte) 0);
    }

    protected Demo(Parcel in) {
        this.stringField = in.readString();
        this.intField = in.readInt();
        this.booleanField = in.readByte() != 0;
    }

    public static final Creator<Demo> CREATOR = new Creator<Demo>() {
        @Override
        public Demo createFromParcel(Parcel source) {
            return new Demo(source);
        }

        @Override
        public Demo[] newArray(int size) {
            return new Demo[size];
        }
    };
}
