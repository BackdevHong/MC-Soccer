package soccerplugin.soccersystem.color;

public enum ColorStringEnum {
    BLACK("&0"),
    BLUE("&1"),
    GREEN("&2"),
    BLUEGREEN("&3"),
    RED("&4"),
    PURPLE("&5"),
    GOLD("&6"),
    SILVER("&7"),
    DARKSILVER("&8"),
    LIGHTBLUE("&9"),
    LIGHTGREEN ("&a"),
    SKYBLUE ("&b"),
    LIGHTRED ("&c"),
    PINK ("&d"),
    YELLOW ("&e"),
    WHITE ("&f");
    private final String code;

    ColorStringEnum(String code) {
        this.code = code;
    }
    public String toString() {
        return this.code;
    }
}
