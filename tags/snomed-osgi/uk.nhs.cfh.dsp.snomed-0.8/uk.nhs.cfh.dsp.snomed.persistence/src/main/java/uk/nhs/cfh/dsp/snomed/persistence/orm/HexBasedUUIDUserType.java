/**
 * Crown Copyright (C) 2008 - 2011
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.nhs.cfh.dsp.snomed.persistence.orm;

import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.LongBuffer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.UUID;

/**
 * A custom {@link org.hibernate.usertype.UserType} for storing {@link java.util.UUID}s based on
 * example shown at http://rcrezende.blogspot.com/2010/04/persistent-uuid-on-hibernate-using-16.html
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Nov 28, 2010 at 8:54:50 PM
 */
public class HexBasedUUIDUserType implements UserType {

    private static final String CAST_EXCEPTION_TEXT = " cannot be cast to a java.util.UUID.";

    public static byte[] UUID2ByteArray(UUID uuid) {
        long msb = uuid.getMostSignificantBits();
        long lsb = uuid.getLeastSignificantBits();
        byte[] bArray = new byte[16];
        ByteBuffer bBuffer = ByteBuffer.wrap(bArray);
        LongBuffer lBuffer = bBuffer.asLongBuffer();
        lBuffer.put(0, msb);
        lBuffer.put(1, lsb);
        return bArray;
    }

    public static UUID byteArray2UUID(byte[] bArray) {
        ByteBuffer bBuffer = ByteBuffer.wrap(bArray);
        LongBuffer lBuffer = bBuffer.asLongBuffer();
        return new UUID(lBuffer.get(0), lBuffer.get(1));
    }

    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        if (!byte[].class.isAssignableFrom(cached.getClass())) {
            return null;
        }
        return byteArray2UUID((byte[]) cached);
    }

    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }

    public Serializable disassemble(Object value) throws HibernateException {
        return UUID2ByteArray((UUID) value);
    }

    public boolean equals(Object x, Object y) throws HibernateException {
        if (x == y)
            return true;

        if (!UUID.class.isAssignableFrom(x.getClass())) {
            throw new HibernateException(x.getClass().toString() + CAST_EXCEPTION_TEXT);
        } else if (!UUID.class.isAssignableFrom(y.getClass())) {
            throw new HibernateException(y.getClass().toString() + CAST_EXCEPTION_TEXT);
        }

        UUID a = (UUID) x;
        UUID b = (UUID) y;

        return a.equals(b);
    }

    public int hashCode(Object x) throws HibernateException {
        if (!UUID.class.isAssignableFrom(x.getClass())) throw new HibernateException(x.getClass().toString() + CAST_EXCEPTION_TEXT);

        return x.hashCode();
    }

    public boolean isMutable() {
        return false;
    }

    public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException {
        byte[] value = rs.getBytes(names[0]);
        return (value == null) ? null : byteArray2UUID(value);
    }

    public void nullSafeSet(PreparedStatement st, Object value, int index) throws HibernateException, SQLException {
        if (value == null) {
            st.setNull(index, Types.VARBINARY);
            return;
        }

        if (!UUID.class.isAssignableFrom(value.getClass())) throw new HibernateException(value.getClass().toString() + CAST_EXCEPTION_TEXT);

        st.setBytes(index, UUID2ByteArray(((UUID) value)));
    }

    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }

    @SuppressWarnings("unchecked")
    public Class returnedClass() {
        return UUID.class;
    }

    public int[] sqlTypes() {
        return new int[] { Types.VARBINARY };
    }
}