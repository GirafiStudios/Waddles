package waddles.client.renderer;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import waddles.Reference;
import waddles.client.model.ModelPenguin;
import waddles.entity.EntityPenguin;

@SideOnly(Side.CLIENT)
public class RenderPenguin extends RenderLiving<EntityPenguin> {
    private static final ResourceLocation PENGUIN_TEXTURE = new ResourceLocation(Reference.MOD_ID + ":textures/entity/penguin.png");

    public RenderPenguin(RenderManager renderManager) {
        super(renderManager, new ModelPenguin(), 0.7F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityPenguin entityPenguin) {
        return PENGUIN_TEXTURE;
    }
}