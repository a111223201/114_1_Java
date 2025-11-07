public class Role extends Object {
    private String name;
    private int health;
    private int attackPower;

    // 建構子：初始化名稱、生命值與攻擊力
    public Role(String name, int health, int attackPower) {
        this.name = name;
        this.health = health;
        this.attackPower = attackPower;
    }

    public String getName() {
        return name;
    }

    // 取得生命值
    public int getHealth() {
        return health;
    }

    // 取得魔法攻擊力
    public int getAttackPower() {
        return attackPower;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isAlive() {
        return health > 0;
    }

    @Override
    public String toString() {
        return "角色名稱: " + name + ", 生命值: " + health ;
    }
}
