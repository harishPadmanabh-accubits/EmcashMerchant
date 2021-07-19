package com.app.emcashmerchant.data.models

data class DemoNotificationResponse(
    val type: Int,
    val notification :String,
    val time: String
)


data class DummyNotificationModel(
    val date: String,
    val notification: List<DemoNotificationResponse>
) {

}

val dummyNotificationDetails = listOf<DemoNotificationResponse>(
    DemoNotificationResponse(1, "Payment received from Faizal", "9:00 PM"),
    DemoNotificationResponse(2, "Payment received from Raheem", "10:00 PM"),
    DemoNotificationResponse(3, "Payment received from Waseem", "10:00 PM"),
    DemoNotificationResponse(3, "Payment received from Begam", "10:00 PM")

)

val dummyNotificationDetails1 = listOf<DemoNotificationResponse>(
    DemoNotificationResponse(1, "Payment received from Vishnu", "12:00 PM"),
    DemoNotificationResponse(2, "Payment received from Rahul", "1:00 PM")

)

val dummyNotificationDetails2= listOf<DemoNotificationResponse>(
    DemoNotificationResponse(1, "Payment received from Faizal", "12:00 PM")
)

val dummyNotificaitonData = listOf<DummyNotificationModel>(
    DummyNotificationModel("19 May 2021", dummyNotificationDetails),
    DummyNotificationModel("20 May 2021", dummyNotificationDetails1),
    DummyNotificationModel("21 May 2021", dummyNotificationDetails2),
    DummyNotificationModel("22 May 2021", dummyNotificationDetails)


)