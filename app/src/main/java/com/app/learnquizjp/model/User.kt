package com.app.learnquizjp.model

class User {

    var id: String? = ""
    var username: String? = ""
    var profileImageUrl: String = "default"
    var favorite : MutableList<Int> = mutableListOf()

    constructor(id: String, username: String) {
        this.id = id
        this.username = username
        //this.profileImageUrl = profileImageUrl
        //this.favorite = favorite
    }

    constructor()

}
