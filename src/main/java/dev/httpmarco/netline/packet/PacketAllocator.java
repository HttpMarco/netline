package dev.httpmarco.netline.packet;

import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import sun.misc.Unsafe;

import java.lang.reflect.InvocationTargetException;

@Log4j2
@UtilityClass
public final class PacketAllocator {

    private static final Unsafe unsafe;

    static {
        try {
            var field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
        } catch (IllegalAccessException | NoSuchFieldException var1) {
            throw new RuntimeException(var1);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T allocate(Class<T> tClass) {
        try {
            return (T) unsafe.allocateInstance(tClass);
        } catch (InstantiationException e) {
            try {
                // default empty constructor
                return tClass.getConstructor().newInstance();
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                     InvocationTargetException ex) {
                log.error("Cannot create new object: {}", tClass.getSimpleName());
            }
        }
        return null;
    }
}
