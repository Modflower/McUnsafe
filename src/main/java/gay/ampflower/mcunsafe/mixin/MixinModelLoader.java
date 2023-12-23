package gay.ampflower.mcunsafe.mixin;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.AffineTransformation;
import org.apache.commons.lang3.tuple.Triple;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

import java.util.Map;
import java.util.Set;

/**
 * @author Ampflower
 * @since ${version}
 **/
@Mixin(ModelLoader.class)
public class MixinModelLoader {
    @Shadow
    @Final
    @Mutable
    private Set<Identifier> modelsToLoad;

    @Shadow
    @Final
    @Mutable
    private Map<Identifier, UnbakedModel> unbakedModels;

    @Shadow
    @Final
    @Mutable
    private Map<Triple<Identifier, AffineTransformation, Boolean>, BakedModel> bakedModelCache;

    @Shadow
    @Final
    @Mutable
    private Map<Identifier, UnbakedModel> modelsToBake;

    @Shadow
    @Final
    @Mutable
    private Map<Identifier, BakedModel> bakedModels;

    @Redirect(method = "<init>", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/model/ModelLoader;modelsToLoad:Ljava/util/Set;"))
    private void mcunsafe$redirectModelsToLoad(ModelLoader self, Set<?> set) {
        modelsToLoad = new ObjectOpenHashSet<>();
    }

    @Redirect(method = "<init>",
            at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/model/ModelLoader;unbakedModels:Ljava/util/Map;"),
            slice = @Slice(id = "primary", from = @At("HEAD"),
                    to = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiler/Profiler;push(Ljava/lang/String;)V", ordinal = 0)))
    private void mcunsafe$redirectUnbakedModels(ModelLoader self, Map<?, ?> map) {
        unbakedModels = new Object2ObjectOpenHashMap<>(1024);
    }

    @Redirect(method = "<init>",
            at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/model/ModelLoader;bakedModelCache:Ljava/util/Map;"),
            slice = @Slice(id = "primary", from = @At("HEAD"),
                    to = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiler/Profiler;push(Ljava/lang/String;)V", ordinal = 0)))
    private void mcunsafe$redirectBakedModelCache(ModelLoader self, Map<?, ?> map) {
        bakedModelCache = new Object2ObjectOpenHashMap<>(1024);
    }

    @Redirect(method = "<init>",
            at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/model/ModelLoader;modelsToBake:Ljava/util/Map;"),
            slice = @Slice(id = "primary", from = @At("HEAD"),
                    to = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiler/Profiler;push(Ljava/lang/String;)V", ordinal = 0)))
    private void mcunsafe$redirectModelsToBake(ModelLoader self, Map<?, ?> map) {
        modelsToBake = new Object2ObjectOpenHashMap<>(64);
    }

    @Redirect(method = "<init>",
            at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/model/ModelLoader;bakedModels:Ljava/util/Map;"),
            slice = @Slice(id = "primary", from = @At("HEAD"),
                    to = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiler/Profiler;push(Ljava/lang/String;)V", ordinal = 0)))
    private void mcunsafe$redirectBakedModels(ModelLoader self, Map<?, ?> map) {
        bakedModels = new Object2ObjectOpenHashMap<>(64);
    }

}
