public class Priest extends RangedRole implements Healable {
    private int healPower;

    public Priest(String name, int health, int attackPower,
                  int healPower, int range, int maxEnergy) {
        super(name, health, attackPower, range, maxEnergy);
        this.healPower = healPower;
    }

    protected void onRangedPrepare() {
        System.out.println("📖 翻開魔法書，開始吟唱古老的咒語...");
        System.out.println("✨ 魔法能量在周圍凝聚，空氣中閃爍著神秘的光芒。");
    }
    protected void onRangedRecover() {
        System.out.println("🧘 " + this.getName() + " 閉目冥想，深度恢復魔力。");
    }

    public void onDeath() {
        System.out.println("💀 " + this.getName() + " 的生命之火熄滅了...");
        System.out.println("✨ " + this.getName() + " 的身體化為無數魔法粒子，消散在空氣中。");
        System.out.println("🌟 魔法書掉落在地上，微微發光。");
        System.out.println("---");
    }

    public void showSpecialSkill() {
        System.out.println("╔═════════════════════════════╗");
        System.out.println("║ " + this.getName() + " 的特殊技能        ║");
        System.out.println("╠═════════════════════════════╣");
        System.out.println("║ 技能名稱：元素爆發          ║");
        System.out.println("║ 技能描述：召喚強大魔法攻擊  ║");
        System.out.println("║ 技能效果：範圍魔法傷害      ║");
        System.out.println("║ 額外效果：恢復自身魔力      ║");
        System.out.println("║ 射程：" + getRange() + " 米                ║");
        System.out.println("║ 治療力：" + healPower + " 點            ║");  // ← 新增治療力顯示
        System.out.println("╚═════════════════════════════╝");
    }

    public void attack(Role opponent) {
        if (!consumeEnergy(15)) {
            System.out.println("❌ " + getName() + " 能量不足，無法施放魔法！");
            return;
        }

        System.out.println("✨ " + getName() + " 施放 " + getRangedAttackType() +
                " 攻擊 " + opponent.getName() + "！");
        opponent.takeDamage(this.getAttackPower());
    }

    @Override
    public void heal(Role target) {
        if (!consumeEnergy(15)) {
            System.out.println("能量不足！");
            return;
        }
        // 治療邏輯...
    }

    @Override
    public int getHealPower() {
        return healPower;
    }

    @Override
    public String getRangedAttackType() {
        return "Holy Light";
    }

    // 其他方法實作...
}
