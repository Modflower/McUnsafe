package gay.ampflower.mcunsafe.mixin;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ampflower
 * @since 0.1.0
 **/
@Mixin(NbtCompound.class)
public class MixinNbtCompound {
    /**
     * Removes now unnecessary allocation of the map.
     */
    @Redirect(method = "<init>()V", at = @At(value = "INVOKE", target = "Lcom/google/common/collect/Maps;newHashMap()Ljava/util/HashMap;"))
    private static HashMap<?, ?> mcunsafe$nullifier() {
        return null;
    }

    /**
     * Allocates FastUtil hashmap with a preallocation of 4 rather than 16.
     */
    @ModifyArg(method = "<init>()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/NbtCompound;<init>(Ljava/util/Map;)V"))
    private static Map<?, ?> mcunsafe$unnullifier(Map<?, ?> map) {
        if (map != null) {
            throw new AssertionError();
        }
        return new Object2ObjectOpenHashMap<>(4);
    }
}
