package com.redlotus.galacticwarfare.utils;

import com.github.javafaker.Faker;

public class GWUtils {
    private static final String[] SUFFIXES = new String[] { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th" };
    private static final Faker FAKER = Faker.instance();

    public static String ordinal(int i) {
        switch (i % 100) {
            case 11:
            case 12:
            case 13:
                return i + "th";
            default:
                return i + SUFFIXES[i % 10];

        }
    }

    public static boolean isBetween(int x, int lower, int upper) {
        return lower <= x && x <= upper;
    }

    public static String generateName() {
        int wheel = (int)(Math.random() * 100); // 0 -- 99
        // ANCIENT  0 - 11
        if(isBetween(wheel, 0, 2)) {
            return FAKER.ancient().primordial();
        } else if(isBetween(wheel, 3, 5)) {
            return FAKER.ancient().hero();
        } else if(isBetween(wheel, 6, 8)) {
            return FAKER.ancient().god();
        } else if(isBetween(wheel, 9, 11)) {
            return FAKER.ancient().titan();
        }

        // REGULAR 12 - 23
        else if(isBetween(wheel, 12, 17)) {
            return FAKER.name().fullName();
        } else if(isBetween(wheel, 18, 23)) {
            return FAKER.funnyName().name();
        }

        // COMMON 24 - 35
        else if(isBetween(wheel, 24, 26)) {
            return FAKER.animal().name();
        } else if(isBetween(wheel, 27, 29)) {
            return FAKER.dog().name();
        } else if(isBetween(wheel, 30, 31)) {
            return FAKER.food().fruit();
        } else if(isBetween(wheel, 32, 33)) {
            return FAKER.food().vegetable();
        } else if(isBetween(wheel, 34, 35)) {
            return FAKER.food().spice();
        }

        // MYSTIC 36 - 47
        else if(isBetween(wheel, 36, 38)) {
            return FAKER.elderScrolls().creature();
        } else if(isBetween(wheel, 39, 40)) {
            return FAKER.elderScrolls().dragon();
        } else if(isBetween(wheel, 41, 43)) {
            return FAKER.gameOfThrones().dragon();
        }  else if(isBetween(wheel, 44, 45)) {
            return FAKER.harryPotter().spell();
        } else if(isBetween(wheel, 46, 47)) {
            return FAKER.witcher().monster();
        }

        // UNCOMMON 48 - 59
        else if(isBetween(wheel, 48, 51)) {
            return FAKER.app().name();
        } else if(isBetween(wheel, 52, 53)) {
            return FAKER.food().dish();
        } else if(isBetween(wheel, 54, 56)) {
            return FAKER.book().title();
        } else if(isBetween(wheel, 57, 59)) {
            return FAKER.beer().name();
        }

        // CHARACTERS & CELEB 60 - 89
        else if(isBetween(wheel, 60, 60)) {
            return FAKER.aquaTeenHungerForce().character();
        } else if(isBetween(wheel, 61, 61)) {
            return FAKER.buffy().characters();
        } else if(isBetween(wheel, 62, 62)) {
            return FAKER.buffy().celebrities();
        } else if(isBetween(wheel, 63, 65)) {
            return FAKER.dragonBall().character();
        } else if(isBetween(wheel, 66, 67)) {
            return FAKER.rockBand().name();
        } else if(isBetween(wheel, 68, 68)) {
            return FAKER.dune().character();
        } else if(isBetween(wheel, 69, 71)) {
            return FAKER.esports().player();
        } else if(isBetween(wheel, 72, 73)) {
            return FAKER.hobbit().character();
        } else if(isBetween(wheel, 74, 74)) {
            return FAKER.friends().character();
        } else if(isBetween(wheel, 75, 77)) {
            return FAKER.gameOfThrones().character();
        } else if(isBetween(wheel, 78, 79)) {
            return FAKER.hitchhikersGuideToTheGalaxy().character();
        } else if(isBetween(wheel, 80, 80)) {
            return FAKER.howIMetYourMother().character();
        } else if(isBetween(wheel, 81, 82)) {
            return FAKER.harryPotter().character();
        } else if(isBetween(wheel, 83, 83)) {
            return FAKER.hitchhikersGuideToTheGalaxy().starship();
        } else if(isBetween(wheel, 84, 85)) {
            return FAKER.lordOfTheRings().character();
        } else if(isBetween(wheel, 86, 87)) {
            return FAKER.overwatch().hero();
        }else if(isBetween(wheel, 88, 91)) {
            return FAKER.pokemon().name();
        } else if(isBetween(wheel, 92, 92)) {
            return FAKER.rickAndMorty().character();
        }else if(isBetween(wheel, 93, 93)) {
            return FAKER.starTrek().character();
        }else if(isBetween(wheel, 94, 94)) {
            return FAKER.starTrek().villain();
        }else if(isBetween(wheel, 95, 97)) {
            return FAKER.superhero().name();
        } else if(isBetween(wheel, 98, 99)) {
            return FAKER.witcher().character();
        }else if(isBetween(wheel, 100, 100)) {
            return FAKER.zelda().character();
        }

        else {
            return FAKER.name().fullName();
        }
    }

    public static void main(String [] args) {
        for(int i = 0; i < 100; i++) {
            System.out.println(generateName());
        }
    }
}
