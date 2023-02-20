package com.foo;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import java.util.Objects;
import org.bson.types.ObjectId;


@Entity
public class MyEntity {
    @Id
    ObjectId myId;
    public MyEnum myEnum;

    public MyEntity() {
        myId = new ObjectId();
        myEnum = MyEnum.Bananas;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + Objects.hashCode(this.myId);
        hash = 43 * hash + Objects.hashCode(this.myEnum);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final MyEntity other = (MyEntity) obj;
        if (!Objects.equals(this.myId, other.myId))
            return false;
        return this.myEnum == other.myEnum;
    }
}
