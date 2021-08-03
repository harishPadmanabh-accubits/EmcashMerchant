package com.app.emcashmerchant.data.models


import com.google.gson.annotations.SerializedName


data class AllContactResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("error")
    val error: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
) {
    data class Data(
        @SerializedName("count")
        val count: Int,
        @SerializedName("limit")
        val limit: Int,
        @SerializedName("page")
        val page: Int,
        @SerializedName("rows")
        val rows: ArrayList<Row>,
        @SerializedName("totalPages")
        val totalPages: Int
    ) {
        data class Row(
            @SerializedName("email")
            val email: String,
            @SerializedName("id")
            val id: String,
            @SerializedName("name")
            val name: String,
            @SerializedName("phoneNumber")
            val phoneNumber: String,
            @SerializedName("ppp")
            val ppp: Int,
            @SerializedName("profileImage")
            val profileImage: String,
            @SerializedName("roleId")
            val roleId: Int
        )
    }


    data class ContactGroupedViewModel(
        val name: String,
        val contacts: List<AllContactResponse.Data.Row>
    ) {

    }

}