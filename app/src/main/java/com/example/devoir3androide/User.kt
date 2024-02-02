package com.example.devoir3androide

//data class User(
//    var name :  String,
//    var email : String,
//    var password : String
//)
//{
//    var id : Int = -1
//
//    constructor(id : Int, name: String, email: String, password: String) : this(name, email, password)
//    {
//        this.id = id
//    }
//}

import androidx.room.Entity
import androidx.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import androidx.room.Ignore

@Entity(tableName = "Users")
data class User(
    val name: String?,
    val email: String?,
    val password: String?,
    ) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    @Ignore
    constructor(id: Long, name: String, email: String, password: String) : this(name, email, password) {
        this.id = id
    }
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id.toInt())
        parcel.writeString(name)
        parcel.writeString(email)
        parcel.writeString(password)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}
