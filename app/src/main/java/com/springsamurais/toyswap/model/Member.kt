package com.springsamurais.toyswap.model

class Member {
    private var id: Long? = null
    private var name: String? = null
    private var nickname: String? = null
    private var location: String? = null
    private var listings: List<Listing>? = null

    constructor()

    constructor(id: Long?, name: String?, nickname: String?, location: String?, listings: List<Listing>?) {
        this.id = id
        this.name = name
        this.nickname = nickname
        this.location = location
        this.listings = listings
    }


}