package com.foo;

import com.antwerkz.bottlerocket.BottleRocket;
import com.antwerkz.bottlerocket.BottleRocketTest;
import com.github.zafarkhaja.semver.Version;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import dev.morphia.mapping.MapperOptions;
import dev.morphia.query.filters.Filters;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Assert;
import org.junit.Test;

public class ReproducerTest extends BottleRocketTest {
    private Datastore datastore;

    public ReproducerTest() {
        MongoClient mongo = getMongoClient();
        MongoDatabase database = getDatabase();
        database.drop();

        MapperOptions options = MapperOptions.builder()
            .codecProvider(new MyCustomCodecFactory())
            .build();

        datastore = Morphia.createDatastore(mongo, getDatabase().getName(), options);
    }

    @NotNull
    @Override
    public String databaseName() {
        return "morphia_repro";
    }

    @Nullable
    @Override
    public Version version() {
        return BottleRocket.DEFAULT_VERSION;
    }

    @Test
    public void reproduce() {
        datastore.getMapper().map(MyEntity.class, MyEnum.class);

        MyEntity first = new MyEntity();
        datastore.save(first);
        
        MyEntity second = datastore.find(MyEntity.class).filter(Filters.eq("_id", first.myId)).first();
        
        Assert.assertEquals(first, second);
        Assert.assertTrue(MyCustomEnumCodec.g_wasEncodeCalled);
        Assert.assertTrue(MyCustomEnumCodec.g_wasDecodeCalled);
    }

}
