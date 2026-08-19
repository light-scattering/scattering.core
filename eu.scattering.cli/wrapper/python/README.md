# scatter-cli

This is the official Python command-line wrapper for the `scattering.core` scientific Java library.

It provides a convenient way to install and execute the CLI directly from your Python environment or terminal using `pip`.

## Requirements

Because this package bundles and delegates commands to the underlying Java executable, you must have JRE 21+ installed and accessible in your system's `PATH`.

## Installation

You can install the CLI directly from PyPI:
```bash
pip install scatter-cli
```

## Usage

Once installed, the `scatter-cli` command is globally available in your terminal. You can pass any standard arguments directly to it:
```bash
scatter-cli --version
```

You can also run the CLI immediately without installing it by using `pipx`:
```bash
pipx run scatter-cli --version
```

## Documentation

This Python package is a thin wrapper maintained as part of the larger `scattering.core` repository. For comprehensive documentation, algorithmic details, and advanced usage examples, please refer to:

* **[Project Documentation](https://github.com/light-scattering/scattering.core/blob/master/README.md)**
* **[CLI-Specific Documentation](https://github.com/light-scattering/scattering.core/blob/master/eu.scattering.cli/README.md)**