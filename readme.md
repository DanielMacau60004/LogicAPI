# Logic API by Daniel Macau

## Overview

The Logic API provides a set of interfaces and utility methods designed to work with logical expressions in both propositional logic (PL) and first-order logic (FOL). Developed by Daniel Macau for thesis purposes, this API offers functionality for parsing, checking well-formedness, and manipulating logical formulas, as well as validating natural deduction (ND) proofs for propositional and first-order logic.

## Features

- **Propositional Logic (PL) Support**: Parse and check propositional logic formulas, evaluate them based on interpretations, generate truth tables, and check logical equivalence.

- **First-Order Logic (FOL) Support**: Parse and check first-order logic formulas, handle terms, functions, predicates, and variables, and determine whether a formula is a sentence.

- **Natural Deduction (ND) Proofs**: Validate and interpret natural deduction proofs in both propositional and first-order logic.

- **Well-Formed Formula (WFF) Checkers**: Ensure that logical formulas are syntactically correct and meet well-formedness criteria.

## Interfaces

### `IFormula`

The `IFormula` interface represents a logical formula in either propositional or first-order logic. It provides methods for retrieving the underlying expression, iterating over generic components, and checking for the presence of generics within the formula.

#### Key Methods:
- `IASTExp getFormula()`: Retrieve the underlying logical expression represented by the formula.
- `Iterator<ASTArbitrary> iterateGenerics()`: Iterate over the generic elements (placeholders) within the formula.
- `boolean hasGenerics()`: Check whether the formula contains generic elements.

### `IFOLFormula`

The `IFOLFormula` interface extends `IFormula` and represents a first-order logic formula. It provides methods for iterating over functions, predicates, bounded and unbounded variables, and terms within the formula.

#### Key Methods:
- `Iterator<ASTFun> iterateFunctions()`: Iterate over function symbols in the formula.
- `Iterator<ASTPred> iteratePredicates()`: Iterate over predicate symbols in the formula.
- `Iterator<ASTVariable> iterateBoundedVariables()`: Iterate over bounded variables in the formula.
- `boolean isABoundedVariable(ASTVariable variable)`: Check if a variable is bounded in the formula.
- `boolean isASentence()`: Check whether the formula is a sentence (no unbounded variables).

### `IPLFormula`

The `IPLFormula` interface represents a propositional logic expression and provides methods to evaluate the expression, generate truth tables, and check logical equivalence between two expressions.

#### Key Methods:
- `Iterator<ASTLiteral> iterateLiterals()`: Iterate over literals (propositional variables) in the expression.
- `boolean interpret(Map<ASTLiteral, Boolean> interpretation)`: Evaluate the propositional expression based on a given interpretation.
- `Map<Map<ASTLiteral, Boolean>, Boolean> getTruthTable()`: Generate the truth table for the propositional expression.
- `boolean isEquivalentTo(IPLFormula other)`: Check if the expression is logically equivalent to another propositional formula.

### `INDProof`

The `INDProof` interface represents a natural deduction (ND) proof. It provides methods for accessing key properties of an ND proof, including the conclusion, premises, proof height, and proof size.

#### Key Methods:
- `IFormula getConclusion()`: Retrieve the conclusion of the proof.
- `Iterator<IFormula> getPremises()`: Iterate over the premises of the proof.
- `int height()`: Compute the height (depth) of the proof tree.
- `int size()`: Compute the total number of steps (nodes) in the proof.

## Utilities

### `LogicAPI`

The `LogicAPI` class provides utility methods for parsing and verifying logical expressions and natural deduction proofs.

#### Key Methods:
- `static IPLFormula parsePL(String expression)`: Parse a propositional logic formula and check if it is well-formed.
- `static IFOLFormula parseFOL(String expression)`: Parse a first-order logic formula and check if it is well-formed.
- `static INDProof parseNDPLProof(String expression)`: Parse and validate a natural deduction proof for propositional logic.
- `static INDProof parseNDFOLProof(String expression)`: Parse and validate a natural deduction proof for first-order logic.

## Example Usage

### Propositional Logic

```java
String expression = "(p ∨ q) → (q ∨ p)";
IPLFormula formula = LogicAPI.parsePL(expression);
Map<Map<ASTLiteral, Boolean>, Boolean> truthTable = formula.getTruthTable();
boolean isEquivalent = formula.isEquivalentTo(anotherFormula);
```

### First-Order Logic

```java
String expression = "∀x (P(x) → Q(x))";
IFOLFormula formula = LogicAPI.parseFOL(expression);
boolean isSentence = formula.isASentence();
```


### Natural Deduction Proof (Propositional Logic)
```java
String proof = "[→I, 1] [(p ∨ q) → (q ∨ p). [∨E, 2, 3] [q ∨ p. [H, 1] [p ∨ q.] [∨IL] [q ∨ p. [H, 2] [p.]] [∨IR] [q ∨ p. [H, 3] [q.]]]]";
INDProof ndProof = LogicAPI.parseNDPLProof(proof);
```

### Natural Deduction Proof (First-Order Logic)
```java
String proof = "[→I, 1] [∀x (P(x) → Q(x)) → ∃x (P(x) → Q(x)).]";
INDProof ndProof = LogicAPI.parseNDFOLProof(proof);
```
## Full Documentation
You can find the full documentation at the following link:  
[https://danielmacau60004.github.io/LogicAPI/doc/com/logic/api/package-summary.html](https://danielmacau60004.github.io/LogicAPI/doc/com/logic/api/package-summary.html)

## Author
Daniel Macau

## Version
1.0 - March 2025
