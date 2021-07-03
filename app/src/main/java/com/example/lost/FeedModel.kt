package com.example.lost

class FeedModel {

    companion object Factory{
        fun createList():FeedModel=FeedModel()
    }

    var UID:String?=null
    var itemDataText:String?=null
    var done: Boolean?=false
}