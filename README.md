# Introduction

The purpose of the code is to generate and analyze synthetic fractal aggregates.

> **Note:** The manual is actively being expanded.
> Currently, it outlines methods required to reproduce the results of the recently submitted manuscript.
> 
## Table of contents

- [Modules](#modules)
- [Generation algorithms](#generation-algorithms)
- [Morphological analysis](#morphological-analysis)

## Modules

The project is divided into three decoupled modules:

- **`design`** - The core API containing all public interfaces, defining both the operations and the data structures.
- **`lib`** - The encapsulated implementation handling all physics, math, and core logic.
- **`cli`** - The command-line interface, providing a standalone JAR for console and script execution that adheres to the Unix philosophy.

## Generation algorithms



All generation and analysis operations begin with the `ScatFactory`, which serves as the core entry point for the library:

```java
var factory = ScatFactoryDef.create();
```

To generate a synthetic Cluster-Cluster (CC) aggregate, you must first define its fundamental physical properties. 
In this step, the primary particles are generated a priori in memory, but they are not yet spatially positioned.
```java
int size = 1000;    // Number of primary particles
double rp = 1.0;    // Particle radius

var fAggregate = factory.getFAggregateContext().base().monodisperse(size, rp);
```

Next, define the aggregation algorithm and its morphological targets by configuring parameters such as the fractal dimension (`df`) and the fractal prefactor (`kf`). Once you bind this model to your aggregate, simply execute the build process to physically position the particles according to your constraints:

```java
double df = 1.8;    // Fractal dimension
double kf = 1.3;    // Fractal prefactor

// Bind the tunable CC model with the aggregate
var fModel = factory.getFModelContext().cc().tunable(fAggregate, df, kf);

// Build the aggregate
fModel.build();
```

## Morphological analysis
