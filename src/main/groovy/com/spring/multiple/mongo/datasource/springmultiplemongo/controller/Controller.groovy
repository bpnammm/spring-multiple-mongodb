package com.spring.multiple.mongo.datasource.springmultiplemongo.controller

import org.bson.Document
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("")
class Controller {

    @Autowired
    @Qualifier(value = "primaryMongoTemplate")
    MongoTemplate primaryMongoTemplate

    @Autowired
    @Qualifier(value = "secondaryMongoTemplate")
    MongoTemplate secondaryMongoTemplate

    @PostMapping("/create")
    String create(@RequestBody Document document){
        primaryMongoTemplate.save(document, "collection")
        secondaryMongoTemplate.save(document, "new_collection")
        return document.toJson()
    }
}
