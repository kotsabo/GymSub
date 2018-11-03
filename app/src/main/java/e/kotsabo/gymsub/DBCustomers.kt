package e.kotsabo.gymsub

import android.provider.BaseColumns


object DBSubscription {

    /* Inner class that defines the table contents */
    class CustomerEntry : BaseColumns {
        companion object {
            const val TABLE_NAME = "customers"
            const val COLUMN_CUSTOMER_ID = "customerID"
            const val COLUMN_NAME = "name"
            const val COLUMN_SURNAME = "surname"
            const val COLUMN_SUBSCRIPTION_DATE = "subscriptionDate"
            const val COLUMN_MONTHS_SUBSCRIPTION = "subscription"
        }
    }
}
