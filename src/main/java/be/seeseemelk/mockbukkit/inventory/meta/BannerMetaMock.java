package be.seeseemelk.mockbukkit.inventory.meta;

import org.bukkit.DyeColor;
import org.bukkit.block.banner.Pattern;
import org.bukkit.inventory.meta.BannerMeta;

import java.util.ArrayList;
import java.util.List;

public class BannerMetaMock extends ItemMetaMock implements BannerMeta
{
    private DyeColor baseColor; // defaults to null
    private List<Pattern> patterns = new ArrayList<>();

    @Override
    public DyeColor getBaseColor()
    {
        return this.baseColor;
    }

    @Override
    public void setBaseColor(DyeColor color)
    {
        this.baseColor = color;
    }

    @Override
    public List<Pattern> getPatterns()
    {
        return new ArrayList<>(patterns);
    }

    @Override
    public void setPatterns(List<Pattern> patterns)
    {
        this.patterns = new ArrayList<>(patterns);
    }

    @Override
    public void addPattern(Pattern pattern)
    {
        this.patterns.add(pattern);
    }

    @Override
    public Pattern getPattern(int i)
    {
        return this.patterns.get(i);
    }

    @Override
    public Pattern removePattern(int i)
    {
        return this.patterns.remove(i);
    }

    @Override
    public void setPattern(int i, Pattern pattern)
    {
        this.patterns.set(i, pattern);
    }

    @Override
    public int numberOfPatterns()
    {
        return this.patterns.size();
    }
}
