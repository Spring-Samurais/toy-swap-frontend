package com.springsamurais.toyswap.model

class Listing {

    var id: Long? = null
    var title: String? = null
    var photo: String? = null
    var member: Member? = null
    var datePosted: String? = null
    var category: String? = null
    var description: String? = null
    var itemCondition: String? = null
    var statusListing: String? = null
    var comments: List<Comment>? = null

    constructor()
    constructor(
        id: Long?,
        title: String?,
        photo: String?,
        datePosted: String?,
        member: Member?,
        category: String?,
        description: String?,
        itemCondition: String?,
        statusListing: String?,
        comments: List<Comment>?
    ) {
        this.id = id
        this.title = title
        this.photo = photo
        this.datePosted = datePosted
        this.member = member
        this.category = category
        this.description = description
        this.itemCondition = itemCondition
        this.statusListing = statusListing
        this.comments = comments
    }




}
