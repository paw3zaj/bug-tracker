package pl.zajaczkowski.bugtracker.issue.enumes;

public enum PriorityName {
    TRIVIAL("#FFFFE0"),             //LightYellow
    LOW ("#FFFF00"),                //Yellow
    MEDIUM ("#FFA500"),             //Orange
    HIGH ("#FF7F50"),               //Coral, LightRed
    URGENT ("#FF0000");             //Red

    private final String colour;

    PriorityName(String colour) {
        this.colour = colour;
    }
}
