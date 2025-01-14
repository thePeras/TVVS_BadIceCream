# Testing Bad Ice Cream

> **Project** <br />
> Course Unit: [Software Testing Verification and Validation](https://sigarra.up.pt/feup/en/ucurr_geral.ficha_uc_view?pv_ocorrencia_id=537128 "Software Testing Verification and Validation"), 1nd year
> <br />
> Course: [Master in Software Engineering](https://sigarra.up.pt/feup/en/cur_geral.cur_view?pv_curso_id=10861&pv_ano_lectivo=2024) <br />
> Faculty: **FEUP** (Faculty of Engineering of the University of Porto)
> <br/>
> Project evaluation: **19**/20

## Project Goals

The high-level goals of this lab project are:

1. To learn about systematic unit testing.
2. To reason about test quality, using different criteria, i.e., code coverage, mutation score, and smelliness of a developed test suite.

## About the game

I am not the author of the game. The game was developed by 3 students in [LDTS course unit](https://sigarra.up.pt/feup/en/UCURR_GERAL.FICHA_UC_VIEW?pv_ocorrencia_id=520319).
Read the [Project.md](docs/LDTS-Project.md) file to learn more about the game.

## Current Reports

- [Jacoco report](https://theperas.github.io/tvvs/reports/jacoco/index.html)
- [PITest report](https://theperas.github.io/tvvs/reports/pitest/index.html)
- [Smelliness report](https://theperas.github.io/tvvs/reports/smelliness/index.html)

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

## Final Results

- branch coverage = 95%
- mutation score = 95%
- smelliness = 4.910714285714286% ≈ 5%

- ꭓ = 1.004
- 𝛼 = 1.009

## Commands

### Generate Jacoco report

```bash
mvn test jacoco:report
```

> target/site/jacoco/index.html

### Generate PITest report

```bash
mvn -DwithHistory test-compile org.pitest:pitest-maven:mutationCoverage
```

> target/pit-reports/index.html

### Generate smelliness report

Generate tsdetector-input-full.csv file

```bash
sudo ./generate-smelliness-report.sh
```

Remove all test suites files from tsdetector-input-full.csv where class under cover does not work in java <= 9.
Rename it to tsdetector-input.csv

```bash
java -jar TestSmellDetector.jar --file tsdetector-input.csv --thresholds spadini --granularity boolean --output tsdetector-output.csv
```

## Technologies used

<div>
<img width="40" src="https://raw.githubusercontent.com/marwin1991/profile-technology-icons/refs/heads/main/icons/java.png" alt="Java" title="Java"/>
<img width="40" src="https://raw.githubusercontent.com/marwin1991/profile-technology-icons/refs/heads/main/icons/maven.png" alt="Maven" title="Maven"/>
<img width="40" src="https://raw.githubusercontent.com/marwin1991/profile-technology-icons/refs/heads/main/icons/junit.png" alt="JUnit" title="JUnit"/>
<img width="40" src="https://raw.githubusercontent.com/marwin1991/profile-technology-icons/refs/heads/main/icons/mocikto.png" alt="Mockito" title="Mockito"/>
</div>
