package com.example.conversationai

import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.Call

interface OpenRouterService {
    @Headers(
        "Content-Type: application/json",
        "Authorization: Bearer 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"
    )
    @POST("chat/completions")
    fun sendChat(
        @Body request: ChatRequest
    ): Call<ChatResponse>
}
