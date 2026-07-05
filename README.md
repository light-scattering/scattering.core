# Scattering Core

Scattering Core is an optimized Java library designed for the generation and morphological analysis of synthetic fractal aggregates.

> **Note on Manuscript Reproducibility:**
> This manual is actively being expanded and currently focuses on the specific methods required to reproduce the findings of the recently submitted manuscript. 
> All test configurations used to generate the manuscript's data are preserved in the `lib` module, located at:
> `src/test/java/eu/scattering/core/paper/morphology_07_2026`.

## Table of contents

- [Generation algorithms](#generation-algorithms)
- [Morphological analysis](#morphological-analysis)

## Generation algorithms

All generation and analysis operations begin with the `ScatFactory`, which serves as the core entry point for the library:
```java
var factory = ScatFactoryDef.create();
```

To generate a synthetic Cluster-Cluster (CC) aggregate, you must first define its fundamental physical properties.
In this step, the primary particles are pre-allocated in memory, but they are not yet spatially positioned.
```java
int size = 1000;    // Number of primary particles
double rp = 1.0;    // Particle radius

var fAggregate = factory.getFAggregateContext().base().monodisperse(size, rp);
```

Next, define the aggregation algorithm and its morphological targets by configuring parameters such as the fractal dimension (`df`) and the fractal prefactor (`kf`). 
Once you bind this model to your aggregate, simply execute the build process to physically position the particles according to your constraints:
```java
double df = 1.8;    // Fractal dimension
double kf = 1.3;    // Fractal prefactor

// Bind the tunable CC model with the aggregate
var fModel = factory.getFModelContext().cc().tunable(fAggregate, df, kf);

// Build the aggregate
fModel.build();
```

## Morphological analysis

Once an aggregate is generated (or loaded into memory), you can perform various morphological analyses directly through its API.

To extract the fractal dimension, simply pass your preferred analytical method directly to the aggregate object:
```java
// The optimized box-counting method
double dimBC = fAggregate.getFractalDimension(FractalDimension.BC_OPTIMIZED);

// The density correlation method with boundary restriction
double dimDC = fAggregate.getFractalDimension(FractalDimension.DC_RESTRICTED);

// The mass-radius method with boundary restriction
double dimMR = fAggregate.getFractalDimension(FractalDimension.MR_RESTRICTED);
```