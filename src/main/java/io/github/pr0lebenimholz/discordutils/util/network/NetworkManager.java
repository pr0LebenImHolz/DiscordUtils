package io.github.pr0lebenimholz.discordutils.util.network;

import io.github.pr0lebenimholz.discordutils.util.Constants;
import io.github.pr0lebenimholz.discordutils.util.NotInitializedException;
import io.github.pr0lebenimholz.discordutils.util.minecraft.MsgFallbackTranslation;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkManager {

    private static SimpleNetworkWrapper channel;

    public static void init() {
        channel = NetworkRegistry.INSTANCE.newSimpleChannel(Constants.MODID);
        int clientPackagesId = 0;
        int serverPackagesId = 0;

        channel.registerMessage(MsgFallbackTranslation.MsgFallbackTranslationHandler.class, MsgFallbackTranslation.class, clientPackagesId++, Side.CLIENT);
    }

    /**
     * Returns the network channel for this Mod.
     *
     * Dont access before preInit!
     *
     * @return
     */
    public static SimpleNetworkWrapper getNetworkChannel() {
        if (channel == null) throw new NotInitializedException("Networkchannel is initialized in init");
        return channel;
    }
}
