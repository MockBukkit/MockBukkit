package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import be.seeseemelk.mockbukkit.inventory.PlayerInventoryMock;
import be.seeseemelk.mockbukkit.inventory.PlayerInventoryViewMock;
import be.seeseemelk.mockbukkit.inventory.SimpleInventoryViewMock;
import com.google.common.base.Charsets;
import com.google.common.base.Function;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.entity.*;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDamageEvent.DamageModifier;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.*;
import org.bukkit.inventory.InventoryView.Property;
import org.bukkit.map.MapView;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.Vector;

import java.net.InetSocketAddress;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class PlayerMock extends EntityMock implements Player {
    private static final double MAX_HEALTH = 20.0;
    private static final Set<String> playersThatPlayedBefore = new HashSet<>();
    boolean canFly;
    boolean isFlying;
    HashMap<Player, Player> hiddenPlayers = new HashMap<>();
    Scoreboard scoreboard;
    private int foodLevel;
    private boolean online;
    private PlayerInventoryMock inventory = null;
    private GameMode gamemode = GameMode.SURVIVAL;
    private double maxHealth = MAX_HEALTH;
    private String displayName = null;
    private double health = 20.0;
    private boolean whitelisted = true;
    private InventoryView inventoryView;
    private boolean isSleeping;
    private boolean isSprinting;
    private boolean isSneaking;
    private int level;
    private float xp;
    private ItemStack itemOnCursor;
    private long lastTimeDamage;
    private final Set<PotionEffect> potionEffects = new HashSet<>();

    public PlayerMock(String name) {
        this(name, UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes(Charsets.UTF_8)));
        this.online = false;
    }

    public PlayerMock(String name, UUID uuid) {
        super(uuid);
        setName(name);
        setDisplayName(name);
        this.online = true;

        if (Bukkit.getWorlds().size() == 0) {
            MockBukkit.getMock().addSimpleWorld("world");
        }

        setLocation(Bukkit.getWorlds().get(0).getSpawnLocation().clone());
        closeInventory();
        playersThatPlayedBefore.add(this.name);
    }

    /**
     * Assert that the player is in a specific gamemode.
     *
     * @param expectedGamemode The gamemode the player should be in.
     */
    public void assertGameMode(GameMode expectedGamemode) {
        assertEquals(expectedGamemode, gamemode);
    }

    /**
     * Simulates the player damaging a block just like {@link simulateBlockDamage}.
     * However, if {@code InstaBreak} is enabled, it will not automatically fire a
     * {@link BlockBreakEvent}. It will also still fire a {@link BlockDamageEvent}
     * even if the player is not in survival mode.
     *
     * @param block The block to damage.
     * @return The event that has been fired.
     */
    protected BlockDamageEvent simulateBlockDamagePure(Block block) {
        BlockDamageEvent event = new BlockDamageEvent(this, block, getItemInHand(), false);
        Bukkit.getPluginManager().callEvent(event);
        return event;
    }

    /**
     * Simulates the player damaging a block. Note that this method does not
     * anything unless the player is in survival mode. If {@code InstaBreak} is set
     * to true by an event handler, a {@link BlockBreakEvent} is immediately fired.
     * The result will then still be whether or not the {@link BlockDamageEvent} was
     * cancelled or not, not the later {@link BlockBreakEvent}.
     *
     * @param block The block to damage.
     * @return {@code true} if the block was damaged, {@code false} if the event was
     * cancelled or the player was not in survival gamemode.
     */
    public boolean simulateBlockDamage(Block block) {
        if (gamemode == GameMode.SURVIVAL) {
            BlockDamageEvent event = simulateBlockDamagePure(block);
            if (event.getInstaBreak()) {
                BlockBreakEvent breakEvent = new BlockBreakEvent(block, this);
                Bukkit.getPluginManager().callEvent(breakEvent);
                if (!breakEvent.isCancelled())
                    block.setType(Material.AIR);
            }

            return !event.isCancelled();
        } else {
            return false;
        }
    }

    /**
     * Simulates the player breaking a block. This method will not break the block
     * if the player is in adventure or spectator mode. If the player is in survival
     * mode, the player will first damage the block.
     *
     * @param block The block to break.
     * @return {@code true} if the block was broken, {@code false} if it wasn't or
     * if the player was in adventure mode or in spectator mode.
     */
    public boolean simulateBlockBreak(Block block) {
        if ((gamemode == GameMode.SPECTATOR || gamemode == GameMode.ADVENTURE)
                || (gamemode == GameMode.SURVIVAL && simulateBlockDamagePure(block).isCancelled()))
            return false;

        BlockBreakEvent event = new BlockBreakEvent(block, this);
        Bukkit.getPluginManager().callEvent(event);
        if (!event.isCancelled())
            block.setType(Material.AIR);
        return !event.isCancelled();
    }

    @Override
    public PlayerInventory getInventory() {
        if (inventory == null) {
            inventory = (PlayerInventoryMock) Bukkit.createInventory(this, InventoryType.PLAYER);
        }
        return inventory;
    }

    @Override
    public GameMode getGameMode() {
        return gamemode;
    }

    @Override
    public void setGameMode(GameMode mode) {
        gamemode = mode;
    }

    @Override
    public double getHealth() {
        return health;
    }

    @Override
    public void setHealth(double health) {
        if (health <= 0) {
            this.health = 0;
            PlayerDeathEvent event = new PlayerDeathEvent(this, new ArrayList<>(), 0, getName() + " got killed");
            Bukkit.getPluginManager().callEvent(event);
        } else if (health > maxHealth) {
            this.health = maxHealth;
        } else {
            this.health = health;
        }
    }

    @Override
    public void resetMaxHealth() {
        setMaxHealth(MAX_HEALTH);
    }

    @Override
    public void damage(double amount) {
        Map<DamageModifier, Double> modifiers = new EnumMap<>(DamageModifier.class);
        modifiers.put(DamageModifier.BASE, 1.0);
        Map<DamageModifier, Function<Double, Double>> modifierFunctions = new EnumMap<>(DamageModifier.class);
        modifierFunctions.put(DamageModifier.BASE, damage -> damage);

        EntityDamageEvent event = new EntityDamageEvent(this, DamageCause.CUSTOM, modifiers, modifierFunctions);
        event.setDamage(amount);
        Bukkit.getPluginManager().callEvent(event);

        if (!event.isCancelled()) {

            lastEntityDamageEvent = event;
            setHealth(health - amount);
        }
    }

    @Override
    public void damage(double amount, Entity source) {
        Map<DamageModifier, Double> modifiers = new EnumMap<>(DamageModifier.class);
        modifiers.put(DamageModifier.BASE, 1.0);
        Map<DamageModifier, Function<Double, Double>> modifierFunctions = new EnumMap<>(DamageModifier.class);
        modifierFunctions.put(DamageModifier.BASE, damage -> damage);

        EntityDamageByEntityEvent event = new EntityDamageByEntityEvent(source, this, DamageCause.ENTITY_ATTACK,
                modifiers, modifierFunctions);
        event.setDamage(amount);
        Bukkit.getPluginManager().callEvent(event);
        if (!event.isCancelled()) {
            lastEntityDamageEvent = event;
            setHealth(health - amount);
        }
    }

    @Override
    public boolean isWhitelisted() {
        return this.whitelisted;
    }

    @Override
    public void setWhitelisted(boolean value) {
        this.whitelisted = value;
    }

    @Override
    public Player getPlayer() {
        if (online) {
            return this;
        }
        return null;
    }

    @Override
    public boolean isOnline() {
        return this.online;
    }

    @Override
    public boolean isBanned() {
        return MockBukkit.getMock().getBanList(BanList.Type.NAME).isBanned(getName());
    }

    @Override
    public void setBanned(boolean banned) {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public InventoryView getOpenInventory() {
        return inventoryView;
    }

    @Override
    public void openInventory(InventoryView inventory) {
        inventoryView = inventory;
    }

    @Override
    public InventoryView openInventory(Inventory inventory) {
        inventoryView = new PlayerInventoryViewMock(this, inventory);
        return inventoryView;
    }

    @Override
    public void closeInventory() {
        inventoryView = new SimpleInventoryViewMock(this, null, inventory, InventoryType.CRAFTING);
    }

    @Override
    public boolean performCommand(String command) {
        return Bukkit.dispatchCommand(this, command);
    }

    @Override
    public Inventory getEnderChest() {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public boolean setWindowProperty(Property prop, int value) {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public InventoryView openWorkbench(Location location, boolean force) {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public InventoryView openEnchanting(Location location, boolean force) {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public ItemStack getItemInHand() {
        return getInventory().getItemInHand();
    }

    @Override
    public void setItemInHand(ItemStack item) {
        getInventory().setItemInHand(item);
    }

    @Override
    public ItemStack getItemOnCursor() {
        return itemOnCursor;
    }

    @Override
    public void setItemOnCursor(ItemStack item) {
        itemOnCursor = item;
    }

    @Override
    public boolean isSleeping() {
        return isSleeping;
    }

    @Override
    public int getSleepTicks() {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public boolean isBlocking() {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public int getExpToLevel() {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public double getEyeHeight() {
        return 1.6;
    }

    @Override
    public double getEyeHeight(boolean ignorePose) {
        return getEyeHeight();
    }

    @Override
    public Location getEyeLocation() {
        return getLocation().clone().add(0, getEyeHeight(), 0);
    }

    @Override
    public List<Block> getLineOfSight(Set<Material> transparent, int maxDistance) {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public Block getTargetBlock(Set<Material> transparent, int maxDistance) {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public List<Block> getLastTwoTargetBlocks(Set<Material> transparent, int maxDistance) {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public int getRemainingAir() {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void setRemainingAir(int ticks) {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public int getMaximumAir() {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void setMaximumAir(int ticks) {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public int getMaximumNoDamageTicks() {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void setMaximumNoDamageTicks(int ticks) {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public double getLastDamage() {
        if (lastEntityDamageEvent != null) {
            return lastEntityDamageEvent.getDamage();
        }
        return 0.0;
    }

    @Override
    public void setLastDamage(double damage) {
        lastEntityDamageEvent.setDamage(damage);
    }

    @Override
    public int getNoDamageTicks() {
        return (int) ((System.currentTimeMillis() - lastTimeDamage) / 1000) / 20;
    }

    @Override
    public void setNoDamageTicks(int ticks) {
        lastTimeDamage = System.currentTimeMillis() - ticks;
    }

    @Override
    public Player getKiller() {
        if (lastEntityDamageEvent instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent ev = ((EntityDamageByEntityEvent) lastEntityDamageEvent);

            if (ev.getDamager() instanceof Player) {
                return ((Player) ev.getDamager());
            }
        }
        return null;
    }

    @Override
    public boolean addPotionEffect(PotionEffect effect) {
        boolean existed = hasPotionEffect(effect.getType());
        potionEffects.add(effect);
        return !existed;
    }

    @Override
    public boolean addPotionEffect(PotionEffect effect, boolean force) {
        boolean existed = potionEffects.stream().anyMatch(p -> effect.getType() == p.getType());

        if (force)
            potionEffects.removeIf(p -> p.getType() == effect.getType());
        potionEffects.add(effect);
        return force || !existed;
    }

    @Override
    public boolean addPotionEffects(Collection<PotionEffect> effects) {
        boolean existed = potionEffects.stream().map(PotionEffect::getType).collect(Collectors.toList()).containsAll(effects.stream().map(PotionEffect::getType).collect(Collectors.toList()));
        this.potionEffects.addAll(effects);
        return !existed;
    }

    @Override
    public boolean hasPotionEffect(PotionEffectType type) {
        return potionEffects.stream().anyMatch(p -> p.getType() == type);
    }

    @Override
    public void removePotionEffect(PotionEffectType type) {
        potionEffects.removeIf(p -> p.getType() == type);
    }

    @Override
    public Collection<PotionEffect> getActivePotionEffects() {
        return new ArrayList<>(potionEffects);
    }

    @Override
    public boolean hasLineOfSight(Entity other) {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public boolean getRemoveWhenFarAway() {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void setRemoveWhenFarAway(boolean remove) {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public EntityEquipment getEquipment() {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public boolean getCanPickupItems() {
        return true;
    }

    @Override
    public void setCanPickupItems(boolean pickup) {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public boolean isLeashed() {
        // Players can not be leashed
        return false;
    }

    @Override
    public Entity getLeashHolder() throws IllegalStateException {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public boolean setLeashHolder(Entity holder) {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public <T extends Projectile> T launchProjectile(Class<? extends T> projectile) {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public <T extends Projectile> T launchProjectile(Class<? extends T> projectile, Vector velocity) {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public boolean isConversing() {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void acceptConversationInput(String input) {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public boolean beginConversation(Conversation conversation) {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void abandonConversation(Conversation conversation) {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void abandonConversation(Conversation conversation, ConversationAbandonedEvent details) {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public long getFirstPlayed() {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public long getLastPlayed() {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public boolean hasPlayedBefore() {
        return playersThatPlayedBefore.contains(this.name);
    }

    @Override
    public Map<String, Object> serialize() {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void sendPluginMessage(Plugin source, String channel, byte[] message) {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public Set<String> getListeningPluginChannels() {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public void setDisplayName(String name) {
        this.displayName = name;
    }

    @Override
    public String getPlayerListName() {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void setPlayerListName(String name) {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public Location getCompassTarget() {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void setCompassTarget(Location loc) {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public InetSocketAddress getAddress() {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void sendRawMessage(String message) {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void kickPlayer(String message) {
        MockBukkit.getMock().removePlayer(message, this);
    }

    @Override
    public void chat(String msg) {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public boolean isSneaking() {
        return isSneaking;
    }

    @Override
    public void setSneaking(boolean sneak) {
        isSneaking = sneak;
    }

    @Override
    public boolean isSprinting() {
        return isSprinting;
    }

    @Override
    public void setSprinting(boolean sprinting) {
        isSprinting = sprinting;
    }

    @Override
    public void saveData() {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void loadData() {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public boolean isSleepingIgnored() {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void setSleepingIgnored(boolean isSleeping) {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void playNote(Location loc, byte instrument, byte note) {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void playNote(Location loc, Instrument instrument, Note note) {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void playSound(Location location, Sound sound, float volume, float pitch) {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void playSound(Location location, String sound, float volume, float pitch) {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void playEffect(Location loc, Effect effect, int data) {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public <T> void playEffect(Location loc, Effect effect, T data) {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void sendBlockChange(Location loc, Material material, byte data) {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public boolean sendChunkChange(Location loc, int sx, int sy, int sz, byte[] data) {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void sendBlockChange(Location loc, int material, byte data) {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void sendSignChange(Location loc, String[] lines) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void sendMap(MapView map) {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void updateInventory() {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void awardAchievement(Achievement achievement) {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void removeAchievement(Achievement achievement) {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public boolean hasAchievement(Achievement achievement) {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void incrementStatistic(Statistic statistic) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void decrementStatistic(Statistic statistic) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void incrementStatistic(Statistic statistic, int amount) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void decrementStatistic(Statistic statistic, int amount) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void setStatistic(Statistic statistic, int newValue) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public int getStatistic(Statistic statistic) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void incrementStatistic(Statistic statistic, Material material) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void decrementStatistic(Statistic statistic, Material material) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public int getStatistic(Statistic statistic, Material material) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void incrementStatistic(Statistic statistic, Material material, int amount) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void decrementStatistic(Statistic statistic, Material material, int amount) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void setStatistic(Statistic statistic, Material material, int newValue) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void incrementStatistic(Statistic statistic, EntityType entityType) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void decrementStatistic(Statistic statistic, EntityType entityType) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public int getStatistic(Statistic statistic, EntityType entityType) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void incrementStatistic(Statistic statistic, EntityType entityType, int amount)
            throws IllegalArgumentException {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void decrementStatistic(Statistic statistic, EntityType entityType, int amount) {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void setStatistic(Statistic statistic, EntityType entityType, int newValue) {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void setPlayerTime(long time, boolean relative) {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public long getPlayerTime() {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public long getPlayerTimeOffset() {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public boolean isPlayerTimeRelative() {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void resetPlayerTime() {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public WeatherType getPlayerWeather() {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void setPlayerWeather(WeatherType type) {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void resetPlayerWeather() {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void giveExp(int amount) {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void giveExpLevels(int amount) {
        level += amount;
    }

    @Override
    public float getExp() {
        return xp;
    }

    @Override
    public void setExp(float exp) {
        xp = exp;
        if (xp > 1) {
            level++;
        }
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public int getTotalExperience() {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void setTotalExperience(int exp) {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public float getExhaustion() {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void setExhaustion(float value) {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public float getSaturation() {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void setSaturation(float value) {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public int getFoodLevel() {
        return foodLevel;
    }

    @Override
    public void setFoodLevel(int value) {
        foodLevel = value;
    }

    @Override
    public Location getBedSpawnLocation() {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void setBedSpawnLocation(Location location) {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void setBedSpawnLocation(Location location, boolean force) {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public boolean getAllowFlight() {
        return canFly;
    }

    @Override
    public void setAllowFlight(boolean flight) {
        canFly = flight;
    }

    @Override
    public void hidePlayer(Player player) {
        hiddenPlayers.put(this, player);
    }

    @Override
    public void showPlayer(Player player) {
        hiddenPlayers.remove(this, player);
    }

    @Override
    public boolean canSee(Player player) {
        return hiddenPlayers.containsKey(this) && hiddenPlayers.containsValue(player);
    }

    @Override
    public boolean isFlying() {
        return this.isFlying;
    }

    @Override
    public void setFlying(boolean value) {
        this.isFlying = true;
    }

    @Override
    public float getFlySpeed() {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void setFlySpeed(float value) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public float getWalkSpeed() {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void setWalkSpeed(float value) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void setTexturePack(String url) {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void setResourcePack(String url) {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    @Override
    public void setScoreboard(Scoreboard scoreboard) throws IllegalArgumentException, IllegalStateException {
        this.scoreboard = scoreboard;
    }

    @Override
    public boolean isHealthScaled() {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void setHealthScaled(boolean scale) {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public double getHealthScale() {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void setHealthScale(double scale) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public Entity getSpectatorTarget() {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void setSpectatorTarget(Entity entity) {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void sendTitle(String title, String subtitle) {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void resetTitle() {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public Player.Spigot spigot() {
        return new PlayerSpigot(this);
    }

    @Override
    public List<Block> getLineOfSight(HashSet<Byte> transparent, int maxDistance) {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public Block getTargetBlock(HashSet<Byte> transparent, int maxDistance) {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public List<Block> getLastTwoTargetBlocks(HashSet<Byte> transparent, int maxDistance) {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public Egg throwEgg() {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public Snowball throwSnowball() {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public Arrow shootArrow() {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public int _INVALID_getLastDamage() {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void _INVALID_setLastDamage(int damage) {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void _INVALID_damage(int amount) {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void _INVALID_damage(int amount, Entity source) {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public int _INVALID_getHealth() {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void _INVALID_setHealth(int health) {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public int _INVALID_getMaxHealth() {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public void _INVALID_setMaxHealth(int health) {
        // TODO Auto-generated method stub
        throw new UnimplementedOperationException();
    }

    @Override
    public double getMaxHealth() {
        return maxHealth;
    }

    @Override
    public void setMaxHealth(double health) {
        this.maxHealth = health;
        if (getHealth() > maxHealth)
            setHealth(maxHealth);
    }

}
