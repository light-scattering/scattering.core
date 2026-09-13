# scatter-cli

[![NPM](https://img.shields.io/npm/v/@light-scattering/scatter-cli.svg?label=NPM)](https://www.npmjs.com/package/@light-scattering/scatter-cli)
[![PyPI](https://img.shields.io/pypi/v/scatter-cli.svg?label=PyPI)](https://pypi.org/project/scatter-cli/)

The official Node.js command-line wrapper for the [Scattering Core](https://github.com/light-scattering/scattering.core) Java library.

This package provides a frictionless way to install and execute the CLI directly from the Node.js environment or terminal, bypassing manual JAR downloads.

## Requirements

Because this package bundles and delegates commands to the underlying Java executable, you must have JRE 21+ installed and accessible in your system's `PATH`.

## Installation

Install the package globally via `npm`:
```bash
npm install -g @light-scattering/scatter-cli
```

Alternatively, you can run the CLI instantly without installing it using `npx`:
```bash
npx @light-scattering/scatter-cli --info
```

## Getting started

Once installed, both `scatter-cli` and the `scatter-vis` 3D viewer are globally available in your terminal.

To verify your setup, use the built-in demo command. It generates a standard Cluster-Cluster (CC) aggregate composed of 2,048 primary particles with fractal parameters `df=1.8` and `kf=1.3`, exported in the `multisphere` format.
```bash
scatter-cli demo > demo.xyzr        # Save assembly to a file.
scatter-cli demo | scatter-vis      # Show a 3D model of the assembly.
```

Currently, the `scatter-vis` utility exclusively supports data streams in the `multisphere` format.

## Usage

Here is a quick workflow demonstrating how to generate a custom synthetic fractal-like aggregate model and measure its morphological properties:
```bash
# Generate a Diffusion-Limited Aggregation (DLA) fractal-like aggregate model and save it to a file.
scatter-cli generate model pc dla --rad-fixed "2048,1" --export multisphere --out demo.xyzr
# Measure the Density-Correlation (DC) fractal dimension of the generated model.
scatter-cli measure demo.xyzr --metrics df-dc -i multisphere
```

> **Windows / PowerShell Note:** When executing commands in PowerShell, you must wrap comma-separated values in quotes (e.g., `--rad-fixed "2048,1"`). Otherwise, PowerShell will parse the commas as array operators and mangle the arguments before they reach the CLI.
> 
<div align="center">
  <table>
    <tr>
      <td><img src="https://raw.githubusercontent.com/light-scattering/scattering.core/develop/eu.scattering.cli/docs/assets/example.png" alt="PC DLA assembly" width="400"></td>
    </tr>
    <tr>
      <td align="center"><em>Fig 1: PC DLA synthetic fractal-like aggregate model.</em></td>
    </tr>
  </table>
</div>

## Documentation

This Node.js package is a thin distribution wrapper maintained as part of the larger `scattering.core` ecosystem. To avoid duplication, comprehensive documentation, algorithmic details, and advanced usage examples are hosted on the main repository:

* **[Project Documentation](https://github.com/light-scattering/scattering.core/blob/master/README.md)**
* **[CLI-Specific Documentation](https://github.com/light-scattering/scattering.core/blob/master/eu.scattering.cli/README.md)**