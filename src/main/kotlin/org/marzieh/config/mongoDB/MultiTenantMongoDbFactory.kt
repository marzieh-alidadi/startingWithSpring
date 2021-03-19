package org.marzieh.config.mongoDB

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoDatabase
import org.springframework.data.mongodb.core.SimpleMongoClientDbFactory

class MultiTenantMongoDbFactory(
    mongoClient: MongoClient,
    dbName: String
) : SimpleMongoClientDbFactory(mongoClient, dbName) {
    override fun getMongoDatabase(): MongoDatabase {
        var tenant = TenantContext.getCurrentTenant()
        if (tenant == null)
            tenant = "Mine"
        return getMongoDatabase(tenant)
    }

}
