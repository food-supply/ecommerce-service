package com.se.ecommerce_service.helper;

import java.nio.ByteBuffer;
import java.util.UUID;

import com.fasterxml.uuid.Generators;

public class UUIDUtil {
    public static UUID generateUuidV7() {
        return Generators.timeBasedEpochRandomGenerator().generate();
    }

    public static byte[] uuidToBytes(UUID uuid) {
        ByteBuffer buffer = ByteBuffer.wrap(new byte[16]);
        buffer.putLong(uuid.getMostSignificantBits());
        buffer.putLong(uuid.getLeastSignificantBits());
        return buffer.array();
    }

    public static UUID bytesToUuid(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        long high = buffer.getLong();
        long low = buffer.getLong();
        return new UUID(high, low);
    }
}
