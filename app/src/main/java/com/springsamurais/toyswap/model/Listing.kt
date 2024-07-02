package com.springsamurais.toyswap.model

import java.util.Date

class Listing {

    var id: Long? = null
    var title: String? = null
    var datePosted: Date? = null
    var member: Member? = null
    var category: String? = null
    var description: String? = null
    var itemCondition: String? = null
    var status: String? = null
    var comments: List<Comment>? = null

    constructor()
    constructor(
        id: Long?,
        title: String?,
        datePosted: Date?,
        member: Member?,
        category: String?,
        description: String?,
        itemCondition: String?,
        status: String?,
        comments: List<Comment>?
    ) {
        this.id = id
        this.title = title
        this.datePosted = datePosted
        this.member = member
        this.category = category
        this.description = description
        this.itemCondition = itemCondition
        this.status = status
        this.comments = comments
    }




}
