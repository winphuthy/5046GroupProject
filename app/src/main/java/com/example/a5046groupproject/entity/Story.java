package com.example.a5046groupproject.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "story_table")
public class Story {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "consume_type")
    @NonNull
    public String consumeType;

    @ColumnInfo(name = "story_title")
    @NonNull
    public String storyTitle;
    /*
    @ColumnInfo(name = "details")
    public String details;
     */
    @ColumnInfo(name = "price")
    @NonNull
    public float price;

    @ColumnInfo(name = "story_time")
    @NotNull
    public long storyTime;


    public Story(@NonNull String consumeType,@NonNull String storyTitle, float price, long storyTime) {

        this.consumeType = consumeType;
        this.storyTitle = storyTitle;
        this.price = price;
        this.storyTime = storyTime;
    }

    public int getUid() { return uid; }

    public void setUid(int uid) { this.uid = uid; }


    public String getStoryTitle() {
        return storyTitle;
    }

    public void setStoryTitle(@NonNull String storyTitle) {
        this.storyTitle = storyTitle;
    }

    public String getConsumeType() {
        return consumeType;
    }

    public void setConsumeType(@NonNull String consumeType) {
        this.consumeType = consumeType;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public long getStoryTime() {
        return storyTime;
    }

    public void setStoryTime(long storyTime) {
        this.storyTime = storyTime;
    }
}
