package com.app.emcashmerchant.utils

object DeepLinkRoutes {
    const val ROUTE_REUPLOAD: String = "ReUpload"
    const val ROUTE_PAYMENT_HISTORY: String = "paymentHistory"

}


object AccountType {
    const val ACCOUNT_VERIFIED : Int = 1
    const val ACCOUNT_IN_REVIEW: Int = 2
    const val ACCOUNT_REJECTED: Int = 3
    const val ACCOUNT_BLOCKED: Int = 4

}

object NotificationTYPE{
    const val PENDING_NOTIFICATION: Int = 1
    const val SUCCESS_NOTIFICATION: Int = 2
    const val REJECTED_NOTIFICATION: Int = 3
    const val MERCHANT_DOCUMENTS_VERIFIED_NOTIFICATION: Int = 4
    const val MERCHANT_DOCUMENTS_REJECTED_NOTIFICATION: Int = 5
    const val REGISTRATION_COMPLETED: Int = 6
    const val OTHER_NOTIFICATION: Int = 7


}