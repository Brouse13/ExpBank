package brouse13.expbank.conversation;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GiveXpManager implements Prompt {
    JavaPlugin main;

    public GiveXpManager(JavaPlugin main) {
        this.main = main;
    }

    @NotNull
    public String getPromptText(@NotNull ConversationContext conversationContext) {
        return null;
    }

    public boolean blocksForInput(@NotNull ConversationContext conversationContext) {
        return false;
    }

    @Nullable
    public Prompt acceptInput(@NotNull ConversationContext conversationContext, @Nullable String s) {
        return null;
    }
}
