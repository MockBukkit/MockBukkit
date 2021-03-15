package be.seeseemelk.mockbukkit.inventory.meta;

import org.bukkit.DyeColor;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BannerMetaMockTest
{
    private BannerMetaMock meta;

    @Before
    public void setUp()
    {
        meta = new BannerMetaMock();
    }

    @Test
    public void setBaseColor_SetExactly()
    {
        meta.setBaseColor(DyeColor.BLACK);
        assertEquals(DyeColor.BLACK, meta.getBaseColor());
    }

    @Test
    public void setPatterns_SetExactly()
    {
        List<Pattern> patterns = Arrays.asList(
                new Pattern(DyeColor.BLACK, PatternType.CROSS),
                new Pattern(DyeColor.BROWN, PatternType.CROSS)
        );

        meta.setPatterns(patterns);
        assertEquals(patterns, meta.getPatterns());
    }

    @Test
    public void addPattern()
    {
        Pattern pattern = new Pattern(DyeColor.MAGENTA, PatternType.DIAGONAL_LEFT_MIRROR);
        meta.addPattern(pattern);
        assertTrue(meta.getPatterns().contains(pattern));
    }

    @Test
    public void getPattern_SetAsList()
    {
        Pattern pattern1 = new Pattern(DyeColor.BLACK, PatternType.CROSS);
        Pattern pattern2 = new Pattern(DyeColor.BROWN, PatternType.CROSS);
        List<Pattern> patterns = Arrays.asList(pattern1, pattern2);
        meta.setPatterns(patterns);

        assertEquals(pattern1, meta.getPattern(0));
        assertEquals(pattern2, meta.getPattern(1));
    }

    @Test
    public void getPattern_SetExactly()
    {
        Pattern pattern = new Pattern(DyeColor.MAGENTA, PatternType.DIAGONAL_LEFT_MIRROR);
        meta.setPatterns(Collections.singletonList(null)); // so there is at least one pattern
        meta.setPattern(0, pattern);
        assertEquals(pattern, meta.getPattern(0));
    }

    @Test
    public void removePattern()
    {
        Pattern pattern = new Pattern(DyeColor.BLACK, PatternType.CROSS);
        List<Pattern> patterns = Collections.singletonList(pattern);
        meta.setPatterns(patterns);
        meta.removePattern(0);
        assertEquals(0, meta.numberOfPatterns());

    }

    @Test
    public void numberOfPatterns_DefaultsTo0()
    {
        assertEquals(0, meta.numberOfPatterns());
    }

    @Test
    public void numberOfPatterns_SetExactly()
    {
        List<Pattern> patterns = Arrays.asList(
                new Pattern(DyeColor.BLACK, PatternType.CROSS),
                new Pattern(DyeColor.BROWN, PatternType.CROSS)
        );

        meta.setPatterns(patterns);
        assertEquals(2, meta.numberOfPatterns());
    }
}
