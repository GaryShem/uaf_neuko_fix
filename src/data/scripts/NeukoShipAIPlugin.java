package data.scripts;

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.PluginPick;
import com.fs.starfarer.api.campaign.CampaignPlugin;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.combat.ShipAIConfig;
import com.fs.starfarer.api.combat.ShipAIPlugin;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.fleet.FleetMemberAPI;

public class NeukoShipAIPlugin extends BaseModPlugin {
    private boolean isNeuko(FleetMemberAPI member) {
        if (member == null) return false;
        PersonAPI captain = member.getCaptain();
        if (captain == null || captain.isDefault()) return false;
        String captainId = captain.getId();
        if (captainId.equals("uaf_black_sorceress") || captainId.equals("uaf_robo_empress")) {
            return true;
        }
        if (!captain.isAICore()) return false;
        String aiCoreId = captain.getAICoreId();
        return aiCoreId.equals("uaf_courtesan_core")
                || aiCoreId.equals("uaf_princess_core")
                || aiCoreId.equals("uaf_crown_princess_core");
    }

    private boolean isUAF_AI(FleetMemberAPI member) {
        return (member != null && member.getVariant() != null && member.getVariant().hasHullMod("uaf_automataSpec"));
    }

    @Override
    public PluginPick<ShipAIPlugin> pickShipAI(FleetMemberAPI member, ShipAPI ship) {
        if (isNeuko(member) && isUAF_AI(member)) {
            String shipName = "<unknown>";
            if (ship != null) {
                shipName = ship.getName();
            }
            Global.getLogger(getClass()).info("Neuko personality for ship " + shipName + ": " + member.getCaptain().getPersonalityAPI().getDisplayName());
            ShipAIConfig shipAI = new ShipAIConfig();
            shipAI.personalityOverride = member.getCaptain().getPersonalityAPI().getId();
            return new PluginPick<ShipAIPlugin>(
                    Global.getSettings().createDefaultShipAI(ship, shipAI),
                    CampaignPlugin.PickPriority.MOD_SET
            );
        }
        return null;
    }
}
