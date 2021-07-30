## Munkres(Hangarian) Algorithm Implimentation for Scala

> The Hungarian method is a combinatorial optimization algorithm that solves the assignment problem in polynomial time and which anticipated later primal–dual methods. It was developed and published in 1955 by Harold Kuhn, who gave the name "Hungarian method" because the algorithm was largely based on the earlier works of two Hungarian mathematicians: Dénes Kőnig and Jenő Egerváry.
>
> https://en.wikipedia.org/wiki/Hungarian_algorithm
## Usage

```scala
import Munkres.*
val matrix =  Array(
    Array(5.0, 4.0, 7, 6.0),
    Array(6.0, 7.0, 3.0, 2),
    Array(8,11, 2.0, 5),
    Array(9, 8, 6.0, 7))
  )
minimize(m)
// => List((2,2), (0,1), (3,0), (1,3))
cost(m)
// 17
```

## TODO
- add padding

## License
Licensed under the Apache License, Version 2.0.

