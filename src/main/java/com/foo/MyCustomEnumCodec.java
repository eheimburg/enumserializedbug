package com.foo;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;


public class MyCustomEnumCodec implements Codec<MyEnum>
{
    static boolean g_wasEncodeCalled = false;
    static boolean g_wasDecodeCalled = false;

    @Override
    public MyEnum decode(BsonReader reader, DecoderContext decoderContext)
    {
        g_wasDecodeCalled = true;
        String s = reader.readString();
        System.out.println("Decoding " + s + " into a MyEnum");
        return Enum.valueOf(getEncoderClass(), reader.readString());
    }

    @Override
    public void encode(BsonWriter writer, MyEnum value, EncoderContext encoderContext)
    {
        g_wasEncodeCalled = true;
        System.out.println("Encoding MyEnum value " + value);
        writer.writeString(value.name());
    }

    @Override
    public Class<MyEnum> getEncoderClass()
    {
        return MyEnum.class;
    }
}
