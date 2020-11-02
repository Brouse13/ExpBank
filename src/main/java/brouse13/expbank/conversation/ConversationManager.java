package brouse13.expbank.conversation;

import brouse13.expbank.ExpBank;
import brouse13.expbank.translations.Messages;
import org.bukkit.conversations.*;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class ConversationManager {

    private static ExpBank main;
    private static ConversationFactory factory;

    public ConversationManager(ExpBank main) {
        ConversationManager.main = main;
        factory = new ConversationFactory(main)
            .addConversationAbandonedListener(abandonedEvent -> {
                if (abandonedEvent.getContext().getSessionData("player") != null) {
                    Player abandoned = (Player) abandonedEvent.getContext().getSessionData("player");

                    if (abandonedEvent.getContext().getSessionData("deposit") != null) {
                        abandoned.sendMessage(Messages.deposit_success
                                .replace("{0}", abandonedEvent.getContext().getSessionData("deposit").toString()));
                    }else if (abandonedEvent.getContext().getSessionData("withdraw") != null) {
                        abandoned.sendMessage(Messages.withdraw_success
                                .replace("{0}", abandonedEvent.getContext().getSessionData("withdraw").toString()));
                    }else if (abandonedEvent.getContext().getSessionData("give") != null) {
                        Player target = (Player) abandonedEvent.getContext().getSessionData("target");
                        abandoned.sendMessage("§aHas enviado §6{0} §aniveles a §6{1}"
                                .replace("{0}", abandonedEvent.getContext().getSessionData("give").toString())
                                .replace("{1}", target.getName()));
                    }else {
                        abandoned.sendMessage(Messages.transaction_canceled);
                    }

                }
            });
    }

    /**
     * Used to start a Withdraw conversation based on a promp system
     *
     * @param player Player to start with
     */
    public static void convWithdraw(Player player) {
        Map<Object, Object> map = new HashMap<>();
        map.put("player", player);
        Conversation conv = factory
                .withFirstPrompt(new WithdrawXpManager(main))
                .withPrefix(a -> Messages.prefix)
                .withInitialSessionData(map)
                .withLocalEcho(false)
                .withTimeout(60)
                .buildConversation(player);
        if (!player.isConversing()) conv.begin();

    }

    /**
     * Used to start a Deposit conversation based on a promp system
     *
     * @param player Player to start with
     */
    public static void convDeposit(Player player) {
        Map<Object, Object> map = new HashMap<>();
        map.put("player", player);
        Conversation conv = factory
                .withFirstPrompt(new DepositXpManager(main))
                .withPrefix(a -> Messages.prefix)
                .withInitialSessionData(map)
                .withLocalEcho(false)
                .withTimeout(60)
                .buildConversation(player);
        if (!player.isConversing()) conv.begin();
    }

    /**
     * Used to start a GiveXp conversation based on a promp system
     *
     * @param player Player to start with
     * @Param target Player to give the Xp
     */
    public static void convGive(Player player, Player target) {
        Map<Object, Object> map = new HashMap<>();
        map.put("player", player);
        map.put("target", target);
        Conversation conv = factory
                .withFirstPrompt(new GiveXpManager(main))
                .withPrefix(a -> Messages.prefix)
                .withInitialSessionData(map)
                .withLocalEcho(false)
                .withTimeout(60)
                .buildConversation(player);
        if (!player.isConversing()) conv.begin();

    }

}
