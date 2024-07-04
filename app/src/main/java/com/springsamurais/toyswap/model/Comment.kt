package com.springsamurais.toyswap.model

import java.util.Date

data class Comment(
    var commentId: Long?,
    var text: String? ,
    var commenter: Member?,
    var listing: Listing?,
    var dateCommented: Date?
)
