package com.springsamurais.toyswap.model

data class CommentRequest(
    var text: String,
    var commenterId: Long,
    var listingId: Long
)
