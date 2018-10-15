package com.example.marmm.demolevel3


import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable

@Entity(tableName = "game")
class Game( @ColumnInfo(name = "title") private var mTitleText: String?, @ColumnInfo(name = "platform") private var mPlatform: String?, @ColumnInfo(name = "note") private var mNote: String?, @ColumnInfo(name = "status")private var mStatus: Int?, @ColumnInfo(name = "dateModified") private var mDateModified: String?) : Parcelable {

    @PrimaryKey(autoGenerate = true) private var gameId: Long? = null
    @Ignore
    private val statusSpin = arrayOf("Want to play", "Playing", "Stalled", "Dropped")

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readString()) {
        gameId = parcel.readValue(Long::class.java.classLoader) as? Long
    }

    fun getPlatform(): String? {
        return mPlatform
    }

    fun setPlatform(mPlatform: String) {
        this.mPlatform = mPlatform
    }

    fun getNote(): String? {
        return mNote
    }

    fun setNote(mNote: String) {
        this.mNote = mNote
    }


    fun getTitleText(): String? {
        return mTitleText
    }

    fun setDateModified(mDateModified: String) {
        this.mDateModified = mDateModified
    }

    fun getDateModified(): String? {
        return mDateModified
    }

    fun setTitleText(mTitleText: String) {
        this.mTitleText = mTitleText
    }

    fun getGameId(): Long? {
        return gameId
    }

    fun setGameId(gameId: Long?) {
        this.gameId = gameId
    }

    fun getStatus(): Int? {
        return mStatus
    }

    fun getStatusSpin(): Array<String> {
        return statusSpin
    }

    fun setgameId(mStatus: Int) {
        this.mStatus = mStatus
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(mTitleText)
        parcel.writeString(mPlatform)
        parcel.writeString(mNote)
        parcel.writeValue(mStatus)
        parcel.writeString(mDateModified)
        parcel.writeValue(gameId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Game> {
        override fun createFromParcel(parcel: Parcel): Game {
            return Game(parcel)
        }

        override fun newArray(size: Int): Array<Game?> {
            return arrayOfNulls(size)
        }
    }



}
