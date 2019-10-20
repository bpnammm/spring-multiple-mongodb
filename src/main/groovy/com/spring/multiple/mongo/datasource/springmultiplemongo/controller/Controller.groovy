package com.spring.multiple.mongo.datasource.springmultiplemongo.controller

import org.bson.Document
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.jms.core.JmsTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("")
class Controller {

    final String DESTINATION_NAME = "masoffer.test-message"

    @Autowired
    @Qualifier(value = "primaryMongoTemplate")
    MongoTemplate primaryMongoTemplate

    @Autowired
    @Qualifier(value = "secondaryMongoTemplate")
    MongoTemplate secondaryMongoTemplate

    @Autowired
    JmsTemplate jmsTemplate

    @PostMapping("/create")
    String create(@RequestBody Document document) {
        primaryMongoTemplate.save(document, "collection")
        secondaryMongoTemplate.save(document, "new_collection")
        return document.toJson()
    }

    @PostMapping("/message")
    String enqueueMessage(@RequestBody String message) {
        return jmsTemplate.convertAndSend(DESTINATION_NAME, message)
    }

    @GetMapping("/message")
    String dequeueMessage() {
        return jmsTemplate.receiveAndConvert(DESTINATION_NAME)
    }
}
