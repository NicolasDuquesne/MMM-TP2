package projet.istic.fr.tp2;

import android.os.Parcel;
import android.os.Parcelable;

public class Person implements Parcelable {

    private String lastName;
    private String firstName;
    private String date;
    private String city;

    public static final Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel source) {
            return new Person(source);
        }

        @Override
        public Person[] newArray(int size) {

            return new Person[size];
        }
    };

    public Person(String lastNames, String firstNames, String date, String city) {
        this.lastName= lastNames;
        this.firstName= firstNames;
        this.date = date;
        this.city = city;
    }

    private Person(Parcel in) {
        lastName = in.readString();
        firstName = in.readString();
        date = in.readString();
        city = in.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(lastName);
        dest.writeString(firstName);
        dest.writeString(date);
        dest.writeString(city);
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getDate() {
        return date;
    }

    public String getCity() {
        return city;
    }

}

