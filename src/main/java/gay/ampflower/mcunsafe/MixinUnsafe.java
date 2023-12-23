package gay.ampflower.mcunsafe;

import net.gudenau.minecraft.asm.impl.ReflectionHelper;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.transformer.ClassInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * @author Ampflower
 * @since ${version}
 **/
public class MixinUnsafe implements IMixinConfigPlugin {
    static {
        try {
            var $cache = ReflectionHelper.findStaticGetter(ClassInfo.class, "cache", Map.class);
            var oldCache = (Map<String, ClassInfo>) $cache.invoke();
            var newCache = new WeakHashMap<>(oldCache);
            ReflectionHelper.findStaticSetter(ClassInfo.class, "cache", Map.class).invoke(newCache);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @Override
    public void onLoad(String mixinPackage) {

    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return false;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}
