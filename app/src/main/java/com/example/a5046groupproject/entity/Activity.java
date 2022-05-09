package com.example.a5046groupproject.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

public class Activity {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "activity_title")
    @NonNull
    public String activity_title;

    /**
     * Datetime ? How?
     */

    @ColumnInfo(name = "details")
    @NonNull
    public String details;

    @ColumnInfo(name = "consume_type")
    @NonNull
    public String consume_type;

    public float amount;


    public Activity( @NonNull String activity_title, @NonNull String details, @NonNull String consume_type, float amount) {
        this.activity_title=activity_title;
        this.details=details;
        this.consume_type=consume_type;
        this.amount = amount;
    }
}
