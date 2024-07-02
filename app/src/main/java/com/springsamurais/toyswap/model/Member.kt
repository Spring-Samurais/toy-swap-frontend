package com.springsamurais.toyswap.model

class Member {
    var id: Long? = null
    var name: String? = null
    var nickname: String? = null
    var location: String? = null
    var listings: List<Listing>? = null

    constructor()

    constructor(id: Long?, name: String?, nickname: String?, location: String?, listings: List<Listing>?) {
        this.id = id
        this.name = name
        this.nickname = nickname
        this.location = location
        this.listings = listings
    }
}