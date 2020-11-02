package brouse13.expbank.translations;

public class Messages {

    public static String prefix, reload_config;

    public static String only_players, permission_denied;

    public static String sign_created, sign_removed, sign_invalid, load_duplicate;

    public static String exp_withdraw, exp_deposit, deposit_success, withdraw_success, not_enough_exp, not_number, transaction_canceled, exp_given_to, exp_removed_to;

    public static String account_created, account_renamed, account_balance, player_not_exist;

    public static String command_info, command_give, command_remove, command_balance, command_reload;

    public Messages() {
        prefix = MessagesManager.getFromMessages("messages.prefix", "&a[&bExpBank&a] ");
        reload_config = prefix+ MessagesManager.getFromMessages("messages.reload_config", "&aAll files were reloaded ");//este

        only_players = prefix+ MessagesManager.getFromMessages("messages.only_players", "&cThis command is only available to players");
        permission_denied = prefix+ MessagesManager.getFromMessages("messages.permission_denied", "&cYou don't have permission to perform this action");

        sign_created = prefix+ MessagesManager.getFromMessages("messages.sign_created", "&aSign created successfully");
        sign_removed = prefix+ MessagesManager.getFromMessages("messages.sign_removed", "&aSign removed successfully");
        sign_invalid = prefix+ MessagesManager.getFromMessages("messages.sign_invalid", "&cInvalid sign type");
        load_duplicate = prefix+ MessagesManager.getFromMessages("messages.load_duplicate", "&cTrying to load a duplicate sign");

        exp_withdraw = MessagesManager.getFromMessages("messages.deposit_withdraw", "&aHow much xp do you want to withdraw?");
        exp_deposit = MessagesManager.getFromMessages("messages.exp_deposit", "&aHow much xp do you want to deposit?");
        deposit_success = MessagesManager.getFromMessages("messages.deposit_success", "&aYou have deposit &6{0} &axp levels successfully");
        withdraw_success = MessagesManager.getFromMessages("messages.withdraw_success", "&aYou have withdrawed &6{0} &axp levels successfully");
        not_enough_exp = MessagesManager.getFromMessages("messages.not_enought_exp", "&cSorry but you don't have &6{0} &cxp levels");
        not_number = MessagesManager.getFromMessages("messages.not_number", "&cDo you think &6{0} &cit's a number?");
        transaction_canceled = prefix+ MessagesManager.getFromMessages("messages.transaction_canceled", "&cYou haven't typed any number");
        exp_given_to = MessagesManager.getFromMessages("messages.exp_given_to", "&aYou have successfully given &6{0} &aexp to &6{1}");
        exp_removed_to = MessagesManager.getFromMessages("messages.exp_removed_to", "&aYou have successfully removed &6{0} &aexp to &6{1}");

        account_created = prefix+ MessagesManager.getFromMessages("messages.account_created", "&aYour account has been successfully created");
        account_renamed = prefix+ MessagesManager.getFromMessages("messages.account_renamed", "&aYour name has been updated");
        account_balance = prefix+ MessagesManager.getFromMessages("messages.account_balance", "&aYour balance is: &a6{0}");
        player_not_exist = prefix+ MessagesManager.getFromMessages("messages.player_not_exist", "&aPlayer {0} doesn't exist or hasn't played before");

        command_info = MessagesManager.getFromMessages("messages.commands_description.info", "Shows info about the plugin");
        command_give = MessagesManager.getFromMessages("messages.commands_description.give", "Insert experience into a player account");
        command_balance = MessagesManager.getFromMessages("messages.commands_description.balance", "Shows your account balance");
        command_remove = MessagesManager.getFromMessages("messages.commands_description.remove", "Remove experience from a player account");
        command_reload = MessagesManager.getFromMessages("messages.commands_description.reload", "Reload plugin configuration");
    }
}
