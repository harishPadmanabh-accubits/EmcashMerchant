package com.app.emcashmerchant.utils

import androidx.security.crypto.MasterKeys


const val BASE_URL : String = "https://emcash-api-dev.devtomaster.com/"
var masterKeyAlias: String = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

const val DATABASE_Name = "app_master_db"
const val READ_TIMEOUT = 30  //in seconds
const val WRITE_TIMEOUT = 15  //in seconds
const val REQUEST_CODE_PICK_IMAGE_TRADEFILE = 101
const val REQUEST_CODE_PICK_IMAGE_COMM = 102
const val REQUEST_CODE_PICK_IMAGE_BANK = 103
const val REQUEST_CODE_PICK_IMAGE_PROFILE = 104



const val KEY_REF_ID = "ref_id"
const val KEY_BUISINESS_NAME = "buisness_name"
const val KEY_CONTACT_PERSON = "contact_person_name"
const val KEY_TRADE_LICENSE_NUM = "license_num"
const val KEY_LICENSE_AUTHORITY = "license_authority"
const val KEY_SERVICE_DESC = "service_desc"
const val KEY_PASSWORD = "password"
const val KEY_CONFIRM_PASSWORD = "confirm_password"
const val KEY_PIN = "pin"
const val KEY_CONFIRM_PIN = "confirm_pin"
const val KEY_QUESTION_ID_1 = "question1"
const val KEY_QUESTION_ID_2 = "question2"
const val KEY_ANSWER_1 = "answer1"
const val KEY_ANSWER_2 = "answer2"
