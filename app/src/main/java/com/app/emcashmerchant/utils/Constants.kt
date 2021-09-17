package com.app.emcashmerchant.utils

import androidx.security.crypto.MasterKeys


const val BASE_URL : String = "https://emcash-api-dev.devtomaster.com/"//dev
//const val BASE_URL : String = "https://emcash-api-stg.devtomaster.com"
//const val BASE_URL : String = "https://2fe5-117-216-229-80.ngrok.io"
const val BUCKET_URL : String = "https://stemcashmerchantdocstest.blob.core.windows.net"


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
const val KEY_REGISTERED_BUISINESS_NAME = "reg_buisness_name"
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

const val KEY_AMOUNT = "AMOUNT"
const val KEY_DESCRIPTION = "DESCRIPTION"
const val KEY_LONGITUDE = "LONGITUDE"
const val KEY_LATITUDE = "LATITUDE"
const val KEY_SENDER_NUMBER = "SENDER_NUMBER"
const val KEY_SENDER_NAME = "SENDER_NAME"

const val KEY_USERID = "USER_ID"
const val KEY_QRCODE = "QRCODE"
const val KEY_PAGE = "PAGE"
const val KEY_ACTION = "ACTION"
const val KEY_NAME = "NAME"
const val KEY_NUMBER = "NUMBER"
const val KEY_LEVEL_COLOUR = "LEVEL_COLOUR"
const val KEY_ROLE = "ROLE"
const val KEY_DEEPLINK = "DEEPLINK"
const val KEY_TYPE = "TYPE"
const val KEY_PROFLE_IMAGE_LINK = "PROFLE_IMAGE_LINK"
const val KEY_REUPLOAD_TOKEN = "REUPLOAD_TOKEN"
const val KEY_REVIEWSCREEN = "REVIEWSCREEN"

const val KEY_USERLEVEL = "USERLEVEL"
const val KEY_ROLEID = "ROLEID"


const val DESTINATION = "source"
const val SCREEN_CHAT = 200
const val IS_FROM_DEEPLINK = "deeplink_sourced"
const val KEY_USER_ID_FROM_DEEPLINK = "user_id_deeplink"
