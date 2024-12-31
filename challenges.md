### Challenge 1: equals()

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position position)) return false;
        return x == position.x && y == position.y;
    }

Line return x == position.x && y == position.y; isn't covered by tests.

### Challenge 2: GUI protected methods

Solution: See where the methods are called and test them there.

### Challenge 3: System.currentTimeMillis()

System static class can't be mocked. It difficults to test branches that depend on it.

### Challenge 4: Threads

How its suposed to test threads?

### Challenge 5: Code without dependency injection

Some parts of the code are difficult to test because they don't use dependency injection.

### Challenge 6: Dead code

Fortunatelly, the dead code was public methods that can be tested directly.

### Challenge 7: printStackTrace()

Mutation failed to kill the mutant that removed the printStackTrace().


### Challenge 8: Random()

Random without seed and depency injection is unpredictable to test.