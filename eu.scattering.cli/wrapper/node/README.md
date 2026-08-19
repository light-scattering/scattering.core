# scatter-cli

This is the official Node.js command-line wrapper for the `scattering.core` scientific Java library.

It provides a convenient way to install and execute the CLI directly from your Node.js environment or terminal using `npm`.

## Requirements

Because this package bundles and delegates commands to the underlying Java executable, you must have a **Java Runtime Environment** (version 21 or higher) installed and accessible in your system's `PATH`.

## Installation

You can install the CLI directly from `npm`:
```bash
npm install -g @light-scattering/scatter-cli
```

## Usage

Once installed, the `scatter-cli` command is globally available in your terminal. You can pass any standard arguments directly to it:
```bash
scatter-cli --version
```

You can also run the CLI immediately without installing it by using `npx`:
```bash
npx @light-scattering/scatter-cli --version
```

## Documentation

This Node.js package is a thin wrapper maintained as part of the larger `scattering.core` repository. For comprehensive documentation, algorithmic details, and advanced usage examples, please refer to:

* **[Project Documentation](https://github.com/light-scattering/scattering.core/blob/master/README.md)**
* **[CLI-Specific Documentation](https://github.com/light-scattering/scattering.core/blob/master/eu.scattering.cli/README.md)**

## License

This project is licensed under the **GNU Affero General Public License v3.0 (AGPL-3.0)**.