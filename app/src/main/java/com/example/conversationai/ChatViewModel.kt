package com.example.conversationai

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatViewModel : ViewModel() {

    val chatResponses = MutableLiveData<List<String>>()

    private val openRouterService = RetrofitInstance.create(OpenRouterService::class.java)

    fun sendMessage(userMessage: String) {
        val chatRequest = ChatRequest(
            model = "mistralai/mistral-7b-instruct",
            messages = listOf(
                Message(role = "user", content = userMessage)
            )
        )

        // Call the API
        openRouterService.sendChat(chatRequest).enqueue(object : Callback<ChatResponse> {
            override fun onResponse(call: Call<ChatResponse>, response: Response<ChatResponse>) {
                if (response.isSuccessful) {
                    // Get the AI's reply
                    val aiReply = response.body()?.choices?.firstOrNull()?.message?.content
                    aiReply?.let {

                        chatResponses.value = listOf(userMessage, it)
                    }
                } else {
                    
                    chatResponses.value = listOf("Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ChatResponse>, t: Throwable) {
                // Handle failure
                chatResponses.value = listOf("Failed: ${t.localizedMessage}")
            }
        })
    }
}
