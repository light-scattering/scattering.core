# Contributing to Scattering Core

Thank you for considering contributing to Scattering Core! Whether you are fixing a bug, adding a new feature, or improving the documentation, your help is greatly appreciated.

## 1. Where to start

* **Bugs & Features:** Please use the GitHub Issue tracker. Double-check if your issue has already been reported before creating a new one.
* **Core Science:** If you want to propose a new feature, please provide references to the relevant academic literature.
* **Improvement:** If you want to update or extend the general functionality of the software, please briefly explain your motivation.

## 2. Development setup

Scattering Core is built with Java and uses Gradle for dependency management.

1. Fork the repository and clone it to your local machine.
2. Ensure you have installed:
    - Java v21+
    - Node.js v14+
    - Python v3.9+
3. Run the Gradle `verify` task to check code consistency.

## 3. The pull request process

1. **Branching:** Create a new branch for your feature or bugfix (e.g., `feature/bc-dim` or `fix/bc-dim`).
2. **Testing:** Ensure your code compiles and all existing tests pass. Include tests to verify the accuracy of your feature/bugfix.
3. **Licensing consent:** When you open a PR, a template will automatically load. Because I plan to extract low-level math utilities (`FPoint`, `FVector`, etc.) into a separate MIT-licensed repository, the template includes a mandatory checkbox. You will be asked to consent to dual-licensing your contributions to allow for this future modularization.

## 4. Coding standards

* **Documentation:** Please write modular, self-documenting code. Select meaningful names for classes, methods, and variables. Add comments only where they are necessary (e.g., to explain your reasoning or to include external publications/sources). Add JavaDoc to public methods. Don't use emojis in the code - such submissions will be automatically rejected 👎.
* **AI Usage:** If you use LLMs to assist in writing your contribution, please ensure the final code is rigorously tested, manually verified, and fits the architectural style of the project. Please also mark it with the `@LLM` annotation.