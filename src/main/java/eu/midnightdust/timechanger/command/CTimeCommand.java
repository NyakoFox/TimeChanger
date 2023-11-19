package eu.midnightdust.timechanger.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import eu.midnightdust.timechanger.config.TimeChangerConfig;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.command.argument.TimeArgumentType;
import net.minecraft.text.Text;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class CTimeCommand {

    public static LiteralArgumentBuilder<FabricClientCommandSource> command() {
        return literal("ctime")
                .then(literal("set").then(
                        literal("day").executes((context -> setTime(context.getSource(), 1000)))).then(
                        literal("noon").executes((context -> setTime(context.getSource(), 6000)))).then(
                        literal("night").executes((context -> setTime(context.getSource(), 13000)))).then(
                        literal("midnight").executes((context -> setTime(context.getSource(), 18000)))).then(
                        literal("reset").executes((context -> setTime(context.getSource(), -1)))).then(
                        argument("time", TimeArgumentType.time()).executes((context -> setTime(context.getSource(), IntegerArgumentType.getInteger(context, "time")))))
                );
    }

    private static int setTime(FabricClientCommandSource source, int time) {
        TimeChangerConfig.custom_time = time;
        TimeChangerConfig.write("timechanger");

        source.sendFeedback(Text.translatable("command.timechanger.ctime.success").append(time >= 0 ? String.valueOf(time) : "disabled"));
        return 1;
    }

}