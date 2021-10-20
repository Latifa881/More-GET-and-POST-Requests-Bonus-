package com.example.moregetandpostrequests

import com.google.gson.annotations.SerializedName

class Details {
    var data: List<Data>? = null

    class Data {
        @SerializedName("name")
        var name: String? = null


        constructor( name: String?) {

            this.name = name
        }
    }
}