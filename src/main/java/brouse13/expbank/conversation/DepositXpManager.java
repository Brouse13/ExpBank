package brouse13.expbank.conversation;

import brouse13.expbank.configuration.ConfigurationManager;
import brouse13.expbank.translations.Messages;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.NumericPrompt;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DepositXpManager extends NumericPrompt {
    JavaPlugin main;

    public DepositXpManager(JavaPlugin main) {
        this.main = main;
    }

    @NotNull
    public String getPromptText(@NotNull ConversationContext conversationContext) {
        return Messages.exp_deposit;
    }

    @Override
    protected String getInputNotNumericText(@NotNull ConversationContext context, @NotNull String input) {
        return Messages.not_number.replace("{0}", input);
    }

    @Override
    protected String getFailedValidationText(@NotNull ConversationContext context, @NotNull Number input) {
        return Messages.not_enough_exp.replace("{0}", input.intValue()+ "");
    }

    protected boolean isInputValid(@NotNull ConversationContext context, @NotNull String input) {
        Player player = (Player)context.getSessionData("player");
        int playerLevel = player.getLevel();

        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return false;
        }

        return playerLevel >= Integer.parseInt(input);
    }

    @Nullable
    protected Prompt acceptValidatedInput(@NotNull ConversationContext context, @NotNull Number number) {
        Player player = (Player) context.getSessionData("player");

        ConfigurationManager.getConfiguration().accountPlayerInsert(player, number.floatValue());
        player.setLevel(player.getLevel()- number.intValue());
        context.setSessionData("deposit", number);
        return END_OF_CONVERSATION;
    }

}
