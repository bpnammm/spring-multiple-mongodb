package com.multiple.mongo.controller

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

    final String DESTINATION_NAME_01 = "masoffer.test-message-01"
    final String DESTINATION_NAME_02 = "masoffer.test-message-02"

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
        jmsTemplate.convertAndSend(DESTINATION_NAME_01, message)
        jmsTemplate.convertAndSend(DESTINATION_NAME_02, message)
        return "Success!"
    }

    @GetMapping("/message")
    String dequeueMessage() {
        return jmsTemplate.receiveAndConvert(DESTINATION_NAME_01)
    }
}
