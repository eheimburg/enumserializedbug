package com.foo;

import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

public class MyCustomCodecFactory implements CodecProvider
{
    @Override
    public <T> Codec<T> get(Class<T> clazz, CodecRegistry registry)
    {
        System.out.println("Called MyCustomCodecFactory.get(" + clazz.getSimpleName() + ")");
        if (clazz.equals(MyEnum.class))
        {
            return (Codec<T>)new MyCustomEnumCodec();
        }
        return null;
    }
}
