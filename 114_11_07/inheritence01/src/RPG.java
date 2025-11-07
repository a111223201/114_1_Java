public class RPG {
    public static void main(String[] args) {
        // 建立劍士和魔法師角色
        SwordsMan swordsMan = new SwordsMan("Arthur", 100, 20);
        SwordsMan swordsMan2 = new SwordsMan("Lancelot", 100, 18);

        Magician magician = new Magician("Merlin", 80, 25, 15);
        Magician magician2 = new Magician("Morgana", 80, 22, 12);

        // 戰鬥過程
        System.out.println("戰鬥開始");
        swordsMan.attack(swordsMan2);
        magician.castSpell(magician2);
        magician.heal(swordsMan);
    }
}
