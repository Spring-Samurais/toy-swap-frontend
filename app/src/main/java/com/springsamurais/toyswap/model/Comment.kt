package com.springsamurais.toyswap.model

import java.util.Date

class Comment {

    private var commentId: Long? = null;
    private var text: String? = null;
    private var commenter: Member? = null;
    private var listing: Listing? = null;
    private var dateCommented: Date? = null;

    constructor()
    constructor(commentId: Long?, text: String?, commenter: Member?, listing: Listing?, dateCommented: Date?) {
        this.commentId = commentId
        this.text = text
        this.commenter = commenter
        this.listing = listing
        this.dateCommented = dateCommented
    }


}
