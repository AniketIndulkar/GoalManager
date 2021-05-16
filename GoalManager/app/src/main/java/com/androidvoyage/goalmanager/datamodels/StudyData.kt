package com.androidvoyage.goalmanager.datamodels

/*
* type
* 1 - Heading
* 2- Paragraph
* 3- Image
* 4- List
* 5- Quote
* */
data class StudyData(var type : Int , var stringData : String ="", val imageData : String = "", var listData : List<String>? = null, var quoteData : String = "")