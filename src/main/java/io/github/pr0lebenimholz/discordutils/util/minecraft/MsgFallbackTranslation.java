package io.github.pr0lebenimholz.discordutils.util.minecraft;

import io.github.pr0lebenimholz.discordutils.util.data.ConfigHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

// TODO: 28.03.22 impl https://mcforge.readthedocs.io/en/1.12.x/networking/simpleimpl/
public class MsgFallbackTranslation implements IMessage {

    private ConfigHandler.FallbackEntry<String, String> message;

    public MsgFallbackTranslation() {}

    public MsgFallbackTranslation(ConfigHandler.FallbackEntry<String, String> message) {
        this.message = message;
    }

    public ConfigHandler.FallbackEntry<String, String> getMessage() {
        return this.message;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        // TODO: 29.03.22 write buf to MsgFallbackTranslation#message
    }

    @Override
    public void toBytes(ByteBuf buf) {
        // TODO: 29.03.22 write MsgFallbackTranslation#message to buf
    }

    public static class MsgFallbackTranslationHandler implements IMessageHandler<MsgFallbackTranslation, IMessage> {

        @Override
        public IMessage onMessage(MsgFallbackTranslation message, MessageContext ctx) {
            EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
            ConfigHandler.FallbackEntry<String, String> fallbackEntry = message.getMessage();
            // packets are handled on the network thread; create new one to send a message to the player
            Minecraft.getMinecraft().addScheduledTask(() -> {
                // get client language key
                String langKey = Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage().getLanguageCode();
                String translatedMessage = fallbackEntry.get(langKey);
                serverPlayer.sendMessage(new TextComponentString(translatedMessage));
            });
            return null;
        }
    }
}
