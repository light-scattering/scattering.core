# scatter-cli

[![PyPI](https://img.shields.io/pypi/v/scatter-cli.svg?label=PyPI)](https://pypi.org/project/scatter-cli/)
[![NPM](https://img.shields.io/npm/v/@light-scattering/scatter-cli.svg?label=NPM)](https://www.npmjs.com/package/@light-scattering/scatter-cli)

The official Python command-line wrapper for the [Scattering Core](https://github.com/light-scattering/scattering.core) Java library.

This package provides a frictionless way to install and execute the CLI directly from the Python environment or terminal, bypassing manual JAR downloads.

## Requirements

Because this package bundles and delegates commands to the underlying Java executable, you must have JRE 21+ installed and accessible in your system's `PATH`.

## Installation

Install the package globally or in a virtual environment via `pip`:
```bash
pip install scatter-cli
```

Alternatively, you can run the CLI instantly without installing it using `pipx`:
```bash
pipx run scatter-cli --info
```

## Usage

Once installed, the `scatter-cli` command is globally available. Here is a quick workflow demonstrating how to generate a synthetic fractal-like aggregate model and measure its morphological properties:
```bash
# 1. Generate a Diffusion-Limited Aggregation (DLA) fractal-like aggregate model and save to a file.
scatter-cli generate model pc dla --rad-fixed 2048,1 --export multisphere --out demo.xyzr
# 2. Measure the Density-Correlation (DC) fractal dimension of the generated model.
scatter-cli measure demo.xyzr --metrics df-dc -i multisphere
```

<div align="center">
  <table>
    <tr>
      <td><img src="docs/assets/example.png" alt="PC DLA assembly" width="400"></td>
    </tr>
    <tr>
      <td align="center"><em>Fig 1: PC DLA synthetic fractal-like aggregate model.</em></td>
    </tr>
  </table>
</div>

## Documentation

This Python package is a thin distribution wrapper maintained as part of the larger `scattering.core` ecosystem. To avoid duplication, comprehensive documentation, algorithmic details, and advanced usage examples are hosted on the main repository:

* **[Project Documentation](https://github.com/light-scattering/scattering.core/blob/master/README.md)**
* **[CLI-Specific Documentation](https://github.com/light-scattering/scattering.core/blob/master/eu.scattering.cli/README.md)**