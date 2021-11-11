## Munkres(Hangarian) Algorithm Implimentation for Scala 3

> The Hungarian method is a combinatorial optimization algorithm that solves the assignment problem in polynomial time and which anticipated later primal–dual methods. It was developed and published in 1955 by Harold Kuhn, who gave the name "Hungarian method" because the algorithm was largely based on the earlier works of two Hungarian mathematicians: Dénes Kőnig and Jenő Egerváry.
>
> https://en.wikipedia.org/wiki/Hungarian_algorithm

## Install

`Munkres` currently supports only Scala 3.

```scala
scalaVersion := "3.x.x"

libraryDependencies ++= Seq(
  "dev.i10416" %% "munkres" % "0.0.1"
)
```
## Usage

```scala
import Munkres.*
val matrix =  Array(
    Array(5.0, 4.0, 7, 6.0),
    Array(6.0, 7.0, 3.0, 2),
    Array(8,11, 2.0, 5),
    Array(9, 8, 6.0, 7))
  )
Munkres.minimize(matrix)
// => List((2,2), (0,1), (3,0), (1,3))
Munkres.cost(matrix)
// 17
```

## License
Licensed under the Apache License, Version 2.0.

