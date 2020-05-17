import java.util.LinkedList;
import java.util.List;

public enum Emoji {
    // People
    SPEAKING_HEAD_IN_SILHOUETTE("\uD83D\uDDE3"),
    WRITING_HAND("✍\uD83C\uDFFB"),
    BRAIN("\uD83E\uDDE0"),
    WOMAN_TEACHER("\uD83D\uDC69\u200D\uD83C\uDFEB"),
    MAN_TEACHER("\uD83D\uDC68\u200D\uD83C\uDFEB"),

    // Travel and Places
    DERELICT_HOUSE_BUILDING("\uD83C\uDFDA"),
    CLASSICAL_BUILDING("\uD83C\uDFDB"),
    STADIUM("\uD83C\uDFDF"),

    // Objects
    MEMO("\uD83D\uDCDD"), //
    MICROSCOPE("\uD83D\uDD2C"),
    QUESTION("❓");




    String emoji;

    Emoji(String emoji) {
        this.emoji = emoji;
    }

    @Override
    public String toString() {
        return emoji;
    }
}
