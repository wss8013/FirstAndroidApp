package edu.neu.madcourse.NUMAD22Su_ShashaWang;

import android.os.Parcel;
import android.os.Parcelable;

public class LinkInfo implements Parcelable {
    private String name;
    private String link;
    public LinkInfo(String name, String link) {
        this.name = name;
        this.link = link;
    }

    protected LinkInfo(Parcel in) {
        String[] data = new String[2];
        in.readStringArray(data);
        name = data[0];
        link = data[1];
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {this.name,
                this.link});
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LinkInfo> CREATOR = new Creator<LinkInfo>() {
        @Override
        public LinkInfo createFromParcel(Parcel in) {
            return new LinkInfo(in);
        }

        @Override
        public LinkInfo[] newArray(int size) {
            return new LinkInfo[size];
        }
    };

    public String getName(){
        return name;
    }

    public String getLink(){
        return link;
    }
}
