package com.ecoapp.waste_management.enums;

public enum UserLevel {
    BEGINNER("Iniciante", 0, 99),
    BRONZE("Bronze", 100, 499),
    SILVER("Prata", 500, 999),
    GOLD("Ouro", 1000, 2499),
    PLATINUM("Platina", 2500, 4999),
    DIAMOND("Diamante", 5000, Integer.MAX_VALUE);

    private final String description;
    private final int minPoints;
    private final int maxPoints;

    UserLevel(String description, int minPoints, int maxPoints) {
        this.description = description;
        this.minPoints = minPoints;
        this.maxPoints = maxPoints;
    }

    public String getDescription() {
        return description;
    }

    public int getMinPoints() {
        return minPoints;
    }

    public int getMaxPoints() {
        return maxPoints;
    }

    public static UserLevel getByPoints(int points) {
        for (UserLevel level : values()) {
            if (points >= level.minPoints && points <= level.maxPoints) {
                return level;
            }
        }
        return BEGINNER;
    }
}