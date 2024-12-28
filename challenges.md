### Challenge 1: equals()

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position position)) return false;
        return x == position.x && y == position.y;
    }

Line return x == position.x && y == position.y; isn't covered by tests.

### Challenge 2: GUI protected methods

Solution: See where the methods are called and test them there.
