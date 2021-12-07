package com.app.emcashmerchant.ui.transactionHistory.model

data class HistoryFilter(
    var mode: String = "0",
    var startDate: String = "",
    var endDate: String = "",
    var status: String = "",
    var type: String = ""
)