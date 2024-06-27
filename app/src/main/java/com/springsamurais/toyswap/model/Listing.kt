package com.springsamurais.toyswap.model

import java.util.Date

class Listing {

    private var id: Long? = null
    private var title: String? = null
    private var datePosted: Date? = null
    private var member: Member? = null
    private var category: String? = null
    private var description: String? = null
    private var itemCondition: String? = null
    private var status: String? = null
    private var comments: List<Comment>? = null

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
