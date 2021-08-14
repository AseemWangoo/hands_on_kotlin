@file:JvmName("Constants")

package com.aseemwangoo.handsonkotlin

const val DELAY_TIME_MILLIS: Long = 3000
const val CHANNEL_ID = "VERBOSE_NOTIFICATION"

@JvmField val NOTIFICATION_TITLE: CharSequence = "WorkRequest - OnDemandBackup"
const val NOTIFICATION_ID = 1

@JvmField val VERBOSE_NOTIFICATION_CHANNEL_NAME: CharSequence =
    "Verbose WorkManager Notifications"
const val VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION =
    "Shows notifications whenever work starts"


const val KEY_ONDEMANDWORKER_RESP = "KEY_ONDEMANDWORKER_RESP"
const val KEY_FILEWORKER_RESP = "KEY_FILEWORKER_RESP"
const val KEY_PERIODICWORKER_RESP = "KEY_PERIODICWORKER_RESP"

const val ONDEMAND_BACKUP_WORK_NAME = "on_demand_backup_work"
const val PERIODIC_BACKUP_WORK_NAME = "periodic_backup_work"

const val OUTPUT_PATH = "my_app_backups"

const val BACKUP_FILE_NAME = "backup_data.txt"

const val TAG_BACKUP = "TAG_BACKUP"
const val TAG_FILE = "TAG_FILE"
const val TAG_PERIODIC_BACKUP = "TAG_PERIODIC_BACKUP"

const val TEST_INPUT_TAG = "InputTodo Tag"
const val TITLE_MAIN = "My ToDo List"
const val ADD_TODO = "Add Todo"
const val SAVE_TODO = "Save Todo"

