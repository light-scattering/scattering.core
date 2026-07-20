# Scattering Core CLI

The Scattering Core (**ScatterCore**) CLI provides a standalone command-line interface for the highly optimized ScatterCore Java library. 
In its current state, this CLI tool is designed specifically for the morphological analysis and executing fractal measurement methods on synthetic aggregates.

> **Note:**
> This documentation is actively being expanded. 
> It currently focuses on the specific measurement commands required to reproduce the morphological findings of our recently submitted manuscript.
> Aggregate generation is currently handled directly via the core Java API and will be integrated into the CLI in future updates.

## Table of contents

- [Project structure](#project-structure)
- [Measure](#measure)
    - [Help and available metrics](#help-and-available-metrics)
    - [Key options](#key-options)
    - [Input file](#input-file)
    - [Full example](#full-example)

## Project structure

The CLI is organized around specific subcommands. At the moment, only the `measure` module is implemented and available for use.

## Measure

The `measure` command calculates the morphological parameters of a loaded aggregate. To invoke it, type:

```bash
java -jar scatt-cli-0.2.0.jar measure [options] [file]
```

### Help and available metrics

To see a complete list of all available morphological parameters and command options, you can use the built-in help flag:
```bash
java -jar scatt-cli-0.2.0.jar measure --help
```

This will output the documentation directly to your terminal, including a comma-separated list of every valid metric tag (e.g., `np`, `rp`, `df-bc`, etc.) that can be passed to the `--metrics` option.

### Key options

- **`-m`, `--metrics`**: Defines the list of parameters to calculate. To perform multiple measurements simultaneously, separate the metric tags with a space. For example, to measure the three static fractal parameters, type:
    ```bash
    measure --metrics df-bc df-mr df-dc
    ```
- **`-f`, `--format`**: Defines the format of the input file. By default, the `json` format is used. To change this to the `multisphere` format (where each particle is defined on a single line as `x y z rp`), type:
    ```bash
    measure --format multisphere
    ```

### Input file

The final positional argument is the path to your input file. 
If omitted (or if `-` is provided), the CLI will read directly from standard input (`stdin`).

### Full example

A complete command to measure the fractal dimensions of a multisphere file named `aggregate.geo` looks like this:
```bash
java -jar scatt-cli-0.2.0.jar measure --metrics df-bc df-mr df-dc --format multisphere aggregate.geo
```