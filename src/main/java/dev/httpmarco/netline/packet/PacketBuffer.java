package dev.httpmarco.netline.packet;

import io.netty5.buffer.Buffer;
import io.netty5.buffer.BufferAllocator;
import io.netty5.buffer.DefaultBufferAllocators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

@Log4j2
@AllArgsConstructor
public final class PacketBuffer {

    private static final BufferAllocator BUFFER_ALLOCATOR = DefaultBufferAllocators.offHeapAllocator();

    @Getter
    private final Buffer origin;

    @Contract(" -> new")
    public static @NotNull PacketBuffer allocate() {
        return allocate(0);
    }

    @Contract("_ -> new")
    public static @NotNull PacketBuffer allocate(int i) {
        return new PacketBuffer(BUFFER_ALLOCATOR.allocate(i));
    }

    public void resetBuffer() {
        if (origin.readableBytes() > 0) {
            log.warn("Buffer not empty! Skipping remaining bytes: {}", origin.readableBytes());
            origin.skipReadableBytes(origin.readableBytes());
        }
    }

    @Contract("_ -> this")
    public PacketBuffer writeString(@NotNull String value) {
        var bytes = value.getBytes(StandardCharsets.UTF_8);
        this.origin.writeInt(bytes.length);
        this.origin.writeBytes(bytes);
        return this;
    }

    public @NotNull String readString() {
        return this.origin.readCharSequence(this.origin.readInt(), StandardCharsets.UTF_8).toString();
    }

    public PacketBuffer writeBoolean(Boolean booleanValue) {
        this.origin.writeBoolean(booleanValue);
        return this;
    }

    public boolean readBoolean() {
        return this.origin.readBoolean();
    }

    @Contract("_ -> this")
    public PacketBuffer writeUniqueId(@NotNull UUID uniqueId) {
        this.origin.writeLong(uniqueId.getMostSignificantBits());
        this.origin.writeLong(uniqueId.getLeastSignificantBits());
        return this;
    }

    @Contract(" -> new")
    public @NotNull UUID readUniqueId() {
        return new UUID(this.origin.readLong(), this.origin.readLong());
    }

    public PacketBuffer writeInt(int value) {
        this.origin.writeInt(value);
        return this;
    }

    public int readInt() {
        return this.origin.readInt();
    }

    @Contract("_ -> this")
    public PacketBuffer writeEnum(@NotNull Enum<?> value) {
        this.origin.writeInt(value.ordinal());
        return this;
    }

    public <T extends Enum<?>> T readEnum(@NotNull Class<T> clazz) {
        return clazz.getEnumConstants()[this.origin.readInt()];
    }


    public <T> void writeList(@NotNull List<T> list, BiConsumer<PacketBuffer, T> consumer) {
        this.writeInt(list.size());

        list.forEach(o -> consumer.accept(this, o));
    }

    public <T> List<T> readList(List<T> list, Supplier<T> supplier) {
        var size = this.readInt();

        for (int i = 0; i < size; i++) {
            list.add(supplier.get());
        }

        return list;
    }

    public void writeBuffer(@NotNull PacketBuffer buffer) {
        this.writeInt(buffer.getOrigin().readableBytes());
        this.writeBytes(buffer.getOrigin());
    }

    public PacketBuffer writeLong(long value) {
        this.origin.writeLong(value);
        return this;
    }

    public long readLong() {
        return this.origin.readLong();
    }

    public PacketBuffer writeFloat(float value) {
        this.origin.writeFloat(value);
        return this;
    }

    public float readFloat() {
        return this.origin.readFloat();
    }

    public PacketBuffer writeDouble(double value) {
        this.origin.writeDouble(value);
        return this;
    }

    public double readDouble() {
        return this.origin.readDouble();
    }

    public short readShort() {
        return this.origin.readShort();
    }

    public PacketBuffer writeShort(short value) {
        this.origin.writeShort(value);
        return this;
    }

    public PacketBuffer writeByte(byte value) {
        this.origin.writeByte(value);
        return this;
    }

    public byte readByte() {
        return this.origin.readByte();
    }

    public PacketBuffer writeBytes(Buffer bytes) {
        this.origin.writeBytes(bytes);
        return this;
    }

    @Contract("_ -> this")
    public PacketBuffer writeBytes(byte @NotNull [] bytes) {

        this.origin.writeInt(bytes.length);

        for (byte b : bytes) {
            this.origin.writeByte(b);
        }
        return this;
    }

    public byte @NotNull [] readBytes() {
        var elements = new byte[this.origin.readInt()];

        for (int i = 0; i < elements.length; i++) {
            elements[i] = this.origin.readByte();
        }
        return elements;
    }
}
