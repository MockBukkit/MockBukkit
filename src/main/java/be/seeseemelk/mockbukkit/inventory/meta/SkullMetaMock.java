package be.seeseemelk.mockbukkit.inventory.meta;

import com.google.common.base.Strings;
import org.bukkit.inventory.meta.SkullMeta;

/**
 * Created by SimplyBallistic on 27/10/2018
 *
 * @author SimplyBallistic
 **/
public class SkullMetaMock extends ItemMetaMock implements SkullMeta {
    private String owner;

    @Override
    public String getOwner() {
        return owner;
    }

    @Override
    public boolean hasOwner() {
        return !Strings.isNullOrEmpty(owner);
    }

    @Override
    public boolean setOwner(String owner) {
        this.owner = owner;
        return true;
    }

    @Override
    public SkullMeta clone() {
        SkullMetaMock mock = new SkullMetaMock();
        mock.setOwner(owner);
        return mock;
    }
}
