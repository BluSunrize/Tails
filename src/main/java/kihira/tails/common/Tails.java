/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Zoe Lee (Kihira)
 *
 * See LICENSE for full License
 */

package kihira.tails.common;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.NetworkCheckHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import kihira.tails.proxy.CommonProxy;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

@Mod(modid = Tails.MOD_ID, name = "Tails", version = "@VERSION@")
public class Tails {

    public static final String MOD_ID = "Tails";
    public static final Logger logger = LogManager.getLogger(MOD_ID);
    public static final SimpleNetworkWrapper networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(MOD_ID);

    public static Configuration configuration;
    public static boolean hasRemote;

    @SidedProxy(clientSide = "kihira.tails.proxy.ClientProxy", serverSide = "kihira.tails.proxy.CommonProxy")
    public static CommonProxy proxy;
    @Mod.Instance
    public static Tails instance;

    /**
     * This is the {@link TailInfo} for the local player
     */
    public static TailInfo localPlayerTailInfo;

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent e) {
        if (FoxLibManager.checkFoxlib()) {
            Tails.proxy.registerHandlers();
            Tails.proxy.registerMessages();

            if (e.getSide().isClient()) {
                Tails.configuration = new Configuration(e.getSuggestedConfigurationFile());
                loadConfig();
            }
        }
    }

    @SubscribeEvent
    public void onConfigChange(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.modID.equals(Tails.MOD_ID)) {
            loadConfig();
        }
    }

    @NetworkCheckHandler
    public boolean checkRemoteVersions(Map<String, String> versions, Side side) {
        if (side.isClient()) {
            if (versions.containsKey(MOD_ID)) hasRemote = true;
        }
        return true;
    }

    public void loadConfig() {
        //Load local player info
        try {
            localPlayerTailInfo = new Gson().fromJson(Tails.configuration.getString("Local Tail Info",
                    Configuration.CATEGORY_GENERAL, "Local Players tail info. Delete to remove tail. Do not try to edit manually", ""), TailInfo.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }

        if (Tails.configuration.hasChanged()) {
            Tails.configuration.save();
        }
    }

    public static void setLocalPlayerTailInfo(TailInfo tailInfo) {
        localPlayerTailInfo = tailInfo;

        Property prop = Tails.configuration.get(Configuration.CATEGORY_GENERAL, "Local Tail Info", "");
        prop.comment = "Local Players tail info. Delete to remove tail. Do not try to edit manually";
        prop.set(new Gson().toJson(localPlayerTailInfo));

        Tails.configuration.save();
    }
}
