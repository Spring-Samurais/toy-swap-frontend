package com.springsamurais.toyswap.model

data class Member (
    var id: Long? = null,
    var name: String? = null,
    var nickname: String? = null,
    var location: String? = null,
    var listings: List<Listing>? = null
)