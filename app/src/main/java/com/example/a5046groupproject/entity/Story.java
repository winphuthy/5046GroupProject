package com.example.a5046groupproject.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.a5046groupproject.adapter.StoryAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "story_table")
public class Story{
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "owner_id")
    @NonNull
    public String ownerID;

    @ColumnInfo(name = "story_title")
    @NonNull
    public String storyTitle;

    @ColumnInfo(name = "details")
    @NonNull
    public String details;

    @ColumnInfo(name = "consume_type")
    @NonNull
    public String consumeType;

    @ColumnInfo(name = "price")
    @NonNull
    public float price;

    @ColumnInfo(name = "story_time")
    @NotNull
    public String storyTime;


    public Story(@NonNull String ownerID, @NonNull String storyTitle, @NonNull String details,
                 @NonNull String consumeType
            , float price, String storyTime){

        this.ownerID = ownerID;
        this.storyTitle = storyTitle;
        this.details = details;
        this.consumeType = consumeType;
        this.price = price;
        this.storyTime = storyTime;
    }

    public String getOwnerID(){
        return ownerID;
    }

    public void setOwnerID(int uid){
        this.uid = uid;
    }

    public int getUid(){
        return uid;
    }

    public void setUid(int uid){
        this.uid = uid;
    }


    public String getStoryTitle(){
        return storyTitle;
    }

    public void setStoryTitle(@NonNull String storyTitle){
        this.storyTitle = storyTitle;
    }


    public String getDetails(){
        return details;
    }

    public void setDetails(@NonNull String details){
        this.details = details;
    }


    public String getConsumeType(){
        return consumeType;
    }

    public void setConsumeType(@NonNull String consumeType){
        this.consumeType = consumeType;
    }

    public float getPrice(){
        return price;
    }

    public void setPrice(float price){
        this.price = price;
    }

    public String getStoryTime(){
        return storyTime;
    }

    public void setStoryTime(String storyTime){
        this.storyTime = storyTime;
    }

}
