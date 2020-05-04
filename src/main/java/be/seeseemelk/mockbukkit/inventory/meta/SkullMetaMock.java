package be.seeseemelk.mockbukkit.inventory.meta;

import com.google.common.base.Strings;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;

import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.meta.SkullMeta;

/**
 * Created by SimplyBallistic on 27/10/2018
 *
 * @author SimplyBallistic
 **/
public class SkullMetaMock extends ItemMetaMock implements SkullMeta {
    private String owner;
    
    public SkullMetaMock() {
        super();
    }
    
    public SkullMetaMock(SkullMeta meta) {
        super(meta);
        
        this.owner = meta.getOwningPlayer().getName();
    }

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
    public SkullMetaMock clone() {
        SkullMetaMock mock = (SkullMetaMock) super.clone();
        mock.setOwner(owner);
        return mock;
    }

	@Override
	public OfflinePlayer getOwningPlayer()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean setOwningPlayer(OfflinePlayer owner)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
}
