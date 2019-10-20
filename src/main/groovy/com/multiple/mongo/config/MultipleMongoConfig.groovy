package com.multiple.mongo.config

import org.springframework.boot.autoconfigure.mongo.MongoProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.mongodb.MongoDbFactory
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.SimpleMongoClientDbFactory

@Configuration
class MultipleMongoConfig {

    @Primary
    @Bean(name = "primary")
    @ConfigurationProperties(prefix = "mongodb.db01")
    MongoProperties getPrimaryProperties() {
        return new MongoProperties()
    }

    @Primary
    @Bean(name = "primaryMongoTemplate")
    MongoTemplate getPrimaryMongoTemplate() {
        return this.getMongoTemplateByProperties(getPrimaryProperties())
    }

    @Bean(name = "secondary")
    @ConfigurationProperties(prefix = "mongodb.db02")
    MongoProperties getSecondaryProperties() {
        return new MongoProperties()
    }

    @Bean(name = "secondaryMongoTemplate")
    MongoTemplate getSecondaryMongoTemplate() {
        return this.getMongoTemplateByProperties(getSecondaryProperties())
    }

    @Bean(name = "third")
    @ConfigurationProperties(prefix = "mongodb.db03")
    MongoProperties getThirdProperties() {
        return new MongoProperties()
    }

    @Bean(name = "thirdMongoTemplate")
    MongoTemplate getThirdMongoTemplate() {
        return this.getMongoTemplateByProperties(getThirdProperties())
    }

    MongoTemplate getMongoTemplateByProperties(MongoProperties mongoProperties) {
        MongoDbFactory mongoDbFactory = new SimpleMongoClientDbFactory(mongoProperties.getUri())
        return new MongoTemplate(mongoDbFactory)
    }
}
