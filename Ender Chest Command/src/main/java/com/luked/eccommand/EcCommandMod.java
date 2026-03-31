package com.luked.eccommand;

import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.command.ServerCommandSource;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.literal;

public class EcCommandMod implements ModInitializer {

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("ec")
                .executes(this::openEnderChest)
            );
dispatcher.register(literal("enderchest")
                .executes(this::openEnderChest)
            );
            dispatcher.register(literal("ender")
                .then(literal("chest")
                    .executes(this::openEnderChest)
                )
            );
        });
    }

    private int openEnderChest(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = context.getSource().getPlayer();
        if (player == null) {
            context.getSource().sendError(Text.literal("This command can only be run by a player."));
            return 0;
        }

        player.openHandledScreen(new SimpleNamedScreenHandlerFactory(
            (syncId, inv, p) -> new GenericContainerScreenHandler(
                ScreenHandlerType.GENERIC_9X3, syncId, inv, player.getEnderChestInventory(), 3
            ),
            Text.translatable("container.enderchest")
        ));

        return 1;
    }
}
