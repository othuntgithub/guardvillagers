package tallestegg.guardvillagers;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.UnaryOperator;

import javax.annotation.Nullable;

import org.apache.commons.lang3.reflect.FieldUtils;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.gen.feature.jigsaw.JigsawManager;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.jigsaw.JigsawPatternRegistry;
import net.minecraft.world.gen.feature.jigsaw.JigsawPiece;
import net.minecraft.world.gen.feature.jigsaw.SingleJigsawPiece;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.StructureProcessor;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import tallestegg.guardvillagers.entities.GuardEntity;

public final class GuardSpawner extends JigsawPatternRegistry {
    private static final IStructureProcessorType PROCESSOR = Registry.register(Registry.STRUCTURE_PROCESSOR, new ResourceLocation(GuardVillagers.MODID, "golem"), Processor::new);

    private final JigsawPatternRegistry registry;

    private GuardSpawner(final JigsawPatternRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void register(final JigsawPattern pattern) {
        if (pattern != JigsawPattern.EMPTY) {
            this.registry.register(this.map(pattern));
        }
    }

    private JigsawPattern map(final JigsawPattern pattern) {
        if (pattern.func_214947_b().equals(new ResourceLocation("village/common/iron_golem"))) {
            final List<JigsawPiece> jigsawPieces = ObfuscationReflectionHelper.getPrivateValue(JigsawPattern.class, pattern, "field_214953_e");
            jigsawPieces.set(0, new SingleJigsawPiece("guardvillagers:village/common/iron_golem", ImmutableList.of(new Processor()), JigsawPattern.PlacementBehaviour.RIGID));
        }
        return pattern;
    }
    
    @Override
    public JigsawPattern get(final ResourceLocation name) {
        return this.registry.get(name);
    }

    public static void inject() {
        GuardSpawner.<JigsawManager, JigsawPatternRegistry>replace(JigsawManager.class, null, "field_214891_a", GuardSpawner::new);
    }

    @SuppressWarnings("unchecked")
    private static <U, T> void replace(final Class<U> owner, @Nullable final Object instance, final String name, final UnaryOperator<T> operator) {
        final Field registryField = ObfuscationReflectionHelper.findField(owner, name);
        FieldUtils.removeFinalModifier(registryField);
        try {
            registryField.set(instance, operator.apply((T) registryField.get(instance)));
        } catch (final IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static final class Processor extends StructureProcessor 
    {		
        public Processor() 
        {

        }

        public Processor(final Dynamic<?> dynamic) 
        { 
        	
        }

        @Override
        public Template.EntityInfo processEntity(final IWorldReader world, final BlockPos pos, final Template.EntityInfo rawInfo, final Template.EntityInfo info, final PlacementSettings settings, final Template template) {
        	final CompoundNBT nbt = info.nbt.copy();
	        nbt.putInt("Type",  GuardEntity.getRandomTypeForBiome((IWorld) world, pos));
            return new Template.EntityInfo(info.pos, info.blockPos, nbt);
        }

        @Override
        protected IStructureProcessorType getType() {
            return GuardSpawner.PROCESSOR;
        }

        @Override
        protected <T> Dynamic<T> serialize0(final DynamicOps<T> ops) {
            return new Dynamic<>(ops, ops.emptyMap());
        }
    }
}