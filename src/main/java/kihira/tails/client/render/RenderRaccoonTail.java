/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Zoe Lee (Kihira)
 *
 * See LICENSE for full License
 */

package kihira.tails.client.render;

import kihira.tails.client.model.ModelRaccoonTail;
import kihira.tails.common.TailInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.opengl.GL11;

public class RenderRaccoonTail extends RenderTail {

	private String[] skinNames = {"racoonTail"};
	
    private ModelRaccoonTail modelRaccoonTail = new ModelRaccoonTail();

    public RenderRaccoonTail() {
        super("raccoon");
    }

    @Override
    public void doRender(EntityLivingBase entity, TailInfo info, float partialTicks) {
        GL11.glPushMatrix();
        Minecraft.getMinecraft().renderEngine.bindTexture(info.getTexture());
        if (!entity.isSneaking()) GL11.glTranslatef(0F, 0.65F, 0.1F);
        else GL11.glTranslatef(0F, 0.55F, 0.4F);
        GL11.glScalef(0.8F, 0.8F, 0.8F);
        this.modelRaccoonTail.render(entity, info.subid, partialTicks);
        GL11.glPopMatrix();
    }
    
    @Override
	public String[] getTextureNames(int subid) {
		return skinNames;
	}

    @Override
    public int getAvailableSubTypes() {
        return 0;
    }
}
