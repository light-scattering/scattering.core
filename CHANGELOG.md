# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [0.3.0] - 2026-08-02

### Changed
- Refactored and condensed the CLI metric argument strings (e.g., `cm`, `cs`, `cb`, `df-bc`) for improved usability.
- Renamed several core API measurement methods to maintain strict 1:1 parity with the updated CLI terminology.
- Expanded the main `README.md` to include historical API design rationale (limitations vs. pre-allocation) and distinct sections for data serialization and exporting.

### Removed
- Unnecessary `@Tag` annotations across the library test suite to simplify test execution and maintenance.

## [0.2.0] - 2026-07-20

### Added
- Fluent Configuration API (`FConfig...`) for granular, object-oriented control over fractal measurement algorithms (BC, MR, DC, and PL).
- Metadata containers (`FMeta...`) to extract auxiliary diagnostic data, including execution times, reference particle counts, and rendering scripts.
- Built-in algorithm presets (e.g., `FULL`, `NAIVE`, `RESTRICTED`) to streamline measurements.
- Specific reproducibility presets (e.g., `MAN_072026_SHIFT_PCA`) to permanently lock in the exact parameters used for the 2026 CPC manuscript.
- Dedicated README documentation for the standalone CLI module.

### Changed
- Minor internal refactoring and stability improvements within the CLI module.
- Significantly expanded and polished the main project README to reflect the new architecture and provide comprehensive code examples.

## [0.1.0] - 2026-07-05

### Added
- Initial public release of Scattering Core for manuscript submission.
- Strict API for defining fractal aggregate structures (`design` module).
- Implementation of generation algorithms and morphological analysis (`lib` module).
- Command-line interface for standalone execution (`cli` module).
- Backward-compatible baseline methods for manuscript reproducibility.