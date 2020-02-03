package com.danicktakam.demo3andm.services

interface AsyncResponseCallback {
    fun onResponse(isSuccess: Boolean, call: String)
}