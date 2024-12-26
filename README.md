# Testing Bad Ice Cream

## About the game

Read the [Project.md](docs/Project.md) file to learn more about the game.

## Current Reports

- (future link)
- (future link)
- (future link)

## Evaluation Formula

> 65% x branch coverage^ꭓ + 35% x mutation score^𝛼 - 10% x smelliness^ᵝ

**ꭓ**: As the project under test might have dead code or code impossible to reach/exercise, we will compute the maximum achievable branch coverage by

1. building a super-gigant test suite with all our test cases + all test cases developed by all students
2. running branch coverage. The branch coverage achieved by the super-gigant test suite will work as the maximum achievable branch coverage.

**𝛼**: As it is impossible to automatically detect whether a mutant is or is not equivalent, we will make an educated guess by

1. building a super-gigant test suite with all our test cases + all test cases developed by all students
2. running mutation analysis
3. identifying the mutants that are not killed by the super-gigant test suite. The non-killed mutants will be considered equivalent and will not be taken into account to assess the mutation score of your test suite.

**ᵝ**: To compute smelliness we first compute the ratio of test suites affected by each test smell and then the average % of test suites affected by all smells. Formally, given S as the set of smells and T as the set of test suites, smelliness is computed as:

<img src="docs/resources/smelliness_formula.png" alt="smelliness" width="300"/>

## Commands

### Generate Jacoco report

```bash
mvn test jacoco:report
```
>target/site/jacoco/index.html

### Generate PITest report

```bash
mvn -DwithHistory test-compile org.pitest:pitest-maven:mutationCoverage
```
>target/pit-reports/index.html

### Generate smelliness report

```bash
java -jar TestSmellDetector.jar --file tsdetector-input.csv --thresholds spadini --granularity boolean --output tsdetector-output.csv
```

## Roadmap

- [x] Smelliness script to generate .csv file
- [ ] Create CI pipeline to
  - [ ] Build the game
  - [ ] Run the tests
  - [ ] Generate the reports (jacoco / pitest / smelliness)
  - [ ] Get values from the reports and update the README with the results and fail if the results are below 10/20
- [ ] Generate automatic tests
- [ ] Plan the Tests Strategy?
