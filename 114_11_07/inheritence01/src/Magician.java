public class Magician extends Role{
    private int healPower;

    // 建構子：初始化名稱、生命值與魔法攻擊力
    public Magician(String name, int health, int magicPower,int healPower) {
        super(name, health, magicPower);
        this.healPower = healPower;
    }

    //取得治癒力
    public int getHealPower() {
        return healPower;
    }


    // 對對手發動魔法攻擊（會直接扣除對手生命）
    public void castSpell(Magician opponent) {
        opponent.setHealth(opponent.getHealth() - this.getAttackPower());
        System.out.println(this.getName() + " 施放魔法攻擊 " + opponent.getName() + "，造成 " + this.getAttackPower() + " 點傷害。" + opponent.toString());
    }

    public void heal (SwordsMan ally) {
        ally.setHealth(ally.getHealth() + this.healPower);
        System.out.println(this.getName() + " 治癒 " + ally.getName() + "，恢復 " + this.healPower + " 點生命。" + ally.toString());
    }

    @Override
    public String toString() {
        return super.toString() + " 治癒力: " + healPower;
    }
}
