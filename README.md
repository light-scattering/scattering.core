# Scattering Core

Scattering Core (**ScatterCore**) is a highly optimized Java library designed for the generation and morphological analysis of synthetic fractal aggregates.

> **Note:**
> This documentation is actively being expanded. It currently focuses on the specific methods required to reproduce the findings of our recently submitted manuscript.

## Table of contents

- [Background and scope](#background-and-scope)
- [Development and AI transparency](#development-and-ai-transparency)
- [Project structure](#project-structure)
- [Engine initialization](#engine-initialization)
    - [Reproducible mode](#reproducible-mode)
    - [Performance mode](#performance-mode)
- [Computational parameters](#computational-parameters)
- [Generation algorithms](#generation-algorithms)
- [Loading and saving](#loading-and-saving)
- [Morphological analysis](#morphological-analysis)
    - [Core properties](#core-properties)
    - [Connectivity and overlap](#connectivity-and-overlap)
    - [Linear measurements](#linear-measurements)
    - [Centers of mass and geometry](#centers-of-mass-and-geometry)
    - [Surface and volume](#surface-and-volume)
    - [Gyration metrics](#gyration-metrics)
    - [Topological metrics](#topological-metrics)
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

## Engine initialization

All generation and analysis operations begin with the `ScatterFactory`, which serves as the core entry point for the library. 
The factory can be initialized in one of two main operating modes depending on your needs for reproducibility and performance.

### Reproducible mode

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

### Performance mode

If reproducibility is not required, you can initialize the factory without a seed. 
This mode utilizes a different, non-deterministic random generation strategy. 
As a result, it is extremely unlikely that the exact same aggregate structure will be generated twice.

*(While current development strictly prioritizes mathematical correctness for research publication, the unseeded mode lays the architectural groundwork for multithreaded performance optimizations planned for a near-future release)*
```java
// Initialize the factory for maximum multi-threaded performance
ScatterFactory factory = ScatterCore.createFactory();
```

## Computational parameters

To maintain high performance and numerical stability, the library relies on three configuration elements for calculations. They can be configured globally for the entire aggregate or fine-tuned for each individual particle:
- *epsilon* (Continuous Tolerance): The continuous threshold used for mathematical calculations across the engine. While it can be used to evaluate physical rules (like overlaps or contacts), it applies broadly to any continuous numerical evaluation. Default is `10E-4`.
- *delta* (Discrete Tolerance): The discrete threshold used for calculations. When operations require discretizing continuous space (such as mesh decomposition), this defines the element resolution. Default is 10E-2.
- *FBuffer* (Memory Management): A reusable data buffer required for heavy meshing and discrete operations. Recycling memory allocations across iterations prevents Garbage Collection (GC) overhead and maintains performance.

Before running complex operations, you can configure your tolerances and inject a pre-allocated buffer into the aggregate:
```java
// 1. Initialize and inject the reusable mesh buffer.
int capacity = 1000;
FBuffer<FBufferData> fBuffer = factory.getFBuffer(capacity);
fAggregate.setRefFBuffer(fBuffer);

// 2. Define the computational tolerances (defaults shown).
double epsilon = 10E-4;
double delta = 10E-2;

// 3. Apply tolerances to the entire aggregate.
fAggregate.setParticleEpsilon(epsilon);
fAggregate.setParticleDelta(delta);
```

If your operations require it, you can override these global parameters for specific particles:
```java
// Apply specific tolerances to individual particles.
for (var fShape : fAggregate) {
    fShape.setEpsilon(10E-5);   // Stricter continuous tolerance.
    fShape.setDelta(10E-3);     // Finer discrete resolution.
}
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

## Loading and saving

The library provides dedicated aspects for serializing aggregates and exporting them to various external formats. This allows you to save your generated structures, import existing ones, or export them for external visualization and mesh generation.

Both loading and saving contexts are accessed via the main `ScatterFactory` instance.

**Loading aggregates**
You can reconstruct an `FAggregate` from a string representation. The default and most comprehensive format is JSON, which strictly preserves all component properties.

```java
String data = "...";                                                // The serialized string data.

var load = this.factory.getLoadAspect().getFAggregateContext();     // Retrieve the loading context for aggregates.

FAggregate fAggregate = load.fromJSON(data);                        // Load from the default JSON format.     
FAggregate fAggregate = load.fromBasic(data, ExBasic.MULTISPHERE);  // Load from an alternative basic format.
```

**Saving and exporting aggregates**
When saving an aggregate, you have access to several specialized exporters depending on your target application (e.g., storage, meshing, or rendering).

```java

var save = this.factory.getSaveAspect().getComponentContext();      // Retrieve the saving context for components.

// 1. Standard Serialization
String output = save.toJSON(this.aggregate);                        // The default, comprehensive JSON format.      
String output = save.toBasic(this.aggregate, ExBasic.MULTISPHERE);  // Alternative lightweight formats.

// 2. External Tool Compatibility
String output = save.toFLAGE(this.aggregate);                       // Export to a format compatible with the FLAGE program.
String output = save.toNGSolve(this.aggregate);                     // Export for volumetric mesh generation using NetGen/NGSolve. 
String output = save.toPovRay(this.aggregate, ExPovRay.BOUNDARY);   // Export for high-quality 3D rendering using PovRay.
```

## Morphological analysis

Once an aggregate is generated or loaded into memory, you can perform various morphological analyses directly through its API.

### Core properties

This section provides fundamental information about the aggregate's size, composition, and constituent primary particles.

```java
// Returns the total number of primary particles that make up the geometry.
int size = fAggregate.size();

// Returns a comprehensive statistical summary of the primary particle radii.
FStat radius = fAggregate.getFStatParticleRadius();
```

Primary particles can also be accessed or iterated over directly for custom analysis:

```java
// Retrieves the underlying particle assembly reference.
FAssembly<Shape> fAssembly = fAggregate.getRefParticles();

// Iterates through individual primary particles.
for (var fParticle : fAggregate) {
    // Perform individual particle evaluation.
}

// Iterates exclusively through pairs of particles that are in direct contact.
fAggregate.forEachPairInContact((fParticle1, fParticle2) -> {
    // Perform pair particle evaluation.
});
```

### Connectivity and overlap

The library provides methods to evaluate internal structural integrity, overlap metrics, and spatial interactions between aggregates.

```java
// Evaluates internal structural integrity.
boolean isConnected = fAggregate.isConnected();
boolean isPointConnected = fAggregate.isPointConnected();
boolean isNonOverlapping = fAggregate.isNonOverlapping();

// Evaluates spatial relationships with another aggregate.
boolean touches = fAggregate.touches(otherAggregate);
boolean overlaps = fAggregate.overlaps(otherAggregate);

// Calculates statistical overlap measures across the structure.
FStat overlap = fAggregate.getOverlapFactor(OverlapFactor.PARTICLE_QUANTITATIVE);
```

The `getOverlapFactor` method returns an `FStat` object, which provides statistical analysis over a collection of calculated values. The `OverlapFactor` enum dictates exactly how these values are generated:

**Particle-Level Metrics**
For these methods, the `FStat` collection contains exactly one element per primary particle in the aggregate:

- **`PARTICLE_QUANTITATIVE`**: Returns the absolute count of overlaps (coordination number) for each particle.
- **`PARTICLE_LINEAR`**: Calculates the mean linear overlap for each particle. For any intersecting pair, the overlap is defined as `1 - d/(r1 + r2)`, where `d` is the distance between their centers.
- **`PARTICLE_VOLUMETRIC`**: Evaluates the overlapping volume fraction for each particle. The formula is `1 - (Vn / Vt)`, where `Vt` is the particle's total volume and `Vn` is its strictly non-overlapping volume. This calculation requires a pre-injected `FBuffer` and is governed by the `delta` parameter.

**Cluster-Level Metrics**
For these methods, the `FStat` collection contains elements corresponding to structural overlap layers, rather than individual particles:

- **`CLUSTER_VOLUMETRIC`**: Evaluates the volume distribution across different intersection depths for the entire aggregate. The overlap fraction for each layer is `1 - (Vi / Vt)`, where `Vt` is the total aggregate volume, and `Vi` is the volume of the `i`-th layer (e.g., `V0` is the volume belonging to no other particles, `V1` is shared by exactly 2 particles, `V2` is shared by 3 particles, etc.). The sum of all individual layer volumes exactly equals the total aggregate volume. This calculation requires a pre-injected `FBuffer` and is governed by the `delta` parameter.

> **Note:** Connectivity and point-based overlap metrics heavily depend on the `epsilon` tolerance parameter. When working with geometries generated by external algorithms or imported files, you may need to adjust `epsilon` to match their specific definitions of point contact and overlap. Conversely, all volumetric metrics depend on the `delta` parameter for mesh discretization.

### Linear measurements

```java
// Returns the 3D bounding box as a pair of absolute minimum and maximum coordinates.
FPairPos3D boundary = fAggregate.getBoundary();

// Returns the maximum spatial span of the entire aggregate.
double diameter = fAggregate.getDiameter();

// Calculates the total length of the bounding box along the X, Y, and Z axes.
FPos3D length = fAggregate.getLength();
// Calculates a specific bounding box dimension based on the Length enum.
double length = fAggregate.getLength(Length.MAX);
```

The `Length` enum provides the following options for extracting specific dimensions:
- `X`, `Y`, `Z`: The bounding box length along the specific coordinate axis.
- `MIN`, `MAX`: The absolute minimum or maximum bounding box length among all three axes.

You can also calculate the maximum spanning radius originating from a specific point. 
This is useful when comparing different structural definitions of the aggregate. 
The method is overloaded to accept various input formats:
```java
// Calculates the spanning radius originating from explicitly defined raw coordinates.
double radius = fAggregate.getRadiusFrom(0.0, 0.0, 0.0);

// Calculates the spanning radius originating from a dynamically calculated center type.
double radius = fAggregate.getRadiusFrom(Center.MASS);

// Calculates the spanning radius originating from an existing FPoint or FPos3D object.
double radius = fAggregate.getRadiusFrom(fPoint);
double radius = fAggregate.getRadiusFrom(fPos3D);
```

When using dynamically calculated centers, the following `Center` enum definitions are available:
- `BOX`: The geometric center (centroid of the bounding box).
- `MASS`: The center of mass based on the particle mass distribution.
- `ORIGIN`: The absolute coordinate system origin (0, 0, 0).
- `SPHERICAL`: The center of the minimum bounding sphere enclosing the aggregate (100 iterations).

If you need the full spatial distribution rather than just the maximum boundary, you can extract the distances of all primary particles relative to a specific origin:

```java
// Returns a comprehensive statistical summary (FStat) of particle distances from the specified center.
FStat distances = fAggregate.getFStatParticleDistance(Center.MASS);
```

### Centers of mass and geometry

The aggregate's center point can be calculated using several different structural definitions.

```java
// Returns the geometric center (centroid of the bounding box).
FPos3D center = fAggregate.getBoxCenter();
// Returns the center of the minimum bounding sphere using a custom number of iterations (e.g., 500).
FPos3D center = fAggregate.getSphericalCenter(500);
// Returns the center of mass based on a specific mass distribution type.
FPos3D center = fAggregate.getMassCenter(MassCenter.ADAPTIVE);
```

Because primary particles can vary in size, material, and overlap, the `MassCenter` enum dictates exactly how the mass distribution is integrated:

- **`SIMPLE_MONO`**: Approximates particles as mass points, ignoring overlap. While different materials are permitted, layered particles are not. It calculates a single, averaged radius and applies it uniformly to all particle positions.
- **`SIMPLE_POLY`**: Approximates particles as mass points, ignoring overlap. It accounts for different materials and layered particles, and evaluates each particle using its exact, individual size.
- **`VOLUMETRIC`**: Supports all shape types and overlapping geometries by decomposing the aggregate into a mesh. This requires passing an `FBuffer` object to handle the meshing operations, and the precision is governed by the `delta` parameter.
- **`ADAPTIVE`**: This approach makes a separate decision for each individual particle. It uses the fast `SIMPLE_POLY` logic where particles do not overlap, and strictly applies the `VOLUMETRIC` meshing logic to intersecting regions.

You can also retrieve any of the basic center coordinates dynamically using the general `getCenter` method and the `Center` enum:
```java
// Dynamically fetches the requested center type.
FPos3D center = fAggregate.getCenter(Center.BOX);
```

These center calculations are often invoked thousands of times inside tight iteration loops (e.g., during fractal dimension analysis or collision detection). To prevent massive Garbage Collection (GC) overhead from constantly instantiating new `FPos3D` objects, every method provides an overload that accepts a pre-allocated `FPoint`.

The `in` parameter acts as a data buffer: the method updates its internal coordinates and returns the exact same object, allowing for zero-allocation method chaining.

```java
// Pre-allocate the point once outside your loop.
FPoint bufferPoint = factory.getFPoint();

for (int i = 0; i < 1_000_000; i++) {
// The aggregate updates 'bufferPoint' and returns it, allowing direct chaining.
// No new objects are created in memory.
    double distance = fAggregate.getBoxCenter(bufferPoint).getDistance(target);
}
```

### Surface and volume

The library provides methods to calculate the total surface area and volume of the aggregate, as well as their equivalent sphere radii.

```java
// Calculates the total surface area and volume.
double surface = fAggregate.getSurface(Surface.ADAPTIVE);
double volume = fAggregate.getVolume(Volume.ADAPTIVE);

// Calculates the equivalent radius of a sphere with the exact same surface area or volume.
double rSurface = fAggregate.getSurfaceRadius(Surface.ADAPTIVE);
double rVolume = fAggregate.getVolumeRadius(Volume.ADAPTIVE);
```

Similar to center calculations, you can dictate exactly how these metrics are evaluated to balance performance with precision. The `Surface` and `Volume` enums provide three approaches:

- **`SIMPLE`**: An extremely fast approximation that completely ignores overlap. It simply sums the individual surface areas or volumes of all primary particles.
- **`DISCRETE`**: Evaluates overlapping geometries through discretization based on `delta`. For **volume**, it decomposes the structure into a 3D mesh. For **surface**, it breaks the outer bounds into small surface elements, removes those hidden inside overlapping particles, and sums the remainder. This requires a pre-injected `FBuffer`.
- **`ADAPTIVE`**: The optimal approach. It uses the fast `SIMPLE` mathematical logic for non-overlapping particles and dynamically applies the `DISCRETE` meshing logic strictly to intersecting regions.

If you need to perform custom spatial analysis, export the geometry to an external tool, or visualize the aggregate's exact continuous bounds, you can extract the underlying mesh generated by the engine directly:

```java
// Generates and returns the full mesh representation.
FMesh<FBufferData> mesh = fAggregate.getVolumeMesh();
```

### Gyration metrics

The library provides methods to analyze the spatial distribution of mass within the aggregate by calculating its gyration tensor and the radius of gyration.
```java
// Calculates the 3x3 gyration tensor matrix.
FMatrix3x3D tensor = fAggregate.getGyrationTensor(GyrationTensor.ADAPTIVE);
// Calculates the radius of gyration.
double rg = fAggregate.getRadiusOfGyration(RadiusOfGyration.SIMPLE_POLY_06R1);
```

Under the hood, these calculations automatically determine and use the corresponding mass center (as discussed in the **Centers** section) to ensure consistency.

For the **Gyration Tensor**, the `GyrationTensor` enum provides the standard approaches:
- **`SIMPLE_POLY`** and **`SIMPLE_MONO`**: Fast approximations that treat primary particles as mass points, either using their exact individual sizes or a single averaged size.
- **`VOLUMETRIC`**: Calculates an accurate spatial distribution by decomposing the geometry.
- **`ADAPTIVE`**: The optimal approach. It uses the fast `SIMPLE_POLY` logic where particles do not overlap, and applies meshing logic strictly to intersecting regions.

When calculating the **Radius of Gyration** using the simple mass-point approximations (`SIMPLE_MONO` and `SIMPLE_POLY`), the primary particles' own internal volumes are ignored.

To account for this, the `RadiusOfGyration` enum offers specific correction factors based on `r` (the averaged radius of all primary particles):
- **Base Methods (`SIMPLE_MONO`, `SIMPLE_POLY`)**: Strictly uses the point-mass approximation without any corrections.
- **`_06R1` Corrections**: Adds an offset of `0.6 * r` to the final calculation.
- **`_10R2` Corrections**: Adds an offset of `r * r` (or `r^2`) to the final calculation.
- **`VOLUMETRIC`**: Because this method accurately decomposes the full intersecting geometry, it inherently accounts for the particles' actual spatial distribution and requires no geometric corrections.

### Topological metrics

To deeply analyze the internal structure of the aggregate, the library provides methods to evaluate how primary particles are spatially arranged relative to one another.

Each of these metrics can be extracted in two formats: as an `FStat` object for basic statistical summaries (mean, variance, min, max), or as an `FPlot` object containing the distribution function data, which is ready to be visualized as a histogram.

```java
// Retrieve a statistical summary of the coordination numbers.
FStat coordStats = fAggregate.getCoordinationNumber();

// Retrieve the full distribution function for plotting.
FPlot coordPlot = fAggregate.getCoordinationNumberFunction();
```

The available topological metrics include:

- **Coordination Number** (`getCoordinationNumber` / `Function`): Evaluates how many direct neighbors (intersecting or touching) each primary particle has within the aggregate. When exported as an `FPlot`, it generates a discrete histogram ranging from 1 to the maximum coordination number, with a bin size of exactly 1.
- **Pair Distance** (`getPairDistance` / `Function`): Measures the spatial distances between the centers of primary particle pairs. The corresponding `FPlot` histogram spans from 0 to the maximum pair distance, dynamically scaling the number of bins based on the smallest primary particle radius (`max_distance / min_radius`).
- **Triplet Angle** (`getTripletAngle` / `Function`): Calculates the angles formed by triplets of adjacent primary particles, providing insight into the linearity, branching, and folding of the aggregate's chains. The `FPlot` histogram spans from 0 to π radians with exactly 180 bins, effectively providing a 1-degree resolution.

### Fractal dimension

This project implements two categories of fractal dimension extraction methods: structural and kinetic. 

#### Structural methods

Structural methods can be applied to any generated aggregate model or sphere assembly. Because they analyze the final geometry, they require no historical data about the aggregation process itself.

Currently, three structural algorithms are available: BC (Box-Counting), MR (Mass-Radius), and DC (Density Correlation). The simplest way to execute these measurements is by calling the desired method directly on the aggregate object:
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

Kinetic methods, on the other hand, require a monitor to be injected during the aggregation process. A monitor is simply an object that collects data at each step of the simulation. While you can easily create custom monitors, the most common ones are already built-in. For instance, you will use the radius of gyration monitor to measure the PL (Power-Law) fractal dimension. Note that there are separate monitors for the PC (Particle-Cluster) and the CC (Cluster-Cluster) aggregation processes.

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