package com.test.shaadi.utils

object AppConstants {
    interface NetworkConstant {
        companion object {
            const val BASE_URL = "https://randomuser.me/"
        }
    }

    interface TypeConstants {
        companion object {
            const val ACTION_TYPE_ACCEPT = "Accept"
            const val ACTION_TYPE_DECLINE = "Decline"
        }
    }

    interface UserStatus {
        companion object {
            const val STATUS_ACCEPTED = "accepted"
            const val STATUS_DECLINED = "declined"
            const val STATUS_NOT_DEFINED = "notDefined"
        }
    }
}