package kihira.tails.render;

import kihira.tails.TailInfo;
import kihira.tails.model.ModelRacoonTail;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.opengl.GL11;

public class RenderRacoonTail extends RenderTail {

	private String[] skinNames = {"racoonTail"};
	
    private ModelRacoonTail modelRacoonTail = new ModelRacoonTail();
	//private ResourceLocation tailTexture = new ResourceLocation("tails", "texture/racoonTail.png");

    @Override
    public void render(EntityLivingBase player, TailInfo info) {
        GL11.glPushMatrix();
        Minecraft.getMinecraft().renderEngine.bindTexture(info.texture);
        if (!player.isSneaking()) GL11.glTranslatef(0F, 0.65F, 0.1F);
        else GL11.glTranslatef(0F, 0.55F, 0.4F);
        GL11.glScalef(0.8F, 0.8F, 0.8F);
        this.modelRacoonTail.render(player, info.subid);
        GL11.glPopMatrix();
    }
    
    @Override
	public String[] getTextureNames() {
		return skinNames;
	}
}
