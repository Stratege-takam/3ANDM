package com.danicktakam.demo3andm.services

import com.danicktakam.demo3andm.db.entity.Country

interface ICountryService {
    fun getAllCountry(): ArrayList<Country>
}