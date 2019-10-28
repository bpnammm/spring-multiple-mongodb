package com.multiple.mongo.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.mongo.MongoProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.mongodb.MongoDbFactory
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.SimpleMongoClientDbFactory

@Configuration
@EnableConfigurationProperties
class MultipleMongoConfig {

    @Primary
    @Bean(name = "primaryMongoTemplate")
    MongoTemplate getPrimaryMongoTemplate(@Value('${mongodb.db01.uri}') String uri) {
        return this.getMongoTemplateByProperties(uri)
    }

    @Bean(name = "secondaryMongoTemplate")
    MongoTemplate getSecondaryMongoTemplate(@Value('${mongodb.db02.uri}') String uri) {
        return this.getMongoTemplateByProperties(uri)
    }

    @Bean(name = "thirdMongoTemplate")
    MongoTemplate getThirdMongoTemplate(@Value('${mongodb.db03.uri}') String uri) {
        return this.getMongoTemplateByProperties(uri)
    }

    MongoTemplate getMongoTemplateByProperties(String uri) {
        MongoDbFactory mongoDbFactory = new SimpleMongoClientDbFactory(uri)
        return new MongoTemplate(mongoDbFactory)
    }
}
