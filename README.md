# Scattering Core

Scattering Core (**ScatterCore**) is a highly optimized Java library designed for the generation and morphological analysis of synthetic fractal aggregates.

> **Note:**
> This documentation is actively being expanded. It currently focuses on the specific methods required to reproduce the findings of our recently submitted manuscript.

## Table of contents

- [Background and scope](#background-and-scope)
- [Development and AI transparency](#development-and-ai-transparency)
- [Project structure](#project-structure)
- [Entry point](#entry-point)
    - [Reproducible mode](#reproducible-mode-deterministic)
    - [Performance mode](#performance-mode-non-deterministic)
- [Generation algorithms](#generation-algorithms)
- [Morphological analysis](#morphological-analysis)
    - [Fractal dimension](#fractal-dimension)
        - [Structural methods](#structural-methods)
        - [Kinetic methods](#kinetic-methods)
            - [PC aggregation](#particle-cluster-pc-aggregation)
            - [CC aggregation](#cluster-cluster-cc-aggregation)
          
## Background and scope

The core aggregation elements were built upon the foundation of my previous project, FLAGE (Fractal-Like Aggregate Generation Environment). 
However, ScatterCore is not a simple port - the entire codebase was rewritten almost from scratch to be modernized, heavily optimized, and highly extensible.

Because the project is massive, and I am currently the sole developer, this manual focuses strictly on high-level features, such as aggregation models and morphological analysis. 
Low-level mathematical primitives (like `FComplex`, `FPoint`, and `FVector`) are excluded from this guide. 
If you wish to utilize them in your own projects, please refer to the `design` module, where all public interfaces are clearly defined.

## Development and AI transparency

This project began several years ago, well before the LLM era.
As a result, the architectural foundation was built the old-school way: through academic literature, official documentation, and Stack Overflow.

During the later stages of development, maintaining the scientific correctness and cleanliness of the code remained the highest priority. 
AI was used primarily as a research assistant - sourcing information, suggesting component names, and generating initial drafts for a few isolated mathematical functions (such as calculating the gyration tensor and performing PCA).

To maintain transparency, methods that originated from an LLM prompt are explicitly flagged using a custom `@LLM` annotation. 
Furthermore, the generated code was only used as a baseline. 
Every single line within those marked methods was subsequently refactored, manually analyzed, and rigorously tested to ensure strict scientific accuracy.

On the other hand, since this manual is neither executable code nor a formal manuscript, I allowed an LLM (hi Gemini!) to heavily polish, structure, and rewrite my original text for better readability.

## Project structure

The repository is divided into three base modules:

*   **`design`** - Contains all public interfaces and serves primarily as API reference documentation.
*   **`lib`** - Contains the core implementation of the design, including the main `ScatterCore` entry point.
*   **`cli`** - Provides a command-line interface and is used to build a standalone, executable JAR file.

This manual focuses exclusively on the **`design`** and **`lib`** modules. 
The **`cli`** module is documented separately in its own project README file.

## Entry point

All generation and analysis operations begin with the `ScatterFactory`, which serves as the core entry point for the library. 
The factory can be initialized in one of two main operating modes depending on your needs for reproducibility and performance.

### Reproducible mode (deterministic)

This mode requires a seed value. 
When a seed is provided, all subsequent operations are executed on a single thread and are fully deterministic. 
If you generate an aggregate or run an analysis using the same seed, the results will be identical every time.
```java
// Initialize the factory with a specific seed for full reproducibility
ScatterFactory factory = ScatterCore.createFactory(123L);

// Alternatively, pass -1L to randomly generate a seed. 
// The generated seed will be printed to the console at startup so you can reuse it later.
ScatterFactory factory = ScatterCore.createFactory(-1L);
```
### Performance mode (non-deterministic)

If reproducibility is not required, you can initialize the factory without a seed. 
This mode utilizes a different, non-deterministic random generation strategy. 
As a result, it is extremely unlikely that the exact same aggregate structure will be generated twice.

*(While current development strictly prioritizes mathematical correctness for research publication, the unseeded mode lays the architectural groundwork for multithreaded performance optimizations planned for a near-future release)*
```java
// Initialize the factory for maximum multi-threaded performance
ScatterFactory factory = ScatterCore.createFactory();
```

## Generation algorithms

To generate a synthetic Cluster-Cluster (CC) aggregate, you must first define its fundamental physical properties. 
In this step, the primary particles are pre-allocated in memory, resulting in an unassembled aggregate where particles are not yet spatially positioned.
```java
int np = 1000;      // Number of primary particles.
double rp = 1.0;    // Particle radius.

// Create an unassembled monodisperse aggregate.
FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(np, rp);
```

Next, define the aggregation algorithm and its morphological targets by configuring parameters such as the fractal dimension and the fractal prefactor. 
Once you bind this model to your unassembled aggregate, simply execute the build process to physically position the particles according to your constraints:
```java
double df = 1.8;    // Target fractal dimension.
double kf = 1.3;    // Target fractal prefactor.

// Bind the tunable CC model to the aggregate.
var fModel = factory.getFModelContext().cc().tunable(fAggregate, df, kf);

// Assemble the aggregate.
fModel.build();
```

## Morphological analysis

Once an aggregate is generated or loaded into memory, you can perform various morphological analyses directly through its API.

### Fractal dimension

This project implements two categories of fractal dimension extraction methods: structural and kinetic. 

#### Structural methods

Structural methods can be applied to any generated aggregate model or sphere assembly. 
Because they analyze the final geometry, they require no historical data about the aggregation process itself.

Currently, three structural algorithms are available: BC (Box-Counting), MR (Mass-Radius), and DC (Density Correlation). 
The simplest way to execute these measurements is by calling the desired method directly on the aggregate object:
```java
// 1. Box-Counting (BC)
// The original, unoptimized box-counting method. It iterates over vast amounts of empty space. 
// Unless you have plenty of time (or are paid by the hour), it should be used strictly for testing.
double dimBC = fAggregate.getFractalDimension(FractalDimension.BC_NAIVE);

// The baseline box-counting method. 
// It yields the exact same results as the naive method but is heavily optimized for speed.
double dimBC = fAggregate.getFractalDimension(FractalDimension.BC_BASELINE);

// The fully optimized box-counting method (recommended). 
// Includes advanced spatial procedures like grid-shifting and PCA alignment.
double dimBC = fAggregate.getFractalDimension(FractalDimension.BC_OPTIMIZED);

// 2. Mass-Radius (MR)
// The mass-radius method utilizing boundary restriction.
double dimMR = fAggregate.getFractalDimension(FractalDimension.MR_RESTRICTED);

// 3. Density Correlation (DC)
// The density correlation method utilizing boundary restriction.
double dimDC = fAggregate.getFractalDimension(FractalDimension.DC_RESTRICTED);
```

If you need insights into the algorithm's performance or visual representations of the results, you can capture this auxiliary data using a metadata container:
```java
// Create a container to capture the diagnostic metadata.
FMetaDF fMeta = factory.getFMetaDF();

// Pass the metadata object as the second parameter during the extraction.
double dimBC = fAggregate.getFractalDimension(FractalDimension.BC_OPTIMIZED, fMeta);

// Retrieve the metrics
long   time = fMeta.getExecutionTimeMillis(); // Algorithm execution time in milliseconds.
String plot = fMeta.getPythonRenderScript();  // Python Plotly script to render the resulting chart.
FPlot  data = fMeta.getRefData();             // Raw coordinate data used to calculate the dimension.
```

If you need a more granular approach, you can configure each method manually using a custom configuration object:
```java
// 1. Box-Counting (BC) Custom Configuration
FConfigBC fConfigBC = factory.getFConfigBC()
                .setAlignedOrigin(false)        // Toggles whether the geometry is moved to the center of the Cartesian axis (0,0,0).
                .setAlignedPCA(true)            // Enables the Principal Component Analysis (PCA) alignment procedure.
                .setShiftsPerAxis(3)            // Sets the number of spatial grid shifts per axis.
                .setScalingFactor(2.0)          // Defines the box size scaling factor for each iteration.
                .setWindowRatio(0.9);           // Sets the ratio of the sliding window used for the linear fit.

// Create a BC-specific metadata container (currently shares the same elements as the default FMetaDF interface).
FMetaBC fMetaBC = factory.getFMetaBC();

// Extract the fractal dimension using the custom configuration. The metadata object is optional.
double dimBC = fAggregate.getFractalDimension(fConfigBC, fMetaBC);

// -------------------------------------------------------------------------------------------------

// Alternatively, you can initialize the configuration object using one of the built-in presets.
// This is especially useful for scientific reproducibility.
FConfigBC fConfigPreset = factory.getFConfigBC(FConfigBC.Preset.MAN_072026_SHIFT_PCA);
```

```java
// 2. Mass-Radius (MR) Custom Configuration
FConfigMR fConfigMR = factory.getFConfigMR()
                .setRadiusOfGyration(RadiusOfGyration.SIMPLE_POLY)  // Sets the method used to calculate the radius of gyration.
                .setScalingFactor(1.1)                              // Defines the radial scaling factor for each iteration.
                .setRestricted(true)                                // Applies the boundary restriction procedure.
                .setWindowRatio(0.9);                               // Sets the ratio of the sliding window used for the linear fit.

// Create an MR-specific metadata container (extends standard FMetaDF metrics with MR-specific ones).
FMetaMR fMetaMR = factory.getFMetaMR();

// Extract the fractal dimension using the custom configuration. The metadata object is optional.
double dimMR = fAggregate.getFractalDimension(fConfigMR, fMetaMR);

// Retrieve MR-specific diagnostic data.
int refs = fMetaMR.getRefParticlesCount();                          // Retrieves the number of reference particles used.

// -------------------------------------------------------------------------------------------------

// Alternatively, you can initialize the configuration object using one of the built-in presets.
FConfigMR fConfigPreset = factory.getFConfigMR(FConfigMR.Preset.FULL);
```

```java
// 3. Density Correlation (DC) Custom Configuration
FConfigDC fConfigDC = factory.getFConfigDC()
                .setRadiusOfGyration(RadiusOfGyration.SIMPLE_POLY)  // Sets the method used to calculate the radius of gyration.
                .setScalingFactor(1.1)                              // Defines the radial scaling factor for each iteration.
                .setRestricted(true)                                // Applies the boundary restriction procedure.
                .setWindowRatio(0.9);                               // Sets the ratio of the sliding window used for the linear fit.

// Create a DC-specific metadata container (extends standard FMetaDF metrics with DC-specific ones).
FMetaDC fMetaDC = factory.getFMetaDC();

// Extract the fractal dimension using the custom configuration. The metadata object is optional.
double dimDC = fAggregate.getFractalDimension(fConfigDC, fMetaDC);

// Retrieve DC-specific diagnostic data.
int refs = fMetaDC.getRefParticlesCount();                          // Retrieves the number of reference particles used.

// -------------------------------------------------------------------------------------------------

// Alternatively, you can initialize the configuration object using one of the built-in presets.
FConfigDC fConfigPreset = factory.getFConfigDC(FConfigDC.Preset.FULL);
```

#### Kinetic methods

Kinetic methods, on the other hand, require a monitor to be injected during the aggregation process. 
A monitor is simply an object that collects data at each step of the simulation. 
While you can easily create custom monitors, the most common ones are already built-in. 
For instance, you will use the radius of gyration monitor to measure the PL (Power-Law) fractal dimension. 
Note that there are separate monitors for the PC (Particle-Cluster) and the CC (Cluster-Cluster) aggregation processes.

##### Particle-Cluster (PC) aggregation

```java
// Create an unassembled fractal aggregate model.
var fAggregate = factory.getFAggregateContext().base().monodisperse(1000, 1.0);

// Create a predefined monitor to capture the radius of gyration of the growing aggregate at each step.
// The first parameter sets the calculation method.
// The second (optional) parameter defines how many initial steps to skip.
var fMonitor = factory.getFMonitorContext().pc().radiusOfGyration(RadiusOfGyration.SIMPLE_MONO_10R2, 5);

// This skip excludes early generation stages where the geometry is too small to be considered a true fractal.
// At very low particle counts, different calculation methods can yield inconsistent radii. 
// Additionally, early correction procedures for compact aggregates can temporarily distort the radius of the position sphere.

// Define the aggregation model and target fractal parameters.
var fModel = factory.getFModelContext().pc().tunable(fAggregate, 1.8, 1.3);

// Add a monitor.
fModel.addStepMonitor(fMonitor);

// Assemble the aggregate.
fModel.build();

// Raw measurement data.
FPlot data = fMonitor.getRefFPlot();

// Create the configuration object.
FConfigPCPL fConfigPCPL = factory.getFConfigPCPL()
        .setDropRatio(0.0)                      // Sets the percentage of initial steps that should be excluded from the linear fit. 
        .setWindowRatio(0.9);                   // Sets the ratio of the sliding window used for the linear fit.

// Create a PC/PL-specific metadata container. Since kinetic methods are very different from structural ones, there is no shared parent.
FMetaPCPL fMeta = factory.getFMetaPCPL();

// Extract the fractal dimension using the custom configuration. The metadata object is optional.
double dimPL = fMonitor.getPowerLawDimension(fConfigPCPL, fMeta);

// Retrieve PC/PL-specific diagnostic data.
String plot = fMeta.getPythonRenderScript();    // Python Plotly script to render the resulting chart.

// -------------------------------------------------------------------------------------------------

// Alternatively, you can initialize the configuration object using one of the built-in presets.
FConfigPCPL fConfigPreset = factory.getFConfigPCPL(FConfigPCPL.Preset.WINDOW);
```

##### Cluster-Cluster (CC) aggregation

```java
// Create an unassembled fractal aggregate model.
var fAggregate = factory.getFAggregateContext().base().monodisperse(1000, 1.0);

// Create a predefined monitor for capturing the radius of gyration of the growing geometry at each iteration step.
// The parameter sets the method used to calculate the radius of gyration.
var fMonitor = factory.getFMonitorContext().cc().radiusOfGyration(RadiusOfGyration.SIMPLE_MONO_10R2);

// The CC process merges already-generated PC clusters, eliminating the need for a skip parameter.

// Define the aggregation model and target fractal parameters.
FModelCCTunable fModel = factory.getFModelContext().cc().tunable(fAggregate, 1.8, 1.3);

// Add a monitor.
fModel.addStepMonitor(fMonitor);

// Assemble the aggregate.
fModel.build();

// Raw measurement data.
FPlotBar data = fMonitor.getRefFPlotBar();

// Create the configuration object.
FConfigCCPL fConfigCCPL = factory.getFConfigCCPL()
        .setDropRatio(0.0)                      // Sets the percentage of initial steps that should be excluded from the linear fit. 
        .setWindowRatio(0.9)                    // Sets the ratio of the sliding window used for the linear fit.
        .setReducer(FStat::mean);               // Set the reducer. Since multiple clusters can contain the same number of particles, their measurements must be reduced to a single value.

// Create a CC/PL-specific metadata container.
FMetaCCPL fMeta = factory.getFMetaCCPL();

// Extract the fractal dimension using the custom configuration. The metadata object is optional.
double dimPL = fMonitor.getPowerLawDimension(fConfigCCPL, fMeta);

// Retrieve CC/PL-specific diagnostic data.
String plotParsed = fMeta.getPythonRenderScript(FMetaCCPL.Plot.PARSED);     // Python Plotly script to render the resulting chart after using the reducer.
String plotRaw = fMeta.getPythonRenderScript(FMetaCCPL.Plot.RAW);           // Python Plotly script to show measured raw data.

// -------------------------------------------------------------------------------------------------

// Alternatively, you can initialize the configuration object using one of the built-in presets.
// This is especially useful for scientific reproducibility.
FConfigCCPL fConfigPreset = factory.getFConfigCCPL(FConfigCCPL.Preset.WINDOW);
```